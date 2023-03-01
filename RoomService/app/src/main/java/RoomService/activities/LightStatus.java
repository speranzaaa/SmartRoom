package RoomService.activities;

import RoomService.devices.Status;

public class LightStatus implements Status {
	
	private final boolean on;
	
	public LightStatus(final boolean on) {
		this.on = on;
	}

	@Override
	public String toString() {
		return on ? "On" : "Off";
	}
}
