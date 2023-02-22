#include "LightSensor.h"
#include <Arduino.h>
#define DAYLIGHT 400

LightSensor::LightSensor(int pin) {
    this->pin = pin;
    pinMode(this->pin, INPUT);
}

bool LightSensor::isDay() {
    int lightLevel = this->getLightLevel();
    #ifdef __DEBUG__
        Serial.print("light level detected: ");
        Serial.print(lightLevel);
        Serial.print(", daylight threshold: ");
        Serial.println(DAYLIGHT);
    #endif
    return lightLevel > DAYLIGHT;
}

int LightSensor::getLightLevel() {
    return analogRead(this->pin);
}