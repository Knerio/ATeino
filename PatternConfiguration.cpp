class PatternConfiguration {
  private:
    PinConfiguration pin_config;
    TestCase test_cases[];
  public:
    PatternConfiguration(PinConfiguration c_pin_config, TestCase c_test_cases[]) {
      pin_config = c_pin_config;
      test_cases = c_test_cases;
    }
}