package RoomService.actuators;

public interface RollerBlinds {
    
        void open();
    
        void close();
    
        boolean isOpen();
    
        boolean isClosed();

        int getOpenPercentage();
}