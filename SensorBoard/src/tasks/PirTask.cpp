#include "PirTask.hpp"
#include "../sensors/Pir.hpp"
#include "../utils/Config.hpp"
#include <Arduino.h>

extern SemaphoreHandle_t presenceMutex;
extern volatile bool isPresenceDetected;

void PirTask(void* param) {
    Pir* pirSensor = new Pir(PIR_PIN);
    for (;;) {
        while (xSemaphoreTake(presenceMutex, 100) == pdFALSE) {
            delay(1000);
        }
        isPresenceDetected = pirSensor->isDetected();
        Serial.println("Pir sensor data updated");
        xSemaphoreGive(presenceMutex);
        delay(500);
    }
};