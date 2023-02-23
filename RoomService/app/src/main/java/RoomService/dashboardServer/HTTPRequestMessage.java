package RoomService.dashboardServer;

public interface HTTPRequestMessage {
	
	public String getMethod();
	
	public String getUrl();
	
	public String getHTTPVersion();

	public void appendHeaderLine(String line) throws Exception;
	
	public String getHeaderLine(String name);
	
	public void appendBody(String body);
	
	public boolean hasBody();
	
	public String getBody();
}
