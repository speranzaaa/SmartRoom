package RoomService.serial;

/**
 * Simple interface for an asynchronous message communication channel
 */
public interface CommChannel {
	/**
	 * Sends a message on the communication channel
	 * @param msg the {@link String} that will be sent
	 */
	void sendMsg(String msg);
	
	/**
	 * Receives a message from the communication channel
	 * @return a {@link String} that representing the message received
	 * @throws InterruptedException
	 */
	String receiveMsg() throws InterruptedException;
	
	/**
	 * Checks if there are messages that can be read
	 * @return True if a message can be read on the channel, false otherwise
	 */
	boolean isMsgAvailable();
}
