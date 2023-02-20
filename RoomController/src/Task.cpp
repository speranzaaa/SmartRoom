#include "Task.h"
#include <Arduino.h>

Task::Task(unsigned long period) {

    this->period = period;
}

unsigned long Task::getPeriod() {
    return this->period;
}

void Task::tick() {
    unsigned long currentTime = millis();
    if (currentTime - this->lastExecuted >= this->getPeriod()) {
        #ifdef __DEBUG__
            Serial.println("Executing task.");
        #endif
        this->toExecute();
        this->lastExecuted = currentTime;
    }
}

void Task::setPeriod(unsigned long period) {
    this->period = period;
}