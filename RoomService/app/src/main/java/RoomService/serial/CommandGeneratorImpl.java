package RoomService.serial;

import com.google.gson.Gson;

import RoomService.devices.actuators.LightImpl.LightStatus;
import RoomService.devices.actuators.RollerBlindsImpl.RollerBlindStatus;

public class CommandGeneratorImpl implements CommandGenerator {

	private final Gson gson = new Gson();
	
	@Override
	public String createCommand(LightStatus status) {
		final String statusString = status.toString();
		final Boolean statusBool = statusString.substring(6) == "On"
				? Boolean.TRUE : Boolean.FALSE;
		final Command<Boolean> command = new Command<Boolean>("Lights", statusBool);
		return this.gson.toJson(command);
	}

	@Override
	public String createCommand(RollerBlindStatus status) {
		final String statusString = status.toString();
		final Integer statusInt = Integer.valueOf(statusString.substring(24));
		final Command<Integer> command = new Command<Integer>("RollerBlinds", statusInt);
		return this.gson.toJson(command);
	}

	public class Command<T> {
		private String deviceName;
		private T deviceValue;
		
		public Command(final String name, final T value) {
			this.deviceName = name;
			this.deviceValue = value;
		}

		public String getDeviceName() {
			return deviceName;
		}

		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}

		public T getDeviceValue() {
			return deviceValue;
		}

		public void setDeviceValue(T deviceValue) {
			this.deviceValue = deviceValue;
		}
		
		
	}
}
