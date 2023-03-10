package RoomService.dashboardServer;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class SSEHandler implements Handler<RoutingContext> {
	
	private final EventBus eventBus;
	
	public SSEHandler(final EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	private void setHeaders(HttpServerResponse response) {
		response.headers()
				.add("Content-Type", "text/event-stream;charset=UTF-8")
				.add("Connection", "keep-alive")
				.add("Cache-Control", "no-cache")
				.add("Access-Control-Allow-Origin", "*");
	}

	@Override
	public void handle(RoutingContext ctx) {
		HttpServerResponse response = ctx.response();
		
		response.setChunked(true);
		this.setHeaders(response);
		
	    this.eventBus.consumer("sse", message -> {
	    	JsonObject jsonMessage = new JsonObject(message.body().toString());
	    	SSEMessage sseMessage = new SSEMessageImpl(
	    				jsonMessage.getString("eventName"), 
	    				jsonMessage.getString("messageBody"));
	    	response.write(sseMessage.getFormattedMessage());
	    });
	}

}
