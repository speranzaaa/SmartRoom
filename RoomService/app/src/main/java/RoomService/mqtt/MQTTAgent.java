package RoomService.mqtt;

import com.google.gson.Gson;

import RoomService.logic.LogicController;
import io.vertx.core.AbstractVerticle;
import io.vertx.mqtt.MqttClient;

/*
 * MQTT Agent
 */
public class MQTTAgent extends AbstractVerticle {
	
	private final LogicController logicController;
	
	public MQTTAgent(final LogicController logicController) {
		this.logicController = logicController;
	}

	@Override
	public void start() {		
		MqttClient client = MqttClient.create(vertx);
		final Gson gson = new Gson();
		
		client.connect(1883, "broker.mqtt-dashboard.com", c -> {

			this.log("connected");
			
			this.log("subscribing...");
			client.publishHandler(s -> {
				final String received = s.payload().toString();
				this.logicController.updateRoom(gson.fromJson(received, SensorBoardData.class));
			})
			.subscribe("room-sensor-board", 2);
		});
	}
	

	private void log(String msg) {
		System.out.println("[MQTT AGENT] "+msg);
	}

}