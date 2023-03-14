package RoomService.serial;

import RoomService.room.Room;

/**
 * Class that updates a {@link Room}'s data based on
 * data received from serial port.
 */
public class SerialUpdater extends Thread {
	private static long SLEEP_LENGTH = 1000;
	private final Room room;
	
	public SerialUpdater(final Room room) {
		this.room = room;
	}
	
	@Override
	public void run() {
		while (true) {
			room.updateFromSerial();
			try {
				Thread.sleep(SerialUpdater.SLEEP_LENGTH);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
