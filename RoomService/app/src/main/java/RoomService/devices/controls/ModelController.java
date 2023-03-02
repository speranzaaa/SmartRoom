package RoomService.devices.controls;

import java.util.Map;
import java.util.function.Consumer;

import RoomService.devices.Device;
import RoomService.devices.actuators.LightLed;
import io.vertx.core.json.JsonObject;

public class ModelController implements Consumer<JsonObject> {
	
	private final Map<String, Device> modelDevices;
	
	public ModelController(Map<String, Device> modelDevices) {
		this.modelDevices = modelDevices;
	}

	@Override
	public void accept(JsonObject obj) {
		switch(obj.getString("control")) {
			case "lightSwitch":
				if(obj.getJsonObject("status").getBoolean("isOn")) {
					((LightLed) this.modelDevices.get("lights-subgroup")).turnOn();
				} else {
					((LightLed) this.modelDevices.get("lights-subgroup")).turnOff();
				}
				break;
			case "rollerblindSlider":
				System.out.println(obj.toString());
				break;
			default:
				throw new IllegalArgumentException("The json object is an unknown control." + obj.toString());	
		}
	}

}
