package RoomService.serial;

import RoomService.devices.actuators.LightImpl.LightStatus;
import RoomService.devices.actuators.RollerBlindsImpl.RollerBlindStatus;

/**
 * Interface that generates Json formatted commands from a device's status
 */
public interface CommandGenerator {
	/**
	 * Creates a {@link String} command from a {@link LightStatus}.
	 * @param status the {@link LightStatus} the command will be generated from.
	 * @return the {@link String} representing the command in Json format.
	 */
	public String createCommand(LightStatus status);

	/**
	 * Creates a {@link String} command from a {@link RollerBlindStatus}.
	 * @param status the {@link RollerBlindStatus} the command will be generated from.
	 * @return the {@link String} representing the commnad in Json format.
	 */
	public String createCommand(RollerBlindStatus status);
}
