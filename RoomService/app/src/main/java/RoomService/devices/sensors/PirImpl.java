package RoomService.devices.sensors;

/**
 * Class that represents the pir sensor.
 */
public class PirImpl implements Pir {
    private boolean detected = false;

    /**
     * Creates a new instance of the Pir class.
     */
    public PirImpl() {
        super();
    }

    @Override
    public boolean isDetected() {
        return this.detected == true;
    }

    @Override
    public void setDetected() {
        this.detected = true;
    }

    @Override
    public void setUndetected() {
        this.detected = false;
    }

}
