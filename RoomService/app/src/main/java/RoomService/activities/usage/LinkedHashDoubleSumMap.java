package RoomService.activities.usage;

public class LinkedHashDoubleSumMap<K, V> extends LinkedHashSumMap<K, Double> {

	@Override
	protected Double sum(Double num1, Double num2) {
		return num1+num2;
	}


}
