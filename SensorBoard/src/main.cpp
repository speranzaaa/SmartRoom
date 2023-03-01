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

    Serial.println(String("Connecting to ") + ssid);
    Serial.flush();

    WiFi.mode(WIFI_STA);
    WiFi.begin(ssid, password);

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    Serial.println("");
    Serial.println("WiFi connected");
    Serial.println("IP address: ");
    Serial.println(WiFi.localIP());
}

void callback(char* topic, byte* payload, unsigned int length) {
    Serial.println(String("Message arrived on [") + topic + "] len: " + length );
}

void setup() {
    Serial.begin(9600);
    setup_wifi();
    randomSeed(micros());
    client->setServer(mqtt_server, 1883);
    client->setCallback(callback);
    dayMutex = xSemaphoreCreateMutex();
    presenceMutex = xSemaphoreCreateMutex();
    if (dayMutex == NULL || presenceMutex == NULL) {
        Serial.println("failed to create mutex, quitting");
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
    // Serial.println("Sensor data obtained, publishing to topic");
    char msg[MSG_BUFFER_SIZE];
    DynamicJsonDocument doc(128);
    doc["isDay"] = currDay;
    doc["isPresence"] = currPresence;
    serializeJson(doc, msg);
    // serializeJson(doc, Serial);
    while (!client->connected()) {
        Serial.print("Attempting MQTT connection...");
        Serial.flush();
        
        // Create a random client ID
        String clientId = String("esiot-2122-client-")+String(random(0xffff), HEX);

        // Attempt to connect
        if (client->connect(clientId.c_str())) {
            Serial.println("connected");
        } else {
            Serial.print("failed, rc=");
            Serial.println(client->state());
            delay(500);
        }
    }
    client->loop();
    client->publish(topic, msg);
    delay(1000);
}