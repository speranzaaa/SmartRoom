package RoomService.devices.controls;

public class SwitchStatusImpl implements SwitchStatus {
	
	private boolean on;

	@Override
	public void setOn(boolean on) {
		this.on = on;
	}

	@Override
	public String getJson() {
		return null;
	}

}