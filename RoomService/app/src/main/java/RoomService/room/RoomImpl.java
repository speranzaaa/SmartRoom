package RoomService.room;

import java.util.HashMap;
import java.util.Map;

import RoomService.devices.Device;

public class RoomImpl implements Room {
	
	private final Map<String, Device> devices = new HashMap<>();

	@Override
	public void addDevice(Device device) {
		this.devices.put(device.getName(), device);
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
