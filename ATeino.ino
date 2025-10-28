const uint8_t pin_configuration[] = {
    9, A1, A2, A3, A4, A5, 7, 6, 5, 4, 3, 2
};

// 0 -> write low
// 1 -> write high
// L -> read low
// H -> read high
// X -> nothing read/write

const char pattern[][12] = {
  { '0', '1', 'L', 'L', 'L', 'L', '0', '1', 'L', 'L', 'L', 'L' },
  { '1', '0', 'L', 'L', 'L', 'L', '1', '0', 'L', 'L', 'L', 'L' },
  { '0', '0', 'H', 'L', 'L', 'L', '0', '0', 'H', 'L', 'L', 'L' },
  { '1', '0', 'H', 'L', 'L', 'L', '1', '0', 'H', 'L', 'L', 'L' },
  { '0', '0', 'L', 'H', 'L', 'L', '0', '0', 'L', 'H', 'L', 'L' },
  { '1', '0', 'L', 'H', 'L', 'L', '1', '0', 'L', 'H', 'L', 'L' },
  { '0', '0', 'H', 'H', 'L', 'L', '0', '0', 'H', 'H', 'L', 'L' },
  { '1', '0', 'H', 'H', 'L', 'L', '1', '0', 'H', 'H', 'L', 'L' },
  { '0', '0', 'L', 'L', 'H', 'L', '0', '0', 'L', 'L', 'H', 'L' },
};

void setup() {
  Serial.begin(9600);
  
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

  // 12 = Red, 11 = Green
  int LED = fails == 0 ? 12 : 11;

  pinMode(LED, OUTPUT);


  while(true) {
    digitalWrite(LED, HIGH);
    delay(200);
    digitalWrite(LED, LOW);
    delay(200);
  }
}

bool test(int pattern_index) {
  Serial.println("--- Starting Pattern ---");

  for(int i = 0; i < sizeof(pattern[pattern_index]) / sizeof(pattern[pattern_index][0]); i++) {
    int pin = pin_configuration[i];
    char type = pattern[pattern_index][i];

    //Serial.println("Pin " + String(pin) + " with type " + String(type));

    if(pin == 255) continue;
    switch(type) {
      case '0':
        pinMode(pin, OUTPUT);
        digitalWrite(pin, 0);
        break;
      case '1':
        pinMode(pin, OUTPUT);
        digitalWrite(pin, 1);
        break;
      case 'H':
      case 'L':
        break; // Do nothing here because we first write then read
      case 'X':
        break;
      default:
        Serial.println("Aborting Test. Unknown pin type: " + String(type));
        return false;
    }
  }

  for(int i = 0; i < sizeof(pattern[pattern_index]) / sizeof(pattern[pattern_index][0]); i++) {
    int pin = pin_configuration[i];
    char type = pattern[pattern_index][i];

    //Serial.println("Pin " + String(pin) + " with type " + String(type));

    if(pin == 255) continue;
    switch(type) {
      case '0':
      case '1':
        break; // Do nothing here because we first write then read
      case 'H':
        pinMode(pin, INPUT);
        if (digitalRead(pin) != 1) return false;
        break;
      case 'L':
        pinMode(pin, INPUT);
        if (digitalRead(pin) != 0) return false;
        break;
      case 'X':
        break;
      default:
        Serial.println("Aborting Test. Unknown pin type: " + String(type));
        return false;
      }
  }

  delay(5);


  Serial.println("--- Tested Pattern ---");

  return true;
}

void loop() {

}
