package RoomService.mqtt;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class TestMQTTClient {
	
	public static void main(String[] args) throws Exception {		
		Vertx vertx = Vertx.vertx();
		MQTTAgent agent = new MQTTAgent();
		vertx.deployVerticle(agent);
	}
		
}
