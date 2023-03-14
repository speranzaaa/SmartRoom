package RoomService.serial;

/**
 * Pojo class representing data received through serial from the
 * Room Controller.
 */
public class RoomControllerData {
	private boolean Light;
	private int Servo;
	
	public RoomControllerData(final boolean Ligth, final int Servo) {
		this.Light = Ligth;
		this.Servo = Servo;
	}

	public boolean getLight() {
		return this.Light;
	}

	public void setLight(boolean light) {
		this.Light = light;
	}

	public int getServo() {
		return this.Servo;
	}

	public void setServo(int servo) {
		this.Servo = servo;
	}

	@Override
	public String toString() {
		return "RoomControllerData [Light=" + Light + ", Servo=" + Servo + "]";
	}
	
}
