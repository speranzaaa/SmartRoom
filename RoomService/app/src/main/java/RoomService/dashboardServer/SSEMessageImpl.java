package RoomService.dashboardServer;

import java.util.Objects;
import java.util.Optional;

public class SSEMessageImpl implements SSEMessage {
	
	private final static String LF = "\n";
	
	private final Optional<String> eventName;
	private final String messageBody;
	
	public SSEMessageImpl(String eventName, String messageBody) {
		this.eventName = Optional.ofNullable(eventName);
		this.messageBody = Objects.requireNonNull(messageBody);
	}
	
	public boolean hasEventName() {
		return this.eventName.isPresent();
	}
	
	public String getEventName() {
		return this.eventName.get();
	}
	
	public String getMessageBody() {
		return this.messageBody;
	}
	
	public String getFormattedMessage() {
		StringBuilder msgBuilder = new StringBuilder();
		if(this.hasEventName()) {
			msgBuilder.append("event: " + this.getEventName() + LF);
		}
		msgBuilder.append("data: " + this.getMessageBody() + LF + LF);
		return msgBuilder.toString();
	}

	@Override
	public String toString() {
		return "SSEMessage: [eventName: " + eventName.get() + ", messageBody: " + messageBody + "]";
	}

}
