package RoomService.sensors;

/**
 * Class that implements the {@link RoomService.sensors.LightSensor} interface.
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
