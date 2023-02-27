package RoomService.activities;

import java.util.Date;

import RoomService.actuators.Device;

public interface Activity {
	
	Device getDevice();

	Date getTimestamp();

	Status getStatus();
}
