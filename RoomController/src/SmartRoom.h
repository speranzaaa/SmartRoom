#ifndef __ROOM__
#define __ROOM__

#include "Servo.h"
#include "Led.h"

class SmartRoom {
  ServoMotor* servoMotor;
  Led* led;

public:
  SmartRoom(ServoMotor* servoMotor, Led* led);

  void setLedState(bool state);
  bool getLedState();

  void setServoMotorOpening(int percentage);
  int getServoMotorOpening();

};
#endif