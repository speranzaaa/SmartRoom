package RoomService.dashboardServer.HTTP;

public interface HTTPMessage {
	
	public void appendHeaderLine(String line);
	
	public String getHeaderLine(String name);
	
	public void appendBody(String body);
	
	public boolean hasBody();
	
	public String getBody();

}
