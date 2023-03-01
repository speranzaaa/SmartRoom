package RoomService.activities;

import java.util.Date;
import RoomService.devices.Device;
import RoomService.devices.Status;

public interface Activity {
	
	Device getDevice();

	Date getTimestamp();

	Status getStatus();
}
