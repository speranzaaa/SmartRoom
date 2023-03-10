package RoomService.activities.usage;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import RoomService.activities.ActivityLogger;
import RoomService.activities.usage.UsageAnalyzer.TimeRange;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class UsageReporterImpl implements UsageReporter {
	
	private final ActivityLogger activityLogger;
	
	public UsageReporterImpl(ActivityLogger activityLogger) {
		this.activityLogger = activityLogger;
	}
	
	private JsonArray getUsage(TimeRange timeRange, Date fromDate, Date toDate) {
		Map<String, Long> usage = new LightUsageAnalyzer(activityLogger)
			.setTarget("lights-subgroup")
			.fromDate(fromDate)
			.toDate(toDate)
			.groupBy(timeRange)
			.getSecondsOfUsage();
		
		JsonArray jsonUsage = new JsonArray();
		
		usage.entrySet()
			.stream()
			.forEachOrdered((entry)->{
				jsonUsage.add(new JsonObject().put("x", entry.getKey()).put("y", entry.getValue()));
			});
		
		return jsonUsage;
	}

	@Override
	public void handle(Message<JsonObject> msg) {
		JsonObject query = msg.body();
		
		String date[] = query.getString("day").split("/");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.valueOf(date[2]), Integer.valueOf(date[1])-1, Integer.valueOf(date[0]));
		
		Date toDate = cal.getTime();
		Date fromDate = null;
		TimeRange timeRange = TimeRange.DAY;
		
		switch(query.getString("timeRange").toLowerCase()) {
			case "day":
				timeRange = TimeRange.HOUR;
				cal.set(Integer.valueOf(date[2]), Integer.valueOf(date[1])-1, Integer.valueOf(date[0])-1);
				fromDate = cal.getTime();
				break;
			case "week":
				timeRange = TimeRange.DAY_OF_WEEK;
				cal.set(Integer.valueOf(date[2]), Integer.valueOf(date[1])-1, Integer.valueOf(date[0])-7);
				fromDate = cal.getTime();
				break;
			case "month":
				timeRange = TimeRange.DAY;
				cal.set(Integer.valueOf(date[2]), Integer.valueOf(date[1])-2, Integer.valueOf(date[0]));
				fromDate = cal.getTime();
				break;
			case "year":
				timeRange = TimeRange.MONTH;
				cal.set(Integer.valueOf(date[2])-1, Integer.valueOf(date[1])-1, Integer.valueOf(date[0]));
				fromDate = cal.getTime();
				break;
		}
		msg.reply(this.getUsage(timeRange, fromDate, toDate));
	}
}
