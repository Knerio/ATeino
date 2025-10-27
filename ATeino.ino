const uint8_t pin_configuration[2][8] = {
  {
    9, A1, A3, A4, 7, 6, 4, 3
  },
  {
    A2, A5, 5, 2, -1, -1, -1, -1
  }
};

const uint8_t pattern[4][2][8] = {
  { { LOW, LOW, LOW, LOW, LOW, LOW, HIGH, HIGH }, { LOW, LOW, LOW, HIGH, -1, -1, -1, -1 } },
  { { LOW, LOW, LOW, LOW, HIGH, HIGH, LOW, LOW }, { LOW, LOW, HIGH, LOW, -1, -1, -1, -1 } },
  { { LOW, LOW, HIGH, HIGH, LOW, LOW, LOW, LOW }, { LOW, HIGH, LOW, LOW, -1, -1, -1, -1 } },
  { { HIGH, HIGH, LOW, LOW, LOW, LOW, LOW, LOW }, { HIGH, LOW, LOW, LOW, -1, -1, -1, -1 } }
};

void setup() {
  Serial.begin(9600);
  
  for(int i = 0; i < sizeof(pin_configuration[0]) / sizeof(pin_configuration[0][0]); i++) {
      pinMode(pin_configuration[0][i], OUTPUT);
  }
  for(int i = 0; i < sizeof(pin_configuration[1]) / sizeof(pin_configuration[1][0]); i++) {
    pinMode(pin_configuration[1][i], INPUT);
  }


  Serial.println();
  Serial.println("Testing chip: ");
  
  int successes = 0;
  int fails = 0;

  for(int i = 0; i < sizeof(pattern) / sizeof(pattern[0]); i++) {
    if(test(i)) {
      successes++;
    } else {
      fails++;
    }
  }

  Serial.println("-----");
  Serial.println("Ran " + String(successes + fails) + " tests.");
  Serial.println("Successes: " + String(successes));
  Serial.println("Fails: " + String(fails));
  Serial.println("-----");
}

bool test(int pattern_index) {
  Serial.println("--- Starting Pattern ---");
  
  for(int i = 0; i < sizeof(pattern[pattern_index][0]) / sizeof(pattern[pattern_index][0][0]); i++) {
    int pin = pin_configuration[0][i];
    if(pin == 255) continue;
    Serial.println("Setting pin " + String(pin) + " to " + String(pattern[pattern_index][0][i]));
    digitalWrite(pin, pattern[pattern_index][0][i]);
  }

  delay(20);

  for(int i = 0; i < sizeof(pattern[pattern_index][1]) / sizeof(pattern[pattern_index][1][0]); i++) {
    int pin = pin_configuration[1][i];
    int result = digitalRead(pin);
    int expected_value = pattern[pattern_index][1][i];
    if (pin == 255) continue;
    Serial.println("Expecting " + String(expected_value) + " on pin " + String(pin));
    if (result != expected_value) return false;
  }

  Serial.println("--- Tested Pattern ---");

  return true;
}

void loop() {

}
