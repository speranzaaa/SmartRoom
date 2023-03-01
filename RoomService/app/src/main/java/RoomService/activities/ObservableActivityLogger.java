package RoomService.activities;

import java.util.function.Consumer;

public interface ObservableActivityLogger extends ActivityLogger {
	
	void addActivitiesObserver(Consumer<Activity> consumer);
	
	void removeActivitiesObserver(Consumer<Activity> consumer);
}
