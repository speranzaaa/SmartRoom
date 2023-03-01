package RoomService.devices.controls;

import RoomService.devices.Status;

public interface SwitchStatus extends Status {
	
	void setOn(boolean on);
	
	boolean isOn();
	
}
