#include "Led.h"
#include "Arduino.h"

Led::Led(int pin) {
    this->pin = pin;
    pinMode(pin, OUTPUT);
    this->turnOff();
}

bool Led::isOn() {
    return powered;
}

void Led::turnOn() {
    if(!isOn()){
        digitalWrite(pin, HIGH);
        powered = true;
    }
}

void Led::turnOff() {
    if(isOn()) {
        digitalWrite(pin, LOW);
        powered = false;
    }
}