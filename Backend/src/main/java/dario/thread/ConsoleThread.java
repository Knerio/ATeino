package dario.thread;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Scanner;

public class ConsoleThread extends Thread {

    private final SerialPort port;
    private String latestLine;

    public ConsoleThread(SerialPort port) {
        this.port = port;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(port.getInputStream());
        while (true) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println("Console: "+line);
                if (line.isBlank() || line == null) continue;
                latestLine = line;
            }
        }
    }

    public String getLatestLine() {
        return latestLine;
    }
}
