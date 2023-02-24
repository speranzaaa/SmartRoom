package RoomService.dashboardServer.HTTP;

public class HTTPResponseMessageImpl extends AbstractHTTPMessage implements HTTPResponseMessage {
	
	private String version;
	private String statusCode;
	private String phrase;
	
	public HTTPResponseMessageImpl(String version, String statusCode, String phrase) {
		this.version = version;
		this.statusCode = statusCode;
		this.phrase = phrase;
	}

	@Override
	public String getHTTPVersion() {
		return this.version;
	}

	@Override
	public String getStatusCode() {
		return this.statusCode;
	}

	@Override
	public String getPhrase() {
		return this.phrase;
	}

	@Override
	protected String getFirstLine() {
		return this.getHTTPVersion() + " " + this.getStatusCode() + " " + this.getPhrase() + "\r\n";
	}

}
