package RoomService.mqtt;
import RoomService.logic.LogicControllerImpl;
import io.vertx.core.Vertx;

public class TestMQTTClient {
	
	public static void main(String[] args) throws Exception {		
		Vertx vertx = Vertx.vertx();
		MQTTAgent agent = new MQTTAgent(new LogicControllerImpl());
		vertx.deployVerticle(agent);
	}
		
}
