
enum PinType {
  READ_HIGH,
  READ_LOW,
  WRITE_HIGH,
  WRITE_LOW,
  UNKNOWN,
};

class ErrorResult {
  private:
    int fails;
    int successes;
  public:
    ErrorResult() {
      fails = 0;
      successes = 0;
    }
    void increment_fail() {
      fails++;
    }
    void increment_success() {
      successes++;
    }
    int get_fails() {
      return fails;
    }
    int get_successes() {
      return successes;
    }
};


class PinConfiguration {
  private:
    int* pins;
    int pin_count;

  public:
    PinConfiguration(int* c_pins, int c_pin_count) {
      pins = c_pins;
      pin_count = c_pin_count;
    }

    int pin_at(int i) {
      if (i < pin_count) return pins[i];
      return -1;
    }

    int count() { return pin_count; }
};

class TestCase {
  private:
    PinType* data;
    int data_count;

  public:
    TestCase(PinType* c_data, int c_data_count) {
      data = c_data;
      data_count = c_data_count;
    }
    bool run_test(PinConfiguration pin_config) {
      Serial.println("--- Starting Test Case ---");
      for(int i = 0; i < data_count; i++) {
        PinType type = data[i];
        uint8_t pin = pin_config.pin_at(i);

        switch(type) {
          case WRITE_HIGH:
            pinMode(pin, OUTPUT);
            digitalWrite(pin, 1);
            break;
          case WRITE_LOW:
            pinMode(pin, OUTPUT);
            digitalWrite(pin, 0);
            break;
        }
      }

      
      for(int i = 0; i < data_count; i++) {
        PinType type = data[i];
        int pin = pin_config.pin_at(i);

        switch(type) {
          case READ_HIGH:
            pinMode(pin, INPUT);
            if(digitalRead(pin) != 1) return false;
            break;
          case READ_LOW:
            pinMode(pin, INPUT);
            if(digitalRead(pin) != 0) return false;
            break;
        }
      }

      Serial.println("--- Successfully tested pattern ---");
      return true;
    }
};

class PatternConfiguration {
  private:
    PinConfiguration pin_config;
    TestCase* test_cases;
    int test_count;

  public:
    PatternConfiguration(PinConfiguration c_pin_config, TestCase* c_test_cases, int c_test_count)
      : pin_config(c_pin_config) 
    {
      test_cases = c_test_cases;
      test_count = c_test_count;
    }
    ErrorResult test_all() {
      ErrorResult result = ErrorResult();
      for(int i = 0; i < test_count; i++) {
        if(test_cases[i].run_test(pin_config)) {
          result.increment_success();
        } else {
          result.increment_fail();
        }
      }
      return result;
    }
};

static int pins[] = {9, A1, A2, A3, A4, A5, 7, 6, 5, 4, 3, 2};

static TestCase test_cases[] = {
    TestCase(
        (PinType[]){
            WRITE_LOW, WRITE_HIGH, READ_LOW, READ_LOW, READ_LOW, READ_LOW, WRITE_LOW, WRITE_HIGH, READ_LOW, READ_LOW, READ_LOW, READ_LOW
        },
        12
    )
};

const PatternConfiguration pattern_config(
    PinConfiguration(pins, 12),
    test_cases,
    2
);



void setup() {
  Serial.begin(9600);
  
  Serial.println();
  Serial.println("Testing chip: ");
  

  const ErrorResult result = pattern_config.test_all();


  Serial.println("-----");
  Serial.println("Ran " + String(result.get_successes() + result.get_fails()) + " tests.");
  Serial.println("Successes: " + String(result.get_successes()));
  Serial.println("Fails: " + String(result.get_fails()));
  Serial.println("-----");

  // 12 = Red, 11 = Green
  int LED = result.get_fails() == 0 ? 12 : 11;

  pinMode(LED, OUTPUT);


  while(true) {
    digitalWrite(LED, HIGH);
    delay(200);
    digitalWrite(LED, LOW);
    delay(200);
  }
}

void loop() {

}
