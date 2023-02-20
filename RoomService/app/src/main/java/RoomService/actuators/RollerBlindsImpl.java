package RoomService.actuators;

public class RollerBlindsImpl implements RollerBlinds {
    private boolean isOpen = false;
    private boolean isClosed = true;
    private int openPercentage = 0;

    @Override
    public void open() {
        isOpen = true;
        isClosed = false;
        openPercentage = 100;
    }

    @Override
    public void close() {
        isOpen = false;
        isClosed = true;
        openPercentage = 0;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public int getOpenPercentage() {
        return openPercentage;
    }
    
}