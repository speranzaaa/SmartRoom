package RoomService.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class VolatileActivityLogger implements ActivityLogger {
	
	protected List<Activity> log;
	
	public VolatileActivityLogger(List<Activity> log) {
		this.log = log;
	};
	
	public VolatileActivityLogger() {
		this(new ArrayList<>());
	};
	
	@Override
	public void logActivity(final Activity activity) {
		if(this.log.contains(activity)) {
			throw new IllegalArgumentException("An activity at: " + activity.getTimestamp() + " for " + activity.getDevice() + " is already logged!");
		}
		this.log.add(activity);
	}

	@Override
	public List<Activity> getActivities() {
		return Collections.unmodifiableList(this.log);
	}
	
	@Override
	public void clearBefore(Date date) {
		this.log
			.stream()
			.filter(activity->activity.getTimestamp().before(date))
			.forEach(activity->this.log.remove(activity));
	}
}
