package RoomService.dashboardServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class DashboardServer {
	private final int port;
	private ServerSocket socket;
	private Map<Date, ClientHandler> clientHandlers = new HashMap<>();
	
	public DashboardServer(final int port) {
		this.port = port;
		try {
			this.socket = new ServerSocket(this.port);
			System.out.println("socket creato");
		} catch (IOException e) {
			System.out.println("Errore durante la creazione del server!");
			e.printStackTrace();
		}
	}
	
	public void runServer() {
		while(true) {
			try {
				System.out.println("In attesa di un nuovo client");
				Socket clientSocket = this.socket.accept();
				ClientHandler clientHandler = new ClientHandler(clientSocket);
				clientHandlers.put(new Date(), clientHandler);
				System.out.println("Client " + clientSocket.toString() + " accettato e gestito da " + clientHandler.toString());
				clientHandler.start();
			} catch (IOException e) {
				System.out.println("Cannot accept client!");
				e.printStackTrace();
			}
		}
	}

}
