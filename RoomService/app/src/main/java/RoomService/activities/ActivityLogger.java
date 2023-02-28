package RoomService.activities;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import RoomService.actuators.Device;

public interface ActivityLogger {

	void logActivity(final Device device, final Status newStatus);
	
	List<Activity> getActivities();
	
	void clearBefore(final Date date);
	
	void addNewActivityListener(Consumer<Activity> consumer);
	
	void removeNewActivityListener(Consumer<Activity> consumer);
}
