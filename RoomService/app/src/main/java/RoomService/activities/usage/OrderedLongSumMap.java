package RoomService.activities.usage;

public class OrderedLongSumMap<K, V> extends LinkedHashSumMap<K, Long> {

	@Override
	protected Long sum(Long num1, Long num2) {
		return num1+num2;
	}


}
