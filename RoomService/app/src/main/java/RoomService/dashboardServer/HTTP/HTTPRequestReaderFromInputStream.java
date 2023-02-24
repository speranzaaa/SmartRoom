package RoomService.dashboardServer.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class HTTPRequestReaderFromInputStream implements HTTPRequestReader {
	
	private BufferedReader bufferedReader;
	
	public HTTPRequestReaderFromInputStream(InputStream clientInputStream) {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(clientInputStream, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isLineEmpty(String line) {
		return line.equals(null) || line.isBlank();
	}

	@Override
	public HTTPRequestMessage readHTTPRequest() throws IOException {
		HTTPRequestMessage message = null;
		int contentLegnth = 0;

		String line;
		if(!isLineEmpty(line = bufferedReader.readLine())) {
			try {
				message = new HTTPRequestMessageImpl(line);
			} catch (Exception e) {
				System.out.println("not a request line!");
				e.printStackTrace();
			}
			while(!isLineEmpty(line = bufferedReader.readLine())) {
				try {
					message.appendHeaderLine(line);
				} catch (Exception e) {
					e.printStackTrace();
				}
		        if (line.toLowerCase().startsWith("content-length")) {
		            contentLegnth = Integer.parseInt(line.split(":")[1].trim());
		        }
		    }
			StringBuilder requestBodyBuilder = new StringBuilder();
			if (contentLegnth > 0) {
		        int read;
		        while ((read = bufferedReader.read()) != -1) {
		            requestBodyBuilder.append((char) read);
		            if (requestBodyBuilder.length() == contentLegnth) {
		                break;
		            }
		        }
		        message.appendBody(requestBodyBuilder.toString());
			}
			return message;
		}
		return null;
	}

}
