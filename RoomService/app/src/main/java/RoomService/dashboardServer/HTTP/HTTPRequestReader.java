package RoomService.dashboardServer.HTTP;

import java.io.IOException;

public interface HTTPRequestReader {
	
	public HTTPRequestMessage readHTTPRequest() throws IOException;

}
