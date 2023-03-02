package RoomService.logic;

import java.time.LocalTime;
import RoomService.devices.actuators.Light;
import RoomService.devices.actuators.LightImpl;
import RoomService.devices.actuators.RollerBlinds;
import RoomService.devices.actuators.RollerBlindsImpl;
import RoomService.mqtt.SensorBoardData;

/**
 * Implementation of the {@link LogicController} interface.
 */
public class LogicControllerImpl implements LogicController{

	private static final LocalTime MORNING_THRESHOLD = LocalTime.of(8, 0);
	private static final LocalTime EVENING_THRESHOLD = LocalTime.of(19, 0);
	
	private final Light lightLed;
	private final RollerBlinds rollerBlinds;
	private boolean hasFirstPersonEntered = false;
	
	/**
	 * Creates a new instance of the {@link LogicControllerImpl} class.
	 */
	public LogicControllerImpl() {
		this.lightLed = new LightImpl("lights-subgroup");
		this.rollerBlinds = new RollerBlindsImpl("rollerblinds-subgroup");
	}
	
	@Override
	public void updateRoom(final SensorBoardData data) {
		// Control the lights
		this.updateLights(data.isPresenceDetected());
		// Control the blinds
		this.updateRollerBlinds(data);
	}
	
	/**
	 * Updates the status of the lights.
	 * @param presence boolean value indicating presence in the room
	 */
	private void updateLights(boolean presence) {
		// If no one is in the room turn off the light
		if (!presence) {
			this.lightLed.turnOn(false);
			System.out.println("No one in the room, turning off lights.");
		} else if (!this.lightLed.isOn()) {
			// If someone enters the room and the light was off, turn it on
			this.lightLed.turnOn(true);
			System.out.println("Presence detected, turning on lights.");
		}
	}
	
	/**
	 * Updates the status of the roller blinds
	 * @param data {@link SensorBoardData} received from the sensor board
	 */
	private void updateRollerBlinds(final SensorBoardData data) {
		final LocalTime currentTime = LocalTime.now();
		/*
		 * If someone enters the room between MORNING_THRESHOLD and EVENING_THRESHOLD
		 * for the first time today.
		 */
		if (currentTime.isAfter(MORNING_THRESHOLD)
				&& currentTime.isBefore(EVENING_THRESHOLD)
				&& !this.hasFirstPersonEntered) {
			this.hasFirstPersonEntered = true;
			this.rollerBlinds.openFully();
			System.out.println("First person has entered the room, opening blinds.");
		}
		/*
		 * After EVENING_THRESHOLD close the blinds after the last person leaves
		 * or if no one is in the room.
		 */
		if ((currentTime.isAfter(EVENING_THRESHOLD)
				|| currentTime.isBefore(MORNING_THRESHOLD))
				&& !data.isPresenceDetected() && this.hasFirstPersonEntered) {
			this.hasFirstPersonEntered = false;
			this.rollerBlinds.close();
			System.out.println("No one in the room after 8:00 PM, closing blinds.");
		}
	}

}
