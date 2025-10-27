const uint8_t pin_map[][3] = {
  {9, A1, A2},
  {A3, A4, A5},
  {7, 6, 5},
  {4, 3, 2}
};


void setup() {
  Serial.begin(9600);
  for(int i = 0; i < sizeof(pin_map) / sizeof(pin_map[0]); i++) {
    pinMode(pin_map[i][0], OUTPUT);
    pinMode(pin_map[i][1], OUTPUT);
    pinMode(pin_map[i][2], INPUT);
  }


  Serial.println();
  Serial.println("Testing chip: ");
  
  int successes = 0;
  int fails = 0;

  for(int i = 0; i < sizeof(pin_map) / sizeof(pin_map[0]); i++) {
    if(test(pin_map[i][0], pin_map[i][1], pin_map[i][2])) {
      successes++;
    } else {
      fails++;
    }
  }

  Serial.println("Ran " + String(successes + fails) + " tests.");
  Serial.println("Successes: " + String(successes));
  Serial.println("Fails: " + String(fails));
}

bool test(uint8_t pin_1, uint8_t pin_2, uint8_t output_pin) {
  digitalWrite(pin_1, HIGH);
  digitalWrite(pin_2, HIGH);
  if (digitalRead(output_pin) != 1) return false;
  digitalWrite(pin_1, HIGH);
  digitalWrite(pin_2, LOW);
  if (digitalRead(output_pin) != 0) return false;
  digitalWrite(pin_1, LOW);
  digitalWrite(pin_2, HIGH);
  if (digitalRead(output_pin) != 0) return false;
  digitalWrite(pin_1, LOW);
  digitalWrite(pin_2, LOW);
  if (digitalRead(output_pin) != 0) return false;

  return true;
}

void loop() {

}
