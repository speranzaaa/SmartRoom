#include "PirTask.hpp"
#include "../sensors/Pir.hpp"
#include "../actuators/Led.h"
#include "../utils/Config.hpp"
#include <Arduino.h>

extern SemaphoreHandle_t presenceMutex;
extern volatile bool isPresenceDetected;

void PirTask(void* param) {
    Pir* pirSensor = new Pir(PIR_PIN);
    Led* led = new Led(LED_PIN);
    for (;;) {
        while (xSemaphoreTake(presenceMutex, 100) == pdFALSE) {
            delay(1000);
        }
        isPresenceDetected = pirSensor->isDetected();
        xSemaphoreGive(presenceMutex);
        if (isPresenceDetected) {
            led->turnOn();
        } else {
            led->turnOff();
        }
        delay(500);
    }
};