package RoomService.devices.controls;

import java.util.Map;
import java.util.function.Consumer;
import RoomService.devices.Device;
import RoomService.devices.actuators.Light;
import RoomService.devices.actuators.RollerBlinds;
import io.vertx.core.json.JsonObject;

public class DashboardControlsHandler implements Consumer<JsonObject> {
	
	private final Map<String, Device> modelDevices;
	
	public DashboardControlsHandler(Map<String, Device> modelDevices) {
		this.modelDevices = modelDevices;
	}

	@Override
	public void accept(JsonObject obj) {
		switch(obj.getString("control")) {
			case "lightSwitch":
				((Light) this.modelDevices.get("lights-subgroup")).turnOn(obj.getJsonObject("status").getBoolean("isOn"));
				break;
			case "rollerblindSlider":
				((RollerBlinds) this.modelDevices.get("rollerblinds-subgroup")).setTo(obj.getJsonObject("status").getInteger("percentage"));
				break;
			default:
				throw new IllegalArgumentException("The json object is an unknown control." + obj.toString());	
		}
	}
}
