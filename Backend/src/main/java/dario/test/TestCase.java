package dario.test;

import com.fazecast.jSerialComm.SerialPort;
import dario.DataPayload;
import dario.DataType;

import java.io.Serial;
import java.util.List;

public class TestCase {

    private final List<PinType> pinTypes;

    public TestCase(List<PinType> pinTypes) {
        this.pinTypes = pinTypes;
    }

    public boolean test(PinConfiguration pinConfiguration, SerialPort port) {
        for (int i = 0; i < pinTypes.size(); i++) {
            PinType pinType = pinTypes.get(i);
            Integer pin = pinConfiguration.pinAt(i);
            switch (pinType) {
                case WRITE_HIGH -> {
                    new DataPayload(DataType.WRITE_HIGH, pin).write(port);
                }
                case WRITE_LOW -> {
                    new DataPayload(DataType.WRITE_LOW, pin).write(port);
                }
            }
        }


        for (int i = 0; i < pinTypes.size(); i++) {
            PinType pinType = pinTypes.get(i);
            Integer pin = pinConfiguration.pinAt(i);
            switch (pinType) {
                case READ_LOW -> {
                    String result = new DataPayload(DataType.READ, pin).writeWithExpectedResult(port);
                    if (!result.equals("0")) return false;
                }
                case READ_HIGH -> {
                    String result = new DataPayload(DataType.READ, pin).writeWithExpectedResult(port);
                    if (!result.equals("1")) return false;
                }
            }
        }
        return true;
    }
}
