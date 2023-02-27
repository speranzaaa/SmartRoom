package RoomService.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import RoomService.actuators.Device;

public class VolatileActivityLogger implements ActivitiyLogger {
	
	protected List<Activity> log;
	private final Set<Consumer<Activity>> listeners;
	
	public VolatileActivityLogger(List<Activity> log) {
		this.listeners = new HashSet<>();
		this.log = log;
	};
	
	public VolatileActivityLogger() {
		this(new ArrayList<>());
	};
	
	@Override
	public void logActivity(Device device, Status newStatus) {
		Activity activity = new ActivityImpl(device, newStatus);
		if(this.log.contains(activity)) {
			throw new IllegalArgumentException("An activity at: " + activity.getTimestamp() + " for " + activity.getDevice() + " is already logged!");
		}
		this.log.add(activity);
		this.listeners.stream().forEach(listener->listener.accept(activity));
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

	@Override
	public void addNewActivityListener(Consumer<Activity> consumer) {
		this.listeners.add(consumer);
	}

	@Override
	public void removeNewActivityListener(Consumer<Activity> consumer) {
		this.listeners.remove(consumer);
	}
}
