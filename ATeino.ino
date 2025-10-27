const uint8_t pin_map[][2][2] = {
  {{9, A1}, {A2, -1}},
  {{A3, A4}, {A5, -1}},
  {{7, 6}, {5, -1}},
  {{4, 3}, {2, -1}}
};

const uint8_t pattern[][2][2] = {
  {{LOW, LOW}, {LOW, -1}},
  {{LOW, HIGH}, {LOW, -1}},
  {{HIGH, LOW}, {LOW, -1}},
  {{HIGH, HIGH}, {HIGH, -1}}
};

void setup() {
  Serial.begin(9600);
  for(int i = 0; i < sizeof(pin_map) / sizeof(pin_map[0]); i++) {
    for(int j = 0; j < sizeof(pin_map[i][0]) / sizeof(pin_map[i][0][0]); j++) {
        pinMode(pin_map[i][0][j], OUTPUT);
    }
    for(int j = 0; j < sizeof(pin_map[i][1]) / sizeof(pin_map[i][1][0]); j++) {
      pinMode(pin_map[i][1][j], INPUT);
    }
  }

  Serial.println();
  Serial.println("Testing chip: ");
  
  int successes = 0;
  int fails = 0;

  for(int i = 0; i < sizeof(pin_map) / sizeof(pin_map[0]); i++) {
    if(test(pin_map[i][0], pin_map[i][1])) {
      successes++;
    } else {
      fails++;
    }
  }

  Serial.println("Ran " + String(successes + fails) + " tests.");
  Serial.println("Successes: " + String(successes));
  Serial.println("Fails: " + String(fails));
}

bool test(uint8_t input_pins[], uint8_t output_pins[]) {
  for(int i = 0; i < sizeof(pattern) / sizeof(pattern[0]); i++) {
    Serial.println("--- Starting pattern ---");
    for(int j = 0; j < sizeof(input_pins) / sizeof(input_pins[0]); j++) {
      if (input_pins[j] == 255) continue;
      Serial.println("Writing on pin: " + String(input_pins[j]) + " input nr. " + String(j) + " = " + String((pattern[i][0][j])));
      digitalWrite(input_pins[j], pattern[i][0][j]);
    }
    delay(100);
    for(int j = 0; j < sizeof(output_pins) / sizeof(output_pins[0]); j++) {
      int pin = output_pins[j];
      int result = digitalRead(pin);
      int expected_value = pattern[i][1][j];
      if (expected_value == 255) continue;
      Serial.println("Expecting " + String(expected_value) + " on pin " + String(pin) + " output nr. " + String(j));
      Serial.println("Got: " + String(result));
      if (result != expected_value) return false;
    }
    Serial.println("--- Pattern done --");
  }

  return true;
}

void loop() {

}
