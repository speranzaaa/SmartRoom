package RoomService.dashboardServer;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

public class ControlHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext ctx) {
		HttpServerRequest request = ctx.request();
		
		request.bodyHandler(buffer->{
			String body = buffer.getString(0, buffer.length());
			System.out.println(body);
		});
		
		ctx.response().end();
	}

}
