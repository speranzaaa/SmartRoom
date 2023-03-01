#include "Servo.h"

Servo::Servo(const int pin) : Component(pin) {
    
}

void Servo::move(int percentage) {
  int newAngle = map(percentage, 0, 100, 180, 0);
  this->angle = newAngle;
  analogWrite(this->pin, newAngle);  
}

int Servo::getAngle() {
  return this->angle;
}

int Servo::getOpeningPercentage(){
  return map(this->angle, 0, 180, 100, 0);
}