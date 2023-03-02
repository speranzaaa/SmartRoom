package RoomService.devices.actuators;

public interface LightLed extends Actuator {

    void turnOn();

    void turnOff();

    boolean isOn();

    boolean isOff();
}

