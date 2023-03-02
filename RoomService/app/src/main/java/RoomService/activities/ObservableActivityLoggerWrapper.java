package RoomService.activities;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * An ObservableActivityLoggerWrapper wrap an ActivityLogger so every new activity can be observed.
 * <b>
 * Subscribe as an observer calling observeNewActivity() method. 
 * The Consumer passed as an argument will be called every time a new activity will be logged.
 */
public class ObservableActivityLoggerWrapper implements ObservableActivityLogger {
	
	private final ActivityLogger wrappedActivityLogger;
	private final Set<Consumer<Activity>> listeners;
	
	/**
	 * Create a new ObservableActivityLoggerWrapper wrapping an existing ActivityLogger.
	 * @param activityLoggerToObserve The activity logger to wrap and observe.
	 */
	public ObservableActivityLoggerWrapper(ActivityLogger activityLoggerToObserve) {
		this.wrappedActivityLogger = activityLoggerToObserve;
		this.listeners = new HashSet<>();
	}

	@Override
	public void logActivity(final Activity activity) {
		this.wrappedActivityLogger.logActivity(activity);
		this.listeners.stream().forEach(observer->observer.accept(activity));
	}

	@Override
	public List<Activity> getActivities() {
		return this.wrappedActivityLogger.getActivities();
	}

	@Override
	public void clearBefore(Date date) {
		this.wrappedActivityLogger.clearBefore(date);
	}

	@Override
	public void addActivitiesObserver(Consumer<Activity> consumer) {
		this.listeners.add(consumer);
	}

	@Override
	public void removeActivitiesObserver(Consumer<Activity> consumer) {
		this.listeners.remove(consumer);
	}

}
