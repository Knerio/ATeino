package dario.test;

import java.util.List;

public class PinConfiguration {

    private final List<Pin> pins;

    public PinConfiguration(List<Pin> pins) {
        this.pins = pins;
    }

    public void setPinAt(int index, Pin pin) {
        pins.set(index, pin);
    }

    public Integer pinAt(int i) {
        return pins.get(i).getValue();
    }

    public List<Pin> getPins() {
        return pins;
    }
}
