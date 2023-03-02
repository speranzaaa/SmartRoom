package RoomService.devices.actuators;

public interface RollerBlinds extends Actuator {
	
		void setTo(int percentage);
    
        void openFully();
    
        void close();
    
        boolean isOpen();
        
        boolean isFullyOpen();
    
        boolean isClosed();

        int getOpenPercentage();
}