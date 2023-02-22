#include "LightTask.hpp"
#include <Arduino.h>
#include "../sensors/LightSensor.h"
#include "../utils/Config.hpp"
#define MSG_BUFFER_SIZE 50

extern SemaphoreHandle_t dayMutex;
extern volatile bool isDay;

void LightTask(void* param) {
    LightSensor* lightSensor = new LightSensor(LIGHT_SENSOR_PIN);
    for(;;) {
        while(xSemaphoreTake(dayMutex, 100) == pdFALSE){
            Serial.println("mutex not taken, delaying for 1 sec");
            delay(1000);
        }
        isDay = lightSensor->isDay();
        Serial.println("Light sensor data updated");
        xSemaphoreGive(dayMutex);
        delay(500);
    }
};