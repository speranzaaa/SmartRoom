package RoomService.devices.sensors;

public interface Pir extends Sensor {
    /**
     * Returns the status of the sensor.
     * @return True if the sensor is detecting something, false otherwise.
     */
    public boolean isDetected();

    /**
     * Sets the sensor status to detected.
     */
    public void setDetected();

    /**
     * Sets the sensor status to undetected.
     */
    public void setUndetected();
}
