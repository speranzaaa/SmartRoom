package RoomService.room;

import java.util.Map;

import RoomService.devices.Device;
import RoomService.devices.actuators.Actuator;

public interface Room {
	
	void addActuator(Actuator device);
	
	void removeDevice(String deviceName);
	
	Device getDevice(String deviceName);
	
	Map<String, Device> getDevices();
	
	/**
	 * Updates the room's actuators' statuses with the data
	 * received from the serial port.
	 */
	void updateFromSerial();

}
