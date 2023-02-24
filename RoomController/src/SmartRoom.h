#ifndef __ROOM__
#define __ROOM__

#include "Servo.h"
#include "Led.h"

class SmartRoom {
  Servo* servoMotor;
  Led* led;

public:
  SmartRoom(Servo* servoMotor, Led* led);

  void setLedState(bool state);
  bool getLedState();

  void setServoOpening(int percentage);
  int getServoOpening();

};
#endif