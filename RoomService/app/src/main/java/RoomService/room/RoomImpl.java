package RoomService.room;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import RoomService.activities.ActivityImpl;
import RoomService.activities.ActivityLogger;
import RoomService.devices.Device;
import RoomService.devices.actuators.Actuator;
import RoomService.devices.actuators.LightImpl.LightStatus;
import RoomService.devices.actuators.RollerBlindsImpl.RollerBlindStatus;
import RoomService.serial.SerialCommChannel;
import RoomService.serial.SerialStatusMessenger;

public class RoomImpl implements Room {
	
	private final Map<String, Device> devices = new HashMap<>();
	private final ActivityLogger activityLogger;
	private final SerialStatusMessenger messenger;
	
	public RoomImpl(ActivityLogger activityLogger) throws Exception {
		this.activityLogger = activityLogger;		
		this.messenger = new SerialStatusMessenger(new SerialCommChannel());
	}

	@Override
	public void addActuator(Actuator actuator) {
		actuator.addStatusObserver((status)->this.activityLogger.logActivity(new ActivityImpl(actuator, status)));
		// Add listener to send status update via serial port
		if (actuator.getName().equals("lights-subgroup")) {
			actuator.addStatusObserver((status) -> this.messenger.sendUpdate((LightStatus)status));
		} else {
			actuator.addStatusObserver((status) -> this.messenger.sendUpdate((RollerBlindStatus)status));
		}
		this.devices.put(actuator.getName(), actuator);
	}

	@Override
	public void removeDevice(String deviceName) {
		this.devices.remove(deviceName);
	}

	@Override
	public Device getDevice(String deviceName) {
		return this.devices.get(deviceName);
	}

	@Override
	public Map<String, Device> getDevices() {
		return Collections.unmodifiableMap(this.devices);
	}

}
