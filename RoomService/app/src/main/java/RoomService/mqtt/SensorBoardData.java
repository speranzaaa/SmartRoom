package RoomService.mqtt;

/**
 * Class that represents the data received from the sensor board
 * through an mqtt message.
 */
public class SensorBoardData {
	private Boolean day;
	private Boolean presence;
	
	public SensorBoardData(Boolean day, Boolean presence) {
		this.day = day;
		this.presence = presence;
	}
	
	public Boolean isDay() {
		return this.day;
	}
	
	public Boolean isPresenceDetected() {
		return this.presence;
	}

	@Override
	public String toString() {
		return "SensorBoardData [day=" + day + ", presence=" + presence + "]";
	}
}
