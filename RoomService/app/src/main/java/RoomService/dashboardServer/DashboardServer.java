package RoomService.dashboardServer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import com.google.gson.Gson;

import RoomService.activities.usage.UsageReporter;
import RoomService.room.Room;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class DashboardServer extends AbstractVerticle {
	
	private final static String RESOLUTION_PATH = "src/main/resources/roomDashboard";
	
	private final Vertx vertx;
	private final HttpServer server;
	private final int port;
	private final Router router;
	private final Set<Consumer<JsonObject>> controlsObservers = new HashSet<>();
	private MessageConsumer<JsonObject> usageReporter;
	private Room room;
	
	public DashboardServer(int port) {
		this.port = port;
		this.vertx = Vertx.vertx();
		this.server = this.vertx.createHttpServer();
		this.router = Router.router(this.vertx);
		this.setUpRoutes(this.router);
		this.vertx.eventBus().consumer("control", (message) -> {
			JsonObject jsonMessage = new JsonObject(message.body().toString());
			controlsObservers.stream().forEach((consumer) -> {
				consumer.accept(jsonMessage);
			});
		});
		this.vertx.eventBus().consumer("init", (message)-> {
			JsonObject reply = new JsonObject();
			room.getDevices().forEach((name, device)->{
				reply.put(name, new JsonObject(new Gson().toJson(device.getCurrentStatus())));
			});
			message.reply(reply);
		});
	}

	private void setUpRoutes(Router router) {
		router.route("/init").handler(new InitHandler());
		router.route("/activities").produces("text/event-stream").handler(new SSEHandler(this.vertx.eventBus()));
		router.route("/control").handler(new ControlHandler());
		router.route("/usage").handler(new UsageHandler());
		router.route().handler(new FileRequestHandler(RESOLUTION_PATH));
	}

	@Override
	public void start() {
		this.server.requestHandler(this.router).listen(this.port);
	}
	
	public void sendSSEMessage(SSEMessage message) {
		this.vertx
			.eventBus()
			.publish("sse", new JsonObject()
							.put("eventName", message.getEventName())
							.put("messageBody", message.getMessageBody()));
	}
	
	public void addControlObserver(Consumer<JsonObject> consumer) {
		this.controlsObservers.add(consumer);
	}
	
	public void setUsageReporter(UsageReporter usageReporter) {
		if(this.usageReporter != null) {
			this.usageReporter.unregister();
		}
		this.usageReporter = this.vertx.eventBus().consumer("usage", usageReporter);
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
}
