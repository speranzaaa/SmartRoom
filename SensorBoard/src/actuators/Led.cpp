#include "Led.h"

Led::Led(const int pin) {
    this->pin = pin;
    this->state = Led::OFF;
    pinMode(pin, OUTPUT);
}

void Led::turnOn() {
    if (this->state != Led::ON) {
        this->state = Led::ON;
        digitalWrite(pin, HIGH);
    }
}

void Led::turnOff() {
    if (this->state != Led::OFF) {
        this->state = Led::OFF;
        digitalWrite(pin, LOW);
    }
}

bool Led::isOn() {
    return Led::state == ON;
}