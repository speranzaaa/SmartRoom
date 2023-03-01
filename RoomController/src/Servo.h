#ifndef __SERVOMOTOR__
#define __SERVOMOTOR__

#include "Arduino.h"
#include "Sensor.h"

class Servo : public Component {
  
  int angle = 0;

  public:
    Servo(const int pin); 
    void move(int percentage);
    int getAngle();
    int getOpeningPercentage();
    
};

#endif