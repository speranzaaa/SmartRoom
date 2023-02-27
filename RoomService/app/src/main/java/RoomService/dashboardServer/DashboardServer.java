package RoomService.dashboardServer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;

public class DashboardServer extends AbstractVerticle {
	
	private final static String RES_PATH = "src/main/resources/roomDashboard";
	
	private final Vertx vertix;
	private final HttpServer server;
	private final int port;
	private final Router router;
	
	public DashboardServer(int port) {
		this.port = port;
		this.vertix = Vertx.vertx();
		this.server = this.vertix.createHttpServer();
		this.router = Router.router(this.vertix);
		this.setUpRoutes(this.router);
	}

	private void setUpRoutes(Router router) {
		router.route().handler(ctx -> {
			
			String file = "";
			
			HttpServerRequest req = ctx.request();
			
			if(req.path().equals("/")) {	
				file = "/roomDashboard.html";
			} else if (!req.path().contains("..")) {
			    file = req.path();
			}
			
			ctx.response().sendFile(RES_PATH + file);
		});
	}

	@Override
	public void start() {
		this.server.requestHandler(this.router).listen(this.port);
	}
}
