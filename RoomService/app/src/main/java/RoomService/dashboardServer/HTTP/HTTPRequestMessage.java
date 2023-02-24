package RoomService.dashboardServer.HTTP;

public interface HTTPRequestMessage extends HTTPMessage {
	
	public String getMethod();
	
	public String getUrl();
	
	public String getHTTPVersion();
	
}
