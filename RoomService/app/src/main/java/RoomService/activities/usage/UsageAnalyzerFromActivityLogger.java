package RoomService.activities.usage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import RoomService.activities.Activity;
import RoomService.activities.ActivityLogger;

public class UsageAnalyzerFromActivityLogger implements UsageAnalizer {
	
	private final ActivityLogger activityLogger;
	private Map<String, Number> usage;

	public UsageAnalyzerFromActivityLogger(ActivityLogger activityLogger) {
		this.activityLogger = activityLogger;
	}
	
	private void resetUsage() {
		this.usage = new HashMap<>();
	}

	@Override
	public Map<String, Number> getUsage() {
		this.resetUsage();
		final List<Activity> activities = this.activityLogger.getActivities();
		activities.stream();
		
		return null;
	}
}
