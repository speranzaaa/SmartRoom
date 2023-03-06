package RoomService.room;

import RoomService.devices.Device;

public interface Room {
	
	void addDevice(Device device);
	
	void removeDevice(String deviceName);
	
	Device getDevice(String deviceName);

}
