#include "LightTask.hpp"
#include <Arduino.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>
#include "../utils/ConnectionUtils.hpp"
#include "../sensors/LightSensor.h"
#define MSG_BUFFER_SIZE 50

extern PubSubClient client;
extern SemaphoreHandle_t mutex;

void LightTask(void* lightSensor) {
    const char* topic = "SensorBoard";
    LightSensor* sensor = (LightSensor*)lightSensor;
    for(;;) {
        char msg[MSG_BUFFER_SIZE];
        DynamicJsonDocument doc(128);
        doc["lightSensor"] = sensor->isDay();
        serializeJson(doc, msg);
        xSemaphoreTake(mutex, 1000);
        if (!client.connected()) {
            reconnect(&client, topic);
        }
        // Send msg via mqtt
        client.loop();
        client.publish(topic, msg);
        xSemaphoreGive(mutex);
        delay(500);
    }
};