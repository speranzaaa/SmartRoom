#ifndef __SERVOMOTOR__
#define __SERVOMOTOR__

#include "Arduino.h"
#include "Sensor.h"
#include <Servo.h>


class RollerBlinds : public Component, public Servo {
  
  int angle = 0;

  public:
    RollerBlinds(const int pin); 
    void move(int percentage);
    int getAngle();
    int getOpeningPercentage();
    
};

#endif