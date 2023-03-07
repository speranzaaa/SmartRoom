package RoomService.room;

import java.util.HashMap;
import java.util.Map;

import RoomService.activities.ActivityImpl;
import RoomService.activities.ActivityLogger;
import RoomService.devices.Device;
import RoomService.devices.actuators.Actuator;

public class RoomImpl implements Room {
	
	private final Map<String, Device> devices = new HashMap<>();
	private final ActivityLogger activityLogger;
	
	public RoomImpl(ActivityLogger activityLogger) {
		this.activityLogger = activityLogger;
	}

	@Override
	public void addActuator(Actuator actuator) {
		actuator.addStatusObserver((status)->this.activityLogger.logActivity(new ActivityImpl(actuator, status)));
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

}
