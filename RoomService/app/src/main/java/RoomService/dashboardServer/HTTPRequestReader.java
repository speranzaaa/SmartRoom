package RoomService.dashboardServer;

import java.io.IOException;

public interface HTTPRequestReader {
	
	public HTTPRequestMessage getNextMsg() throws IOException;

}
