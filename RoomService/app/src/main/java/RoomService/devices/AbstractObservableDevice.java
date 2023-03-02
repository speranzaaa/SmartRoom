package RoomService.devices;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractObservableDevice implements ObservableDevice {
	
	private final Set<Consumer<Status>> observers = new HashSet<>();;

	@SuppressWarnings("unused")
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
