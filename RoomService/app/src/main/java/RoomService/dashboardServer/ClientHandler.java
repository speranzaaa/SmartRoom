package RoomService.dashboardServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
	
	private boolean handle;
	private final Socket clientSocket;
	private HTTPRequestReader requestReader;
	private int count = 0;
	
	public ClientHandler(final Socket clientSocket) {
		System.out.println("New clientHandler: " + this.toString());
		this.clientSocket = clientSocket;
		this.handle = true;
		try {
			this.requestReader = new HTTPRequestReaderFromInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			System.out.print("Cannot get InputStream for client" + clientSocket);
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(handle) {
			try {
				HTTPRequestMessage msg = this.requestReader.getNextMsg();
				System.out.println(this.getName() + " requests: " + (++count));
				System.out.println(msg);
				//reply
				/*OutputStream outputStream = clientSocket.getOutputStream(); 
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
				bw.write("Ciao");*/
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopHandler() {
		this.handle = false;
	}

}
