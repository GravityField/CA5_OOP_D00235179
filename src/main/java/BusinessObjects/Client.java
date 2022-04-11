package BusinessObjects; /** CLIENT                                                  March 2021
 *
 * This BusinessObjects.Client program asks the user to input commands to be sent to the server.
 *
 * There are only two valid commands in the protocol: "Time" and "Echo"
 *
 * If user types "Time" the server should reply with the current server time.
 *
 * If the user types "Echo" followed by a message, the server will echo back the message.
 * e.g. "Echo Nice to meet you"
 *
 * If the user enters any other input, the server will not understand, and
 * will send back a message to the effect.
 *
 * NOte: You must run the server before running this the client.
 * (Both the server and the client will be running together on this computer)
 */


import DAOs.LocalDateAdapter;
import DTOs.Player;
import Exceptions.DaoException;
import com.google.gson.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.*;

public class Client
{
    public static void main(String[] args)
    {
        Client client = new Client();
        client.start();
    }

    public void start()
    {
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket
            System.out.println("BusinessObjects.Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("BusinessObjects.Client: Port# of BusinessObjects.Server :" + socket.getPort() );

            System.out.println("BusinessObjects.Client message: The BusinessObjects.Client is running and has connected to the server");

            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);   // true => auto flush buffers


            Scanner socketReader = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply
//            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            Gson gsonParser = new GsonBuilder()
                    //.setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            int option = 0;
            final int DISPLAY_BY_ID = 1;
            final int DISPLAY_ALL = 2;
            final int ADD_PLAYER = 3;
            final int DELETE_PLAYER = 4;
            final int ADDITIONAL_FEATURE = 5;
            final int EXIT = -1;

            String command;
            String usersInput;
            String input;
            int id;

            while(option != EXIT) {

                try {
                    printMenu();
                    usersInput = in.nextLine();
                    option = Integer.parseInt(usersInput);
                    switch (option) {
                        case DISPLAY_BY_ID:
                            command = "DisplayById";
                            System.out.println("Enter Id:");
                            id = in.nextInt();
                            in.nextLine();
                            command = command + " " + id;
                            socketWriter.println(command);
                            input = socketReader.nextLine();
                            Player p = gsonParser.fromJson(input, Player.class);
                            if(p != null) {
                                System.out.println(p);
                            }
                            else{
                                System.out.println("Player does not exist");
                            }
                            break;
                        case DISPLAY_ALL:

                            command = "DisplayAll";
                            socketWriter.println(command);
                            input = socketReader.nextLine();
                            Player[] players = gsonParser.fromJson(input, Player[].class);
                            for(Player player: players)
                            {
                                System.out.println(player);
                            }
                            //System.out.println("BusinessObjects.Client message: Response from server: \"" + input + "\"");
                            break;
                        case ADD_PLAYER:
                            command = "Add";
                            System.out.println("Enter First Name:");
                            String firstName = in.nextLine();
                            System.out.println("Enter Last Name:");
                            String lastName = in.nextLine();
                            System.out.println("Enter Weight:");
                            double weight = in.nextDouble();
                            System.out.println("Enter Height:");
                            double height = in.nextDouble();
                            System.out.println("Enter Birth Year:");
                            int year = in.nextInt();
                            System.out.println("Enter Birth Month:");
                            int month = in.nextInt();
                            System.out.println("Enter Birth Day:");
                            int day = in.nextInt();
                            System.out.println("Enter Championship Wins:");
                            int championshipWins = in.nextInt();
                            LocalDate birthDate = LocalDate.of(year,month,day);
                            Player newP = new Player(firstName,lastName,weight,height,birthDate,championshipWins);
                            String newPlayer =  gsonParser.toJson(newP);
                            command = command + " " + newPlayer;
                            socketWriter.println(command);
                            input = socketReader.nextLine();
                            if(!input.equals("null"))
                            {
                                System.out.println("Player added");
                            }
                            else{
                                System.out.println("Player was not added");
                            }
                            socketWriter.println(command);
                            input = socketReader.nextLine();
                            System.out.println("BusinessObjects.Client message: Response from server: \"" + input + "\"");
                            break;
                        case DELETE_PLAYER:
                            command = "Delete";
                            System.out.println("Enter Id to delete:");
                            id = in.nextInt();
                            in.nextLine();
                            command = command + " " + id;
                            socketWriter.println(command);
                            input = socketReader.nextLine();
                            System.out.println(input);
                            if(input.equals("null"))
                            {
                                System.out.println("Player deleted");
                            }
                            else
                            {
                                System.out.println("Deleted");
                            }
                            break;
                            case ADDITIONAL_FEATURE:
                                command = "Summary";
                                socketWriter.println(command);
                                int count = socketReader.nextInt();
                                double averageHeight = socketReader.nextDouble();
                                double averageWeight = socketReader.nextDouble();
                                double averageWins = socketReader.nextDouble();
                                System.out.println("Count\tAverage Height\tAverage Weight\tAverage Wins\t");
                                System.out.printf("%-10d%-15.2f%-15.2f%-15.2f", count, averageHeight, averageWeight, averageWins);
                            break;
                        case EXIT:
                            System.out.println("Closing");
                            break;
                        default:
                            break;

                    }
                }catch(InputMismatchException | NumberFormatException e)
                {
                    System.out.print("Invalid option - please enter number in range\n");
                }
//                in.nextLine();
//                command = "";
//                socketWriter.println(command);
            }

            socketWriter.close();
            socketReader.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("BusinessObjects.Client message: IOException: "+e);
        }catch (JsonSyntaxException e){
            System.out.println(e);
        }
    }
    void printMenu()
    {
        System.out.println("\nClient Menu");
        System.out.println("1. Display Student By Id");
        System.out.println("2. Display All Students");
        System.out.println("3. Add Player");
        System.out.println("4. Delete Player By Id");
        System.out.println("5. Summary");
        System.out.println("-1. Exit");
        System.out.println("Enter option:");
    }
}