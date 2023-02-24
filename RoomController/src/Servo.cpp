#include "Servo.h"
#include "Arduino.h"
#include <Servo.h>

ServoMotor::ServoMotor(const int pin) : Component(pin) {
  servo.attach(pin);
  percentage = 0;
}

void ServoMotor::move(int percentage) {
  this->percentage = percentage;
  servo.write(percentage);
}

int ServoMotor::getOpeningPercentage() {
  return percentage;
}
