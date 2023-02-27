#ifndef __LED__
#define __LED__

#include <Arduino.h>

class Led {

    private:
        enum {ON, OFF} state;
        int pin;
    
    public:
        Led(const int pin); 
        void turnOn();
        void turnOff();
        bool isOn();
};

#endif