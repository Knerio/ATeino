void setup() {
  Serial.begin(9600);
  pinMode(9, OUTPUT);
  pinMode(2, INPUT_PULLUP);
}

void loop() {
  int brightness = analogRead(A5) * 0.25; 
  if (brightness < 50) brightness = 0;
  analogWrite(9, brightness);
  Serial.println(brightness);
}
