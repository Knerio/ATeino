package dario;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class DataPayload {

    private final DataType dataType;
    private final Integer pin;

    public DataPayload(DataType dataType, Integer pin) {
        this.dataType = dataType;
        this.pin = pin;
    }

    public int write(SerialPort port) {
        byte[] buffer = getData();
        return  port.writeBytes(buffer, buffer.length);
    }

    public String writeWithExpectedResult(SerialPort port) {
        write(port);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Main.CONSOLE.getLatestLine();
    }

    public byte[] getData() {
        return new byte[]{dataType.getData(), pin.byteValue()};
    }

}

