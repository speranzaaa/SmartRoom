package RoomService.devices.controls;

import RoomService.devices.Status;

public class SwitchImpl implements Switch {
	
	private SwitchStatus status;
	private final String name;
	
	public SwitchImpl(String name, boolean on) {
		this.name = name;
	}

	@Override
	public Status getCurrentStatus() {
		return this.status;
	}

	@Override
	public void turnOn(boolean on) {
		this.status.setOn(on);
	}

	@Override
	public String getName() {
		return this.name;
	}

}
