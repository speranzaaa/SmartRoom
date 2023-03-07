package RoomService.devices.controls;

import java.util.function.Consumer;
import RoomService.devices.actuators.Light;
import RoomService.devices.actuators.RollerBlinds;
import RoomService.room.Room;
import io.vertx.core.json.JsonObject;

public class DashboardControlHandler implements Consumer<JsonObject> {
	
	private final Room room;
	
	public DashboardControlHandler(Room room) {
		this.room = room;
	}

	@Override
	public void accept(JsonObject obj) {
		switch(obj.getString("control")) {
			case "lightSwitch":
				((Light) this.room.getDevice("lights-subgroup")).turnOn(obj.getJsonObject("status").getBoolean("isOn"));
				break;
			case "rollerblindSlider":
				((RollerBlinds) this.room.getDevice("rollerblinds-subgroup")).setTo(obj.getJsonObject("status").getInteger("percentage"));
				break;
			default:
				throw new IllegalArgumentException("The json object is an unknown control." + obj.toString());	
		}
	}
}
