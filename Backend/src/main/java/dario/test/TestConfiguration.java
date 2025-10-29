package dario.test;

import java.util.Set;

public class TestConfiguration {

    private final PinConfiguration pinConfiguration;
    private final Set<TestCase> testCases;

    public TestConfiguration(PinConfiguration pinConfiguration, Set<TestCase> testCases) {
        this.pinConfiguration = pinConfiguration;
        this.testCases = testCases;
    }

    public PinConfiguration getPinConfiguration() {
        return pinConfiguration;
    }

    public Set<TestCase> getTestCases() {
        return testCases;
    }
}
