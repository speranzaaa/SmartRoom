#include <Arduino.h>
#include <Servo.h>
#include "SoftwareSerial.h"
#include "MsgServiceBT.h"

Servo servo;
MsgServiceBT msgService(2, 3);

void setup() {
  servo.attach(5);
  msgService.init();
  Serial.begin(9600);
  while (!Serial){}
  servo.write(0);

  delay(2000);
  Serial.println("Ready.");
}

void loop() {

  if (msgService.isMsgAvailable()) {
    Msg* msg = msgService.receiveMsg();
    Serial.println(msg->getContent());
    servo.write(msg->getContent().toInt());
  }

  delay(500);
}