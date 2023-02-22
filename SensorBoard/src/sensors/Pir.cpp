#include "Pir.hpp"
#include "Arduino.h"

extern bool debug;

Pir::Pir(unsigned int pin)
{
    this->pin = pin;
    pinMode(this->pin, INPUT);
    delay(this->CALIBRATION_TIME);
}

bool Pir::isDetected()
{
    return digitalRead(this->pin) == HIGH;
}