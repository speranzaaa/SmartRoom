package RoomService.dashboardServer;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class SSEHandler implements Handler<RoutingContext> {
	
	private final EventBus eventBus;
	
	public SSEHandler(final EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void handle(RoutingContext ctx) {
		HttpServerResponse response = ctx.response();
		
		response.setChunked(true);
		response.headers().add("Content-Type", "text/event-stream;charset=UTF-8");
	    response.headers().add("Connection", "keep-alive");
	    response.headers().add("Cache-Control", "no-cache");
	    response.headers().add("Access-Control-Allow-Origin", "*");
	    this.eventBus.consumer("events", message -> {
	    	response.write("data:" + message.body() + "\n\n").onFailure((e)-> {
	    		System.out.print("failure: " + e);
	    	});
	    });
	}

}
