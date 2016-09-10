package chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Commands {

	public static int port = 0;
public static Server serverSocket;
	public static ArrayList < Client > clientsList = new ArrayList < Client > ();

	
	public static void help() {
		System.out.println("myip      ::: Show the IP of your laptop");
		System.out.println("myport    ::: Show the port number that the process runs on");
		System.out.println("connect   ::: connect <destination IP> <destination port>");
		System.out.println("list      ::: List all the connected peers");
		System.out.println("send      ::: send <connection id> <message>");
		System.out.println("terminate ::: Terminate the connection");
		System.out.println("exit      ::: Terminate all the connections and exit the program");


	}

	public static String myip() throws IOException {

		System.out.println("The IP address is " + getIP());
		return getIP();
	}


	public static int myport() {
		System.out.println("The port number is " + port);

		return port;
	}

	public static void connect(String destinationIP, int destinationPort) throws UnknownHostException, IOException, InterruptedException {

			try {

				Client client = new Client(destinationIP, destinationPort, serverSocket);

				if (client.getSocket().isConnected()) {
					clientsList.add(client);
					System.out.println("The connection to peer  " + destinationIP + " is successfully established");

				} else {
					System.out.println("ERROR, can not make this connection");

				}


			} catch (Exception e2) {
				System.out.println("ERROR, can not make this connection");

			}
	
	}

	public static void list() throws InterruptedException, UnknownHostException, IOException {

			System.out.println("id:  IP address                 Port");
			for (int i = 0; i < clientsList.size(); i++) {
				System.out.println(i + 1 + "    " + clientsList.get(i).getHost() + "               " + clientsList.get(i).getPort());

			}

	}

	public static void send(int connectionId, String message) {

		try {
			        clientsList.get(connectionId-1).processMessage(message);
					System.out.println("Message Sent");				
	
		} catch (Exception e2) {
			System.out.println("ERROR, message can not be sent to this peer");
		}


	}

	public static void terminate(String socketHost, int socketPort) throws IOException {


		for (int i = 0; i < clientsList.size(); i++) {

			if (clientsList.get(i).getHost().equals(socketHost)) {
				clientsList.get(i).getSocket().shutdownInput();
				clientsList.get(i).getSocket().shutdownOutput();
				clientsList.get(i).getSocket().close();
				clientsList.remove(i);

			}
		}

	}

	public static void setPort(int p) 
	{	
				port = p;
	}
	
	public static String getIP() throws IOException
	{
		return InetAddress.getLocalHost().getHostAddress();
	}





}