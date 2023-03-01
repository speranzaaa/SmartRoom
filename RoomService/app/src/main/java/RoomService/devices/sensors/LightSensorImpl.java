package RoomService.devices.sensors;

/**
 * Class that implements the {@link RoomService.devices.sensors.LightSensor} interface.
 */
public class LightSensorImpl implements LightSensor {
    private boolean day = false;

    @Override
    public boolean isDay() {
        return this.day == true;
    }

    @Override
    public void setDay() {
        this.day = true;
    }

    @Override
    public void setNight() {
        this.day = false;
    }
    
}
