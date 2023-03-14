#include <Arduino.h>
#include <WiFi.h>
#include <PubSubClient.h>
#include "./tasks/LightTask.hpp"
#include "./tasks/PirTask.hpp"
#include <ArduinoJson.h>
#define MSG_BUFFER_SIZE  50

/* wifi network info */

const char* ssid = "POCO X3 NFC";
const char* password = "12345678";

/* MQTT server address */
const char* mqtt_server = "broker.mqtt-dashboard.com";

/* MQTT topic */
const char* topic = "room-sensor-board";

/* MQTT client management */

WiFiClient espClient;
PubSubClient* client = new PubSubClient(espClient);


char msg[MSG_BUFFER_SIZE];

TaskHandle_t Task1;
TaskHandle_t Task2;
SemaphoreHandle_t dayMutex;
SemaphoreHandle_t presenceMutex;
volatile bool isDay = false;
volatile bool isPresenceDetected = false;

void setup_wifi() {
    WiFi.mode(WIFI_STA);
    WiFi.begin(ssid, password);

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
    }
}


void setup() {
    setup_wifi();
    randomSeed(micros());
    client->setServer(mqtt_server, 1883);
    dayMutex = xSemaphoreCreateMutex();
    presenceMutex = xSemaphoreCreateMutex();
    if (dayMutex == NULL || presenceMutex == NULL) {
        exit(1);
    }
    xTaskCreatePinnedToCore(LightTask, "LightTask", 10000, NULL, 1, &Task1, 0);
    delay(100);
    xTaskCreatePinnedToCore(PirTask, "PirTask", 10000, NULL, 1, &Task2, 0);
}

// Main loop that reads values of isDay and isPresenceDetected and sends via mqtt
void loop() {
    bool currDay;
    bool currPresence;
    while(xSemaphoreTake(dayMutex, 100) == pdFALSE){
        delay(1000);
    }
    currDay = isDay;
    xSemaphoreGive(dayMutex);
    while(xSemaphoreTake(presenceMutex, 100) == pdFALSE){
        delay(1000);
    }
    currPresence = isPresenceDetected;
    xSemaphoreGive(presenceMutex);
    char msg[MSG_BUFFER_SIZE];
    DynamicJsonDocument doc(128);
    doc["day"] = currDay;
    doc["presence"] = currPresence;
    serializeJson(doc, msg);
    while (!client->connected()) {
        Serial.flush();
        
        // Create a random client ID
        String clientId = String("esiot-2122-client-")+String(random(0xffff), HEX);

        // Attempt to connect
        if (client->connect(clientId.c_str())) {
        } else {
            delay(500);
        }
    }
    client->loop();
    client->publish(topic, msg);
    delay(10000);
}