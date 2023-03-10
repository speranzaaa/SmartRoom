package RoomService.activities.usage;

import java.util.Date;
import java.util.Map;

public interface UsageAnalyzer {
	
	public static enum TimeRange {HOUR, DAY_OF_WEEK, DAY, MONTH, YEAR};
	
	UsageAnalyzer setTarget(String target);
	
	UsageAnalyzer fromDate(Date date);
	
	UsageAnalyzer toDate(Date date);
	
	UsageAnalyzer groupBy(TimeRange timeRange);
	
	Map<String, Long> getSecondsOfUsage();

}
