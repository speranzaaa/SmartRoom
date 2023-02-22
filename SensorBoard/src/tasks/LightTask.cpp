#include "LightTask.hpp"
#include <Arduino.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>
#include "../utils/ConnectionUtils.hpp"
#include "../sensors/LightSensor.h"

extern PubSubClient client;
extern SemaphoreHandle_t mutex;

void LightTask(void* lightSensor) {
    char* topic = "SensorBoard";
    LightSensor* sensor = (LightSensor*)lightSensor;
    for(;;) {
        String msg;
        DynamicJsonDocument doc(128);
        doc["lightSensor"] = sensor->isDay();
        serializeJson(doc, msg);
        xSemaphoreTake(mutex, 1000);
        if (!client.connected()) {
            reconnect(&client, topic);
        }
        // Send msg via mqtt
        xSemaphoreGive(mutex);
        delay(500);
    }
};