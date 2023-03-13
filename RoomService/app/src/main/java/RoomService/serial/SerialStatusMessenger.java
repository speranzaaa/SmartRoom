package RoomService.serial;

import RoomService.devices.actuators.LightImpl.LightStatus;
import RoomService.devices.actuators.RollerBlindsImpl.RollerBlindStatus;

/**
 * Class that sends update messages via serial port based on a
 * given actuator status.
 */
public class SerialStatusMessenger {
	private final CommandGenerator generator = new CommandGeneratorImpl();
	private final CommChannel channel;
	
	public SerialStatusMessenger(final CommChannel channel){
		this.channel = channel;
	}
	
	
	/**
	 * Sends a message via Serial port that updates the actuators
	 * in the control board.
	 * @param status the {@link LightStatus} that will generate the message
	 */
	public String sendUpdate(final LightStatus status) {
		System.out.println("status in sendUpdate: " + status);
		final String msg = this.generator.createCommand(status);
		this.channel.sendMsg(msg);
		return msg;
	}
	
	/**
	 * Sends a message via Serial port that updates the roller blinds
	 * actuator in the control board.
	 * @param status the {@link RollerBlindStatus} that will generate the message
	 */
	public String sendUpdate(final RollerBlindStatus status) {
		final String msg = this.generator.createCommand(status);
		this.channel.sendMsg(msg);
		return msg;
	}
}
