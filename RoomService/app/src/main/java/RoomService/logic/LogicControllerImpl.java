package RoomService.logic;

import java.time.LocalTime;
import RoomService.actuators.LightLed;
import RoomService.actuators.LightLedImpl;
import RoomService.actuators.RollerBlinds;
import RoomService.actuators.RollerBlindsImpl;
import RoomService.mqtt.SensorBoardData;

/**
 * Implementation of the {@link LogicController} interface.
 */
public class LogicControllerImpl implements LogicController{

	private static final LocalTime MORNING_THRESHOLD = LocalTime.of(8, 0);
	private static final LocalTime EVENING_THRESHOLD = LocalTime.of(19, 0);
	
	private final LightLed lightLed;
	private final RollerBlinds rollerBlinds;
	private boolean hasFirstPersonEntered = false;
	
	/**
	 * Creates a new instance of the {@link LogicControllerImpl} class.
	 */
	public LogicControllerImpl() {
		this.lightLed = new LightLedImpl();
		this.rollerBlinds = new RollerBlindsImpl();
	}
	
	@Override
	public void updateRoom(SensorBoardData data) {
		// Control the lights
		// If no one is in the room turn off the light
		if (!data.isPresenceDetected()) {
			this.lightLed.turnOff();
			System.out.println("No one in the room, turning off lights.");
		} else if (this.lightLed.isOff()){
			// If someone enters the room and the light was off, turn it on
			this.lightLed.turnOn();
			System.out.println("Presence detected, turning on lights.");
		}
		// Control the blinds
		final LocalTime currentTime = LocalTime.now();
		/*
		 * If someone enters the room between MORNING_THRESHOLD and EVENING_THRESHOLD
		 * for the first time today.
		 */
		if (currentTime.isAfter(MORNING_THRESHOLD)
				&& currentTime.isBefore(EVENING_THRESHOLD)
				&& !this.hasFirstPersonEntered) {
			this.hasFirstPersonEntered = true;
			this.rollerBlinds.open();
			System.out.println("First person has entered the room, opening blinds.");
		}
		/*
		 * After EVENING_THRESHOLD close the blinds after the last person leaves
		 * or if no one is in the room.
		 */
		if ((currentTime.isAfter(EVENING_THRESHOLD)
				|| currentTime.isBefore(MORNING_THRESHOLD))
				&& !data.isPresenceDetected()) {
			this.hasFirstPersonEntered = false;
			this.rollerBlinds.close();
			System.out.println("No one in the room after 8:00 PM, closing blinds.");
		}
	}

}
