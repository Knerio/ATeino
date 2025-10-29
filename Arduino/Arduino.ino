#define CMD_READ  0x01
#define CMD_WRITE_HIGH   0x02
#define CMD_WRITE_LOW   0x03


void setup() {
  Serial.begin(9600);

  Serial.println("Serial now on");
}

void loop() {
  if(Serial.available() >= 2) {
      byte buffer[2];
      Serial.readBytes(buffer, 2);
       
      byte command = buffer[0];
      byte pin = buffer[1];

      switch(command) {
        case CMD_READ:
          pinMode(pin, INPUT);
          Serial.println(digitalRead(pin));
          break;
        case CMD_WRITE_LOW:
          pinMode(pin, OUTPUT);
          digitalWrite(pin, LOW);
          Serial.println("true");
          break;
        case CMD_WRITE_HIGH:
          pinMode(pin, OUTPUT);
          digitalWrite(pin, HIGH);
          Serial.println("true");
          break;
        default:
          Serial.println("false");
      } 
  }
}

