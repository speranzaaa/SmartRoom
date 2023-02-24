package RoomService.dashboardServer.HTTP;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHTTPMessage implements HTTPMessage {

	private Map<String, String> headerLines = new HashMap<>();
	private String body = null;

	@Override
	public void appendHeaderLine(String line) {
		String[] fields = line.split(": ");
		if(fields.length != 2) {
			throw new RuntimeException("Invalid header line");
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
	
	protected abstract String getFirstLine();
	
	@Override
	public String toString() {
		StringBuilder msgBuilder = new StringBuilder();
		msgBuilder.append(this.getFirstLine());
		this.headerLines.forEach((name, value) -> {
			msgBuilder.append(name + ": " + value + "\r\n");
		});
		if(this.hasBody()) {
			msgBuilder.append("\r\n" + this.getBody());
		}
		return msgBuilder.toString();
	}
}
