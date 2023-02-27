package RoomService.activities;

import java.util.Date;
import java.util.Objects;

import RoomService.actuators.Device;

public class ActivityImpl implements Activity {
	
	private final Device device;
	private final Date timestamp;
	private final Status status;
	
	public ActivityImpl(final Device device, final Status status) {
		this.device = device;
		this.status = status;
		this.timestamp = new Date();
	}

	public Device getDevice() {
		return this.device;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public Status getStatus() {
		return this.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(device, timestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ActivityImpl)) {
			return false;
		}
		ActivityImpl other = (ActivityImpl) obj;
		return Objects.equals(device, other.device) && Objects.equals(timestamp, other.timestamp);
	}
}
