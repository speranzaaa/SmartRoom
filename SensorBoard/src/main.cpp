#include <Arduino.h>
#include <WiFi.h>
#include <PubSubClient.h>
#include "./tasks/LightTask.hpp"
#define MSG_BUFFER_SIZE  50

/* wifi network info */

const char* ssid = "LittleBarfly";
const char* password = "303HotelLittleBarfly";

/* MQTT server address */
const char* mqtt_server = "broker.mqtt-dashboard.com";

/* MQTT topic */
const char* topic = "esiot-2022";

/* MQTT client management */

WiFiClient espClient;
PubSubClient client(espClient);


unsigned long lastMsgTime = 0;
char msg[MSG_BUFFER_SIZE];
int value = 0;

TaskHandle_t Task1;
TaskHandle_t Task2;
SemaphoreHandle_t mutex;

portMUX_TYPE lockTest = portMUX_INITIALIZER_UNLOCKED;
volatile int count = 0;

// void lightTaskCode(void* taskToExecute) {
//     ((LightTask*)taskToExecute)->ToExecute();
//     delay(100);
// }

void setup() {
    // setup_wifi();
    // randomSeed(micros());
    client.setServer(mqtt_server, 1883);
    client.setCallback(callback);
    Serial.begin(9600);
    mutex = xSemaphoreCreateMutex();
    // xSemaphoreGive(&semaphore);
    // xTaskCreatePinnedToCore(lightTaskCode, "FirstTask", 10000, (void*)lightTask, 1, &Task1, 0);
    delay(500);
    // xTaskCreatePinnedToCore(SecondTaskCode, "SecondTask", 10000, NULL, 1, &Task2, 1);
    // delay(500);
}

void loop() {
    Serial.print("Main loop running on core ");
    Serial.println(xPortGetCoreID());
    Serial.flush();
    delay(1000);
}


void setup_wifi() {

    delay(10);

    Serial.println(String("Connecting to ") + ssid);

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

/* MQTT subscribing callback */

void callback(char* topic, byte* payload, unsigned int length) {
    Serial.println(String("Message arrived on [") + topic + "] len: " + length );
}


// void loop() {
//     if (!client.connected()) {
//         reconnect();
//     }
//     client.loop();
//     unsigned long now = millis();
//     if (now - lastMsgTime > 10000) {
//         lastMsgTime = now;
//         value++;
//         /* creating a msg in the buffer */
//         snprintf (msg, MSG_BUFFER_SIZE, "hello world #%ld", value);
//         Serial.println(String("Publishing message: ") + msg);
//         /* publishing the msg */
//         client.publish(topic, msg);  
//     }
// }