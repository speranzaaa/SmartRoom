package RoomService.dashboardServer;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

public class FileRequestHandler implements Handler<RoutingContext> {
	
	private final String resolutionPath;
	
	public FileRequestHandler(String resolutionPath) {
		this.resolutionPath = resolutionPath;
	}

	@Override
	public void handle(RoutingContext ctx) {
		
		String file = "";
		
		HttpServerRequest req = ctx.request();
		
		if(req.path().equals("/")) {	
			file = "/roomDashboard.html";
		} else if (!req.path().contains("..")) {
		    file = req.path();
		}
		
		ctx.response().sendFile(this.resolutionPath + file);
	}

}
