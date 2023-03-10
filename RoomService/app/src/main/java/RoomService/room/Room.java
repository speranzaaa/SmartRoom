package RoomService.room;

import java.util.Map;

import RoomService.devices.Device;
import RoomService.devices.actuators.Actuator;

public interface Room {
	
	void addActuator(Actuator device);
	
	void removeDevice(String deviceName);
	
	Device getDevice(String deviceName);
	
	Map<String, Device> getDevices();

}
