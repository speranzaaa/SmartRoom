package RoomService.dashboardServer.HTTP;

public class HTTPRequestMessageImpl extends AbstractHTTPMessage implements HTTPRequestMessage {
	
	private String method;
	private String url;
	private String version;
	
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
	protected String getFirstLine() {
		return this.getMethod() + " " + this.getUrl() + " " + this.getHTTPVersion() + "\r\n";
	}
}
