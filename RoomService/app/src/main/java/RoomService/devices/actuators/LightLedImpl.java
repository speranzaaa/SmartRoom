package RoomService.devices.actuators;
import RoomService.devices.AbstractObservableDevice;
import RoomService.devices.Status;

public class LightLedImpl extends AbstractObservableDevice implements LightLed {

    private boolean isOn = false;
    private final String name;
    
    public LightLedImpl(String deviceName) {
    	this.name = deviceName;
    }

    @Override
    public void turnOn() {
        isOn = true;
        this.updateObservers(this.getCurrentStatus());
    }

    @Override
    public void turnOff() {
        isOn = false;
        this.updateObservers(this.getCurrentStatus());
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public boolean isOff() {
        return !isOn;
    }

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Status getCurrentStatus() {
		return new LightStatus(this.isOn());
	}
	
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
}