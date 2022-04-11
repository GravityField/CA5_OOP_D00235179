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
            final int ADD_PLAYER = 3;
            final int EXIT = -1;

            String command;
            String usersInput;


            while(option != EXIT) {

                try {
                    printMenu();
                    usersInput = in.nextLine();
                    option = Integer.parseInt(usersInput);
                    switch (option) {
                        case DISPLAY_BY_ID:
                            command = "DisplayById";
                            System.out.println("Enter Id:");
                            int id = in.nextInt();
                            in.nextLine();
                            command = command + " " + id;
                            socketWriter.println(command);
                            String input = socketReader.nextLine();
                            Player p = gsonParser.fromJson(input, Player.class);
                            System.out.println(p);
                            break;
                        case DISPLAY_ALL:
                            command = "DisplayAll";
                            socketWriter.println(command);
                            input = socketReader.nextLine();
                            System.out.println("BusinessObjects.Client message: Response from server: \"" + input + "\"");
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
                            String player =  gsonParser.toJson(newP);
                            command = command + " " + player;
                            socketWriter.println(command);
                            input = socketReader.nextLine();
                            System.out.println(input);
                            socketWriter.println(command);
                            input = socketReader.nextLine();
                            System.out.println("BusinessObjects.Client message: Response from server: \"" + input + "\"");
                            break;
                        case EXIT:
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
        System.out.println("Client Menu");
        System.out.println("1. Display Student By Id");
        System.out.println("2. Display All Students");
        System.out.println("3. Add Player");
        System.out.println("Enter option:");
    }
}


//  LocalTime time = LocalTime.parse(timeString); // Parse timeString -> convert to LocalTime object if required