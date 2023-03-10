#include "RollerBlinds.h"

RollerBlinds::RollerBlinds(const int pin) : Component(pin) , Servo(){
    this->attach(pin);
}

void RollerBlinds::move(int percentage) {
  int newAngle = (180*percentage)/100;
  this->angle = newAngle;
  this->write(newAngle);
}

int RollerBlinds::getAngle() {
  return this->angle;
}

int RollerBlinds::getOpeningPercentage(){
  return (this->angle*100)/180;
  }