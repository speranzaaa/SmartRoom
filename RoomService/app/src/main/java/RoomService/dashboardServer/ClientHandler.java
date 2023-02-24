package RoomService.dashboardServer;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;

import RoomService.dashboardServer.HTTP.HTTPRequestMessage;
import RoomService.dashboardServer.HTTP.HTTPRequestReader;
import RoomService.dashboardServer.HTTP.HTTPRequestReaderFromInputStream;
import RoomService.dashboardServer.HTTP.HTTPResponseMessage;
import RoomService.dashboardServer.HTTP.HTTPResponseMessageImpl;
import RoomService.dashboardServer.HTTP.HTTPResponseWriter;
import RoomService.dashboardServer.HTTP.HTTPResponseWriterToOutputStream;

public class ClientHandler extends Thread {
	
	private boolean handle;
	private HTTPRequestReader requestReader;
	private HTTPResponseWriter responseWriter;
	private int count = 0;
	
	public ClientHandler(final Socket clientSocket) {
		System.out.println("New clientHandler: " + this.toString());
		this.handle = true;
		try {
			this.requestReader = new HTTPRequestReaderFromInputStream(clientSocket.getInputStream());
			this.responseWriter = new HTTPResponseWriterToOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.out.print("Cannot get the I/O Stream for client" + clientSocket);
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(handle) {
			try {
				HTTPRequestMessage msg = this.requestReader.readHTTPRequest();
				System.out.println(this.getName() + " requests: " + (++count));
				System.out.println(msg);
				//reply
				HTTPResponseMessage response = new HTTPResponseMessageImpl("HTTP/1.1", "200", "OK");
				//response.appendHeaderLine("Content-lenght: ");
				//responseWriter.writeHTTPResponse(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopHandler() {
		this.handle = false;
	}

}
