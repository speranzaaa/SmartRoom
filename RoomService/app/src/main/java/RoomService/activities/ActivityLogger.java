package RoomService.activities;

import java.util.Date;
import java.util.List;

public interface ActivityLogger {

	void logActivity(final Activity activity);
	
	List<Activity> getActivities();
	
	void clearBefore(final Date date);
}
