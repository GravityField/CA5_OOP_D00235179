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
import com.google.gson.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.InputMismatchException;
import java.util.Scanner;

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
            final int EXIT = 3;

            String command = "";
            String usersInput;
            printMenu();

            while(option != EXIT) {

                try {
                    usersInput = in.nextLine();
                    option = Integer.parseInt(usersInput);
                    switch (option) {
                        case DISPLAY_BY_ID:
                            command = "DisplayById";

                            break;
                        case DISPLAY_ALL:
                            command = "DisplayAll";

                            break;
                        case EXIT:
                            break;


                    }
                }catch(InputMismatchException | NumberFormatException e)
                {
                    System.out.print("Invalid option - please enter number in range\n");
                }



                if (command.startsWith("DisplayById"))
                {
                    try {
                        System.out.println("Enter Id:");
                        int id = in.nextInt();
                        command = command + " " + id;
                        socketWriter.println(command);
                        String input = socketReader.nextLine();
                        Player p = gsonParser.fromJson(input, Player.class);
                        System.out.println(p);
                    }catch (JsonSyntaxException e){
                        System.out.println(e);
                    }
                }
                else if (command.startsWith("DisplayAll"))   //we expect the server to return a time
                {
                    socketWriter.println(command);
                    String input = socketReader.nextLine();
                    System.out.println("BusinessObjects.Client message: Response from server: \"" + input + "\"");
                }
                else if (command.startsWith("Echo"))
                {
                    String input = socketReader.nextLine();
                    System.out.println("BusinessObjects.Client message: Response from server: \"" + input + "\"");

                }
                else                            // the user has entered the Echo command or an invalid command
                {
                    String input = socketReader.nextLine();
                    System.out.println("BusinessObjects.Client message: Response from server: \"" + input + "\"");
                }

                printMenu();
                in.nextLine();
//                command = in.nextLine();
//                socketWriter.println(command);

            }


            socketWriter.close();
            socketReader.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("BusinessObjects.Client message: IOException: "+e);
        }
    }
    void printMenu()
    {
        System.out.println("Client Menu");
        System.out.println("1. Display Student By Id");
        System.out.println("2. Display All Students");
        System.out.println("3. TBD");
        System.out.println("Enter option:");
    }
}


//  LocalTime time = LocalTime.parse(timeString); // Parse timeString -> convert to LocalTime object if required