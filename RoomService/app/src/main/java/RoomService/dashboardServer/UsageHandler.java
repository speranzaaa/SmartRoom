package RoomService.dashboardServer;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UsageHandler implements Handler<RoutingContext> {
	
	private final static long TIMEOUT = 2000;

	@Override
	public void handle(RoutingContext ctx) {
		JsonObject jsonRequestQuery = new JsonObject()
				.put("timeRange", ctx.queryParams().get("time-range"))
				.put("day", ctx.queryParams().get("day"));
		
		DeliveryOptions msgOptions = new DeliveryOptions().setSendTimeout(TIMEOUT);
		
		ctx.vertx().eventBus().request("usage", jsonRequestQuery, msgOptions, (result) -> {
			if(result.failed()) {
				ctx.response().setStatusCode(500).end();
			} else {
				ctx.response().setStatusCode(200).end(result.result().body().toString());
			}
		});
		
	}
}
