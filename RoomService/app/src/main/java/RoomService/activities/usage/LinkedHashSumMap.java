package RoomService.activities.usage;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class LinkedHashSumMap<K, V> implements SumMap<K, V> {
	
	private final Map<K, V> map = new LinkedHashMap<>();

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return this.map.get(key);
	}
	
	/**
	 * Get two values and must return the sum
	 * @param num1
	 * @param num2
	 * @return the sum of the two values
	 */
	abstract protected V sum(V num1, V num2);

	/**
	 *	If the key is present, the new value will be summed to the actual value.
	 */
	@Override
	public V put(K key, V value) {
		if(this.map.containsKey(key)) {
			V old = this.map.get(key);
			return this.map.put(key, this.sum(old, value));
		}
		return this.map.put(key, value);
	}

	@Override
	public V remove(Object key) {
		return this.map.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		m.forEach((k, v)->this.put(k, v));
	}

	@Override
	public void clear() {
		this.map.clear();
	}

	@Override
	public Set<K> keySet() {
		return this.map.keySet();
	}

	@Override
	public Collection<V> values() {
		return this.map.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return this.map.entrySet();
	}


}
