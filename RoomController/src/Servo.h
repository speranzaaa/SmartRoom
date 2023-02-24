#ifndef __SERVOMOTOR__
#define __SERVOMOTOR__

#include "Arduino.h"
#include "Sensor.h"
#include "Utils.h"
#include <Servo.h>


class ServoMotor : public Component {
  public:
    ServoMotor(const int pin); 
    void move(int percentage);
    int getOpeningPercentage();

  private:
    Servo servo;
    int percentage; 
};

#endif