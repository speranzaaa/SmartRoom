package RoomService.devices.actuators;

import RoomService.devices.AbstractObservableDevice;
import RoomService.devices.Status;

public class RollerBlindsImpl extends AbstractObservableDevice implements RollerBlinds {
    private int openPercentage = 0;
    private final String name;
    
    public RollerBlindsImpl(String name) {
    	this.name = name;
    }

	@Override
	public void setTo(int percentage) {
		if(percentage < 0 || percentage > 100) {
			throw new IllegalArgumentException("The percentage must be an integer between 0 and 100.");
		}
		this.openPercentage = percentage;
		this.updateObservers(this.getCurrentStatus());
	}
    
    @Override
    public void openFully() {
       this.setTo(100);;
    }

    @Override
    public void close() {
        this.setTo(0);
    }

    @Override
    public boolean isOpen() {
        return this.getOpenPercentage() != 0;
    }

	@Override
	public boolean isFullyOpen() {
		return this.getOpenPercentage() == 100;
	}

    @Override
    public boolean isClosed() {
        return this.getOpenPercentage() == 0;
    }

    @Override
    public int getOpenPercentage() {
        return this.openPercentage;
    }

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Status getCurrentStatus() {
		return new RollerBlindStatus(this.getOpenPercentage());
	}
	
	public class RollerBlindStatus implements Status {
		
		private final int percentage;
		
		public RollerBlindStatus(int percentage) {
			this.percentage = percentage;
		}
		
		@Override
		public String toString() {
			return "Rollerblind percentage: " + this.percentage;
		}
	}
}