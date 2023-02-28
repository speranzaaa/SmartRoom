package RoomService.dashboardServer;

public interface SSEMessage {
	
	boolean hasEventName();
	
	String getEventName() ;
	
	String getMessageBody();
	
	String getFormattedMessage();

}
