package RoomService.devices;

import java.util.function.Consumer;

public interface ObservableDevice extends Device {
	
	void addStatusObserver(Consumer<Status> consumer);
	
	void removeStatusObserver(Consumer<Status> consumer);

}
