package RoomService.logic;

import java.time.LocalTime;
import RoomService.devices.actuators.Light;
import RoomService.devices.actuators.RollerBlinds;
import RoomService.mqtt.SensorBoardData;
import RoomService.room.Room;

/**
 * Implementation of the {@link LogicController} interface.
 */
public class LogicControllerImpl implements LogicController {

	private static final LocalTime MORNING_THRESHOLD = LocalTime.of(8, 0);
	private static final LocalTime EVENING_THRESHOLD = LocalTime.of(19, 0);
	private final Room room;
	private boolean hasFirstPersonEntered = false;
	
	/**
	 * Creates a new instance of the {@link LogicControllerImpl} class.
	 */
	public LogicControllerImpl(Room room) {
		this.room = room;
	}
	
	@Override
	public void updateRoom(final SensorBoardData data) {
		System.out.println("Received: " + data.toString());
		// Control the lights
		this.updateLights(data);
		// Control the blinds
		this.updateRollerBlinds(data);
	}
	
	/**
	 * Updates the status of the lights.
	 * @param presence {@link SensorBoardData}
	 */
	private void updateLights(final SensorBoardData data) {
		Light lightsSubgroup = (Light) this.room.getDevice("lights-subgroup");
		// If no one is in the room turn off the light
		if (!data.isPresenceDetected() && lightsSubgroup.isOn()) {
			lightsSubgroup.turnOn(false);
			System.out.println("No one in the room, turning off lights.");
		} else if (!lightsSubgroup.isOn() && data.isPresenceDetected() && !data.isDay()) {
			// If someone enters the room and the room was dark, turn it on
			lightsSubgroup.turnOn(true);
			System.out.println("Presence detected, turning on lights.");
		}
	}
	
	/**
	 * Updates the status of the roller blinds
	 * @param data {@link SensorBoardData} received from the sensor board
	 */
	private void updateRollerBlinds(final SensorBoardData data) {
		final LocalTime currentTime = LocalTime.now();
		RollerBlinds rollerblindsSubgroup = (RollerBlinds) this.room.getDevice("rollerblinds-subgroup");
		/*
		 * If someone enters the room between MORNING_THRESHOLD and EVENING_THRESHOLD
		 * for the first time today.
		 */
		if (currentTime.isAfter(MORNING_THRESHOLD)
				&& currentTime.isBefore(EVENING_THRESHOLD)
				&& !this.hasFirstPersonEntered) {
			this.hasFirstPersonEntered = true;
			rollerblindsSubgroup.openFully();
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
			rollerblindsSubgroup.close();
			System.out.println("No one in the room after 8:00 PM, closing blinds.");
		}
	}

}
