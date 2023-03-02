#include "RollerBlinds.h"

RollerBlinds::RollerBlinds(const int pin) : Component(pin) {
    this->attach(pin);
}

void RollerBlinds::move(int percentage) {
  int newAngle = map(percentage, 0, 100, 180, 0);
  this->angle = newAngle;
  this->write(newAngle);
}

int RollerBlinds::getAngle() {
  return this->angle;
}

int RollerBlinds::getOpeningPercentage(){
  return map(this->angle, 0, 180, 100, 0);
}