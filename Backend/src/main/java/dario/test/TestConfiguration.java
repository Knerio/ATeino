package dario.test;

import com.fazecast.jSerialComm.SerialPort;

import java.util.List;
import java.util.Set;

public class TestConfiguration {

    private final PinConfiguration pinConfiguration;
    private final List<TestCase> testCases;

    public TestConfiguration(PinConfiguration pinConfiguration, List<TestCase> testCases) {
        this.pinConfiguration = pinConfiguration;
        this.testCases = testCases;
    }

    public TestResult testAll(SerialPort port) {
        TestResult result = new TestResult();
        for (int i = 0; i < testCases.size(); i++) {
            TestCase testCase = testCases.get(i);
            if (testCase.test(pinConfiguration, port)) {
                result.incrementSuccess(i);
            } else {
                result.incrementFail(i);
            }
        }
        return result;
    }

    public PinConfiguration getPinConfiguration() {
        return pinConfiguration;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }
}
