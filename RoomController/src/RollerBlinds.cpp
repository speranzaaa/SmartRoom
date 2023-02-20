#include "Valve.h"
#include <Arduino.h>
#include "Config.h"
// #include "EnableInterrupt.h"

extern Status currentStatus;
extern volatile bool manual;
extern double waterDistance;
extern int valveOpening;


Valve::Valve(int potPin, int servoPin, unsigned long period) : Task(period) {
    this->servoPin = servoPin;
    this->servo.attach(this->servoPin);
    this->pot = new Pot();
}

void Valve::toExecute() {
    if (currentStatus == ALARM) {
        switch (manual)
        {
        case false:
            if (waterDistance == 0) {
                valveOpening = 180;
            } else {
                valveOpening = 180-(180*(waterDistance)/(WL_MAX));
            };
            this->servo.write(valveOpening);
            break;
        case true:
            valveOpening = this->pot->getValveValue();
            this->servo.write(valveOpening);
            break;
        }
    } else {
        valveOpening = 0;
        this->servo.write(valveOpening);
    }
}


