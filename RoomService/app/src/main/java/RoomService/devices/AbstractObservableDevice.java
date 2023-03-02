package RoomService.devices;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractObservableDevice implements ObservableDevice {
	
	private final transient Set<Consumer<Status>> observers = new HashSet<>();;

	protected void updateObservers(Status newStatus) {
		this.observers.stream().forEach(observer->observer.accept(newStatus));
	}

	@Override
	public void addStatusObserver(Consumer<Status> consumer) {
		this.observers.add(consumer);
	}

	@Override
	public void removeStatusObserver(Consumer<Status> consumer) {
		this.observers.remove(consumer);
	}
}
