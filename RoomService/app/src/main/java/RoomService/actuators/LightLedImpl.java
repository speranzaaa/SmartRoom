public class LightLedImpl implements LightLed {

    private boolean isOn = false;
    private boolean isOff = true;

    @Override
    public void turnOn() {
        isOn = true;
        isOff = false;
    }

    @Override
    public void turnOff() {
        isOn = false;
        isOff = true;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public boolean isOff() {
        return isOff;
    }
}