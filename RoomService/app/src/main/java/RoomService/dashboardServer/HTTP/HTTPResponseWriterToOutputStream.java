package RoomService.dashboardServer.HTTP;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class HTTPResponseWriterToOutputStream implements HTTPResponseWriter {
	
	private BufferedWriter bufferedWriter;
	
	public HTTPResponseWriterToOutputStream(OutputStream clientOutputStream) {
		this.bufferedWriter =  new BufferedWriter(new OutputStreamWriter(clientOutputStream));
	}

	@Override
	public void writeHTTPResponse(HTTPResponseMessage message) {
		try {
			this.bufferedWriter.write(message.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
