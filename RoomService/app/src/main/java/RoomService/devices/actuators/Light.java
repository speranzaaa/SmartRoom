package RoomService.devices.actuators;

public interface Light extends Actuator {

    void turnOn(boolean on);

    boolean isOn();
}

