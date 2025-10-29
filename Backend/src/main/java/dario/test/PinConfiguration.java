package dario.test;

import java.util.List;

public class PinConfiguration {

    private final List<Pin> pins;

    public PinConfiguration(List<Pin> pins) {
        this.pins = pins;
    }

    public Integer pinAt(int i) {
        return pins.get(i).getValue();
    }
}
