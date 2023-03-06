package RoomService.room;

import RoomService.devices.Device;
import RoomService.devices.actuators.Actuator;

public interface Room {
	
	void addActuator(Actuator device);
	
	void removeDevice(String deviceName);
	
	Device getDevice(String deviceName);

}
