#ifndef LED
#define LED

#include "Light.h"

class Led : public Light {
public:
    Led(int pin);
    bool isOn();
    void turnOn();
    void turnOff();
private:
    int pin;
    bool powered;
};

#endif