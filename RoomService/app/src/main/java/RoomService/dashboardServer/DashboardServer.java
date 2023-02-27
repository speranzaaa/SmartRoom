package RoomService.dashboardServer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class DashboardServer extends AbstractVerticle {
	
	private final static String RESOLUTION_PATH = "src/main/resources/roomDashboard";
	
	private final Vertx vertx;
	private final HttpServer server;
	private final int port;
	private final Router router;
	
	public DashboardServer(int port) {
		this.port = port;
		this.vertx = Vertx.vertx();
		this.server = this.vertx.createHttpServer();
		this.router = Router.router(this.vertx);
		this.setUpRoutes(this.router);
	}

	private void setUpRoutes(Router router) {
		router.route("/events").produces("text/event-stream").handler(new SSEHandler(this.vertx.eventBus()));
		router.route().handler(new FileRequestHandler(RESOLUTION_PATH));
	}

	@Override
	public void start() {
		this.server.requestHandler(this.router).listen(this.port);
	}
	
	public void updateClients(String msg) {
		this.vertx.eventBus().publish("events", msg);
	}
}
