package dario;


import com.fazecast.jSerialComm.SerialPort;
import dario.gui.TestGUI;
import dario.test.*;
import dario.thread.ConsoleThread;

import java.util.ArrayList;
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

        TestConfiguration configuration = new TestConfiguration(new PinConfiguration(new ArrayList<>(List.of(PIN_9, PIN_A1, PIN_A2, PIN_A3, PIN_A4, PIN_A5, PIN_7, PIN_6, PIN_5, PIN_4, PIN_3, PIN_2))), new ArrayList<>(
                List.of(
                        new TestCase(new ArrayList<>(List.of(WRITE_LOW, WRITE_HIGH, READ_LOW, READ_LOW, READ_LOW, READ_LOW, WRITE_LOW, WRITE_HIGH, READ_LOW, READ_LOW, READ_LOW, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_HIGH, WRITE_LOW, READ_LOW, READ_LOW, READ_LOW, READ_LOW, WRITE_HIGH, WRITE_LOW, READ_LOW, READ_LOW, READ_LOW, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_LOW, WRITE_LOW, READ_HIGH, READ_LOW, READ_LOW, READ_LOW, WRITE_LOW, WRITE_LOW, READ_HIGH, READ_LOW, READ_LOW, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_HIGH, WRITE_LOW, READ_HIGH, READ_LOW, READ_LOW, READ_LOW, WRITE_HIGH, WRITE_LOW, READ_HIGH, READ_LOW, READ_LOW, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_LOW, WRITE_LOW, READ_LOW, READ_HIGH, READ_LOW, READ_LOW, WRITE_LOW, WRITE_LOW, READ_LOW, READ_HIGH, READ_LOW, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_HIGH, WRITE_LOW, READ_LOW, READ_HIGH, READ_LOW, READ_LOW, WRITE_HIGH, WRITE_LOW, READ_LOW, READ_HIGH, READ_LOW, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_LOW, WRITE_LOW, READ_HIGH, READ_HIGH, READ_LOW, READ_LOW, WRITE_LOW, WRITE_LOW, READ_HIGH, READ_HIGH, READ_LOW, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_HIGH, WRITE_LOW, READ_HIGH, READ_HIGH, READ_LOW, READ_LOW, WRITE_HIGH, WRITE_LOW, READ_HIGH, READ_HIGH, READ_LOW, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_LOW, WRITE_LOW, READ_LOW, READ_LOW, READ_HIGH, READ_LOW, WRITE_LOW, WRITE_LOW, READ_LOW, READ_LOW, READ_HIGH, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_HIGH, WRITE_LOW, READ_LOW, READ_LOW, READ_HIGH, READ_LOW, WRITE_HIGH, WRITE_LOW, READ_LOW, READ_LOW, READ_HIGH, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_LOW, WRITE_LOW, READ_HIGH, READ_LOW, READ_HIGH, READ_LOW, WRITE_LOW, WRITE_LOW, READ_HIGH, READ_LOW, READ_HIGH, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_HIGH, WRITE_LOW, READ_HIGH, READ_LOW, READ_HIGH, READ_LOW, WRITE_HIGH, WRITE_LOW, READ_HIGH, READ_LOW, READ_HIGH, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_LOW, WRITE_LOW, READ_LOW, READ_HIGH, READ_HIGH, READ_LOW, WRITE_LOW, WRITE_LOW, READ_LOW, READ_HIGH, READ_HIGH, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_HIGH, WRITE_LOW, READ_LOW, READ_HIGH, READ_HIGH, READ_LOW, WRITE_HIGH, WRITE_LOW, READ_LOW, READ_HIGH, READ_HIGH, READ_LOW))),
                        new TestCase(new ArrayList<>(List.of(WRITE_LOW, WRITE_LOW, READ_HIGH, READ_HIGH, READ_HIGH, READ_LOW, WRITE_LOW, WRITE_LOW, READ_HIGH, READ_HIGH, READ_HIGH, READ_LOW)))
                        )
        ));


        new TestGUI(configuration, port);
    }
}
