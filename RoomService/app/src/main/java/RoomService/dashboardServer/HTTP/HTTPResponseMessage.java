package RoomService.dashboardServer.HTTP;

public interface HTTPResponseMessage extends HTTPMessage {
	
	public String getHTTPVersion();
	
	public String getStatusCode();
	
	public String getPhrase();
	
}
