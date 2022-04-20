package BusinessObjects; /**
 * SERVER  - MULTITHREADED                                         March 2021
 * <p>
 * BusinessObjects.Server accepts client connections, creates a ClientHandler to handle the
 * BusinessObjects.Client communication, creates a socket and passes the socket to the handler,
 * runs the handler in a separate Thread.
 * <p>
 * <p>
 * The handler reads requests from clients, and sends replies to clients, all in
 * accordance with the rules of the protocol. as specified in
 * "ClientServerBasic" sample program
 * <p>
 * The following PROTOCOL is implemented:
 * <p>
 * If ( the BusinessObjects.Server receives the request "Time", from a BusinessObjects.Client ) then : the
 * server will send back the current time
 * <p>
 * If ( the BusinessObjects.Server receives the request "Echo message", from a BusinessObjects.Client ) then :
 * the server will send back the message
 * <p>
 * If ( the BusinessObjects.Server receives the request it does not recognize ) then : the
 * server will send back the message "Sorry, I don't understand"
 * <p>
 * This is an example of a simple protocol, where the server's response is based
 * on the client's request.
 *
 *  Each client is handled by a ClientHandler running in a separate worker Thread
 *  which allows the BusinessObjects.Server to continually listen for and handle multiple clients
 */


import DAOs.LocalDateAdapter;
import DAOs.MySqlPlayerDao;
import DAOs.PlayerDaoInterface;
import DTOs.Player;
import Exceptions.DaoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Server
{
    public static void main(String[] args)
    {
        Server server = new Server();
        server.start();
    }

    public void start()
    {
        try
        {
            PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();
            ServerSocket ss = new ServerSocket(8080);  // set up ServerSocket to listen for connections on port 8080

            System.out.println("BusinessObjects.Server: BusinessObjects.Server started. Listening for connections on port 8080...");

            int clientNumber = 0;  // a number for clients that the server allocates as clients connect

            while (true)    // loop continuously to accept new client connections
            {
                Socket socket = ss.accept();    // listen (and wait) for a connection, accept the connection,
                // and open a new socket to communicate with the client
                clientNumber++;

                System.out.println("BusinessObjects.Server: BusinessObjects.Client " + clientNumber + " has connected.");

                System.out.println("BusinessObjects.Server: Port# of remote client: " + socket.getPort());
                System.out.println("BusinessObjects.Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, clientNumber, IPlayerDao)); // create a new ClientHandler for the client,
                t.start();                                                  // and run it in its own thread

                System.out.println("BusinessObjects.Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("BusinessObjects.Server: Listening for further connections...");
            }
        } catch (IOException e)
        {
            System.out.println("BusinessObjects.Server: IOException: " + e);
        }
        System.out.println("BusinessObjects.Server: BusinessObjects.Server exiting, Goodbye!");
    }

    public static class ClientHandler implements Runnable   // each ClientHandler communicates with one BusinessObjects.Client
    {
        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;
        PlayerDaoInterface IPlayerDao;

        public ClientHandler(Socket clientSocket, int clientNumber, PlayerDaoInterface IPlayerDao)
        {
            try
            {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber;  // ID number that we are assigning to this client
                this.IPlayerDao = IPlayerDao;
                this.socket = clientSocket;  // store socket ref for closing

            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

        @Override
        public void run()
        {
            Gson gsonParser = new GsonBuilder()
                    //.setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            String message;
            try
            {
                while ((message = socketReader.readLine()) != null)
                {
                    System.out.println("BusinessObjects.Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);

                    if (message.startsWith("DisplayById"))
                    {
                        String[] tokens = message.split(" ");
                        int id = Integer.parseInt(tokens[1]);
                        String player = IPlayerDao.findPlayerByIDJSON(id);
                        System.out.println(player);
                        socketWriter.println(player);
                    }
                    else if (message.startsWith("DisplayAll"))
                    {
                        String players = IPlayerDao.findAllPlayersJSON();
                        socketWriter.println(players);
                    }
                    else if (message.startsWith("Add"))
                    {
                        String tokens = message.substring(4);
                        System.out.println(tokens);
                        Player p = gsonParser.fromJson(tokens, Player.class);
                        IPlayerDao.insertPlayer(p.getFirstName(), p.getLastName(),  p.getWeight(), p.getHeight(), p.getBirthDate(),p.getChampionshipWins());
                        Player test = IPlayerDao.findPlayerByID(p.getPlayerID());
                        socketWriter.println(test);
                    }
                    else if (message.startsWith("Delete"))
                    {
                        String[] tokens = message.split(" ");
                        int id = Integer.parseInt(tokens[1]);
                        IPlayerDao.deletePlayerByID(id);
                        Player p = IPlayerDao.findPlayerByID(id);
                        socketWriter.println(p);
                    }
                    else if (message.startsWith("Summary"))
                    {
                        double averageHeight;
                        double averageWeight;
                        double averageWins;
                        double totalHeight = 0 ;
                        double totalWeight = 0;
                        double totalWins = 0;
                        int size = IPlayerDao.findAllPlayers().size();
                        for(int i = 0; i < size; i++)
                        {
                            totalHeight += IPlayerDao.findAllPlayers().get(i).getHeight();
                            totalWeight += IPlayerDao.findAllPlayers().get(i).getWeight();
                            totalWins += IPlayerDao.findAllPlayers().get(i).getChampionshipWins();
                        }
                        averageHeight = totalHeight / size;
                        averageWeight = totalWeight / size;
                        averageWins = totalWins / size;
                        socketWriter.println(size);
                        socketWriter.println(averageHeight);
                        socketWriter.println(averageWeight);
                        socketWriter.println(averageWins);
                    }
                    else
                    {
                        socketWriter.println("I'm sorry I don't understand :(");
                    }
                }

                socket.close();

            } catch (IOException | DaoException ex)
            {
                ex.printStackTrace();
            }
            System.out.println("BusinessObjects.Server: (ClientHandler): Handler for BusinessObjects.Client " + clientNumber + " is terminating .....");
        }
    }

}
