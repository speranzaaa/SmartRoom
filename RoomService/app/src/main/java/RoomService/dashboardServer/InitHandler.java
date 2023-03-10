package RoomService.dashboardServer;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class InitHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext ctx) {
		ctx.vertx().eventBus().request("init", "", (result)->{
			ctx.response().setStatusCode(200).end(result.result().body().toString());
		});
	}

}
