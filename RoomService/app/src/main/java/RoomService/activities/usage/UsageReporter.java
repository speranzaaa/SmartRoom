package RoomService.activities.usage;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public interface UsageReporter extends Handler<Message<JsonObject>>{
	
}
