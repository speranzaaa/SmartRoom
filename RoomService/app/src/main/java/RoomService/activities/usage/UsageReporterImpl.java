package RoomService.activities.usage;

import RoomService.activities.ActivityLogger;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class UsageReporterImpl implements UsageReporter {
	
	private final ActivityLogger activityLogger;
	
	public UsageReporterImpl(ActivityLogger activityLogger) {
		this.activityLogger = activityLogger;
	}

	private JsonArray getUsage(String timeRange) {
		return new JsonArray().add(new JsonObject().put("x", "Monday").put("y", 20));
	}
	
	@Override
	public void handle(Message<JsonObject> msg) {
		JsonObject query = msg.body();
		System.out.println(query);
		msg.reply(this.getUsage(query.getString("timeRange")));
	}
}
