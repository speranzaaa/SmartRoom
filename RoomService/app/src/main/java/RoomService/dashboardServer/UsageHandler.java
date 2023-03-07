package RoomService.dashboardServer;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class UsageHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext ctx) {
		System.out.println(ctx.queryParams());
		ctx.response().end("{Mon: 10}");
	}

}
