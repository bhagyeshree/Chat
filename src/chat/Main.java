package chat;

import java.io.IOException;
import java.util.Scanner;

//we can have all the methods from the class commands by extending it in this class

public class Main extends Commands {

	public static Server server;

	public static void main(String args[]) throws IOException, InterruptedException {

	
		//Check if the port number is correct before running the program
		
	try{
	
		int port = Integer.parseInt(args[0]);
		
		//the prot number should be between 1 and 65535
		if(port > 0 && port <= 65535 )
		{
			setPort(port);

		}else
		{
			System.out.println("ERROR, Try again and set a correct PORT number");
			Runtime.getRuntime().exit(0);


		}
	
	} catch (Exception e6) {
		
		// if there is any error when setting the port number
		
		System.out.println("ERROR, Try again and set a correct PORT number");

		//close the project 
		Runtime.getRuntime().exit(0);
	}
		
	
	Thread.sleep(500);
	
	
     //Run the server in the background, waiting for incoming connections
		new Thread(new Runnable() {@Override
			public void run() {
				try {
					server = new Server(port);
					

				} catch (IOException e) {} catch (InterruptedException e) {

				}
			}
		}).start();

		


		//Run the listener for the commands from the user
		while (true) {
			
			Thread.sleep(500);

			// getting the commands from the user
			System.out.print(">> ");
			Scanner scanner = new Scanner(System. in );
			String command = scanner.nextLine();

			// this is only for send, terminate and connect since they take arguments
			//We divided the string to elements in an array
			if (command.contains(" ")) {

				try {
					String[] info = command.split(" ");
					if (info[0].equals("connect")) {
						connect(info[1], Integer.parseInt(info[2]));
					}

				} catch (Exception e7) {
					System.out.println("The Connection inputs are not valid");
				}

				try {
					String[] info = command.split(" ");
					if (info[0].equals("send")) {
						String message = "";

						for (int i = 2; i < info.length; i++) {
							message = message + info[i] + " ";
						}
						send(Integer.parseInt(info[1]), message);
					}

				} catch (Exception e6) {
					System.out.println("The Message could not be sent");

				}

				try {

					String[] info = command.split(" ");

					if (info[0].equals("terminate")) {
						int socketPort = clientsList.get(Integer.parseInt(info[1]) - 1).getPort();
						String socketHost = clientsList.get(Integer.parseInt(info[1]) - 1).getHost();

						terminate(socketHost, socketPort);

					}

				} catch (Exception e6) {
					System.out.println("Can not close this connection");

				}

			} else {

				if (command.equals("help")) {
					help();

				} else if (command.equals("exit")) {
					Runtime.getRuntime().exit(0);

				} else if (command.equals("myip")) {
					myip();

				} else if (command.equals("myport")) {
					myport();

				} else if (command.equals("list")) {
					list();

				}

			}

			System.out.println();

		}

	}

}