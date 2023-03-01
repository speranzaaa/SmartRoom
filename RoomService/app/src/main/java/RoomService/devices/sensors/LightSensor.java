package RoomService.devices.sensors;

public interface LightSensor extends Sensor {
    /**
     * Returns a boolean value based on the time of day.
     * @return True if the sensor detects day, false otherwise.
     */
    public boolean isDay();

    /**
     * Sets the sensor's detection to day.
     */
    public void setDay();

    /**
     * Sets the sensor's detection to night.
     */
    public void setNight();
}
