package RoomService.dashboardServer;

import java.util.HashMap;
import java.util.Map;

public class HTTPRequestMessageImpl implements HTTPRequestMessage {
	
	private String method;
	private String url;
	private String version;
	private Map<String, String> headerLines = new HashMap<>();
	private String body = null;
	
	public HTTPRequestMessageImpl(String requestLine) throws Exception {
		String[] fields = requestLine.split(" ");
		if(fields.length != 3) {
			throw new Exception("Invalid request line");
		} else {
			this.method = fields[0];
			this.url = fields[1];
			this.version = fields[2];
		}
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public String getHTTPVersion() {
		return this.version;
	}

	@Override
	public void appendHeaderLine(String line) throws Exception {
		String[] fields = line.split(": ");
		if(fields.length != 2) {
			throw new Exception("Invalid header line");
		} else {
			this.headerLines.put(fields[0].toLowerCase(), fields[1]);	
		}
		
	}

	@Override
	public String getHeaderLine(String name) {
		return this.headerLines.get(name);
	}

	@Override
	public void appendBody(String body) {
		this.body = body;
	}
	
	@Override
	public boolean hasBody() {
		return this.body != null && !this.body.isBlank();
	}

	@Override
	public String getBody() {
		return this.body;
	}
	
	@Override
	public String toString() {
		StringBuilder msgBuilder = new StringBuilder();
		msgBuilder.append(this.method + " " + this.url + " " + this.version + "\r\n");
		this.headerLines.forEach((name, value) -> {
			msgBuilder.append(name + ": " + value + "\r\n");
		});
		if(this.hasBody()) {
			msgBuilder.append("\r\n" + this.body);
		}
		return msgBuilder.toString();
	}
}
