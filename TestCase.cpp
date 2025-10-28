#include <Arduino.h>

class TestCase {
  private:
    PinType data[];
    PinConfiguration pin_config[];
  public:
    TestCase(PinType c_data[], PinConfiguration c_pin_config[]) {
      data = c_data;
      pin_config = c_pin_config;
    }
    void run_test() {
      
    }
} 