package dario;


import com.fazecast.jSerialComm.SerialPort;
import dario.test.*;
import dario.thread.ConsoleThread;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static dario.test.Pin.*;
import static dario.test.PinType.*;

public class Main {

    public static ConsoleThread CONSOLE;

    public static void main(String[] args) {
        SerialPort port = SerialPort.getCommPort("COM3");
        if (!port.openPort()) throw new RuntimeException("Couldn't open port");
        port.setBaudRate(9600);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, SerialPort.TIMEOUT_SCANNER, SerialPort.TIMEOUT_WRITE_BLOCKING);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        CONSOLE = new ConsoleThread(port);
        CONSOLE.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        TestConfiguration configuration = new TestConfiguration(new PinConfiguration(List.of(PIN_9, PIN_A1, PIN_A2, PIN_A3, PIN_A4, PIN_A5, PIN_7, PIN_6, PIN_5, PIN_4, PIN_3, PIN_2)), Set.of(
                new TestCase(List.of(WRITE_LOW, WRITE_HIGH, READ_LOW, READ_LOW, READ_LOW, READ_LOW, WRITE_LOW, WRITE_HIGH, READ_LOW, READ_LOW, READ_LOW, READ_LOW))
        ));

        for (TestCase testCase : configuration.getTestCases()) {
            System.out.println("Test result: " + testCase.test(configuration.getPinConfiguration(), port));
        }

    }
}
