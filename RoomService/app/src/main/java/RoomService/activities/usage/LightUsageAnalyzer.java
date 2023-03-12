package RoomService.activities.usage;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import RoomService.activities.Activity;
import RoomService.activities.ActivityLogger;
import RoomService.devices.actuators.LightImpl.LightStatus;

public class LightUsageAnalyzer implements UsageAnalyzer {
	
	private final ActivityLogger activityLogger;
	private final SumMap<String, Long> secondsOfUsage;
	private String target;
	private Date fromDate;
	private Date toDate;
	private TimeRange groupBy;
	private long activitySeconds;
	private long turnOnTimestamp;
	private boolean lastWasOn = false;

	public LightUsageAnalyzer(ActivityLogger activityLogger) {
		this.activityLogger = activityLogger;
		this.secondsOfUsage = new OrderedLongSumMap<>();
	}

	@Override
	public UsageAnalyzer setTarget(String deviceName) {
		if(this.target != null) {
			throw new IllegalStateException("The target has already ben set!");
		}
		this.target = deviceName;
		return this;
	}

	@Override
	public UsageAnalyzer fromDate(Date date) {
		if(this.fromDate != null) {
			throw new IllegalStateException("The from-date filter has already been set!");
		}
		this.fromDate = date;
		return this;
	}

	@Override
	public UsageAnalyzer toDate(Date date) {
		if(this.toDate != null) {
			throw new IllegalStateException("The to-date filter has already been set!");
		}
		this.toDate = date;
		return this;
	}

	@Override
	public UsageAnalyzer groupBy(TimeRange timeRange) {
		if(this.groupBy != null) {
			throw new IllegalStateException("The grup-by parameter has already been set!");
		}
		this.groupBy = timeRange;
		return this;
	}
	
	private String classificate(Activity activity) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(activity.getTimestamp().getTime());
		switch(this.groupBy) {
			case HOUR:
				return String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
			case DAY_OF_WEEK:
				return DAYS.values()[cal.get(Calendar.DAY_OF_WEEK)].toString();
			case DAY:
				return cal.get(Calendar.DATE) + "/" +  (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
			case MONTH:
				return String.valueOf(cal.get(Calendar.MONTH) + 1);
			case YEAR:
				String.valueOf(cal.get(Calendar.YEAR));
		}
		return "Unknown";
	}
	
	private boolean canBuild() {
		return this.target != null && this.groupBy != null;
	}

	@Override
	public Map<String, Long> getSecondsOfUsage() {
		if(!canBuild()) {
			throw new IllegalStateException("Cannot build. Please, set all parameters first!");
		}
		//filter target activities and divide for period.
		Map<String, List<Activity>> dividedActivities = this.activityLogger
			.getActivities()
			.stream()
			.filter(activity->LightStatus.class.isInstance(activity.getStatus()))
			.filter(activity->activity.getDevice().getName().equals(this.target))
			.filter(activity->{ 
				return this.fromDate == null ? true : activity.getTimestamp().after(this.fromDate);
			}).filter(activity->{ 
				return this.toDate == null ? true : activity.getTimestamp().before(this.toDate);
			}).collect(Collectors.groupingBy(activity->classificate(activity)));
		
		dividedActivities.keySet().forEach((key->{
			activitySeconds = 0;
			turnOnTimestamp = 0;
			lastWasOn = false;
			dividedActivities.get(key)
				.stream()
				.sorted((a1,a2)->a1.getTimestamp().compareTo(a2.getTimestamp()))
				.forEach(activity->{
					if(((LightStatus)activity.getStatus()).wasOn() && !lastWasOn) {
						turnOnTimestamp = activity.getTimestamp().getTime();
						lastWasOn = true;
					} else if(!((LightStatus)activity.getStatus()).wasOn() && lastWasOn) {
						activitySeconds += TimeUnit.MILLISECONDS.toSeconds(activity.getTimestamp().getTime()-turnOnTimestamp);
						lastWasOn = false;
						secondsOfUsage.put(key, activitySeconds);
					}
				});
		}));
		return Collections.unmodifiableMap(this.secondsOfUsage);
	}
	
	private enum DAYS {
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY,
		SUNDAY
	}
}
