#ifndef __ROOM__
#define __ROOM__

#include "RollerBlinds.h"
#include "Led.h"

class SmartRoom {
  RollerBlinds* servoMotor;
  Led* led;

public:
  SmartRoom(RollerBlinds* servoMotor, Led* led);

  void setLedState(bool state);
  bool getLedState();

  void setServoOpening(int percentage);
  int getServoOpening();

};
#endif