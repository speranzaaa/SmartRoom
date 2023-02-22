#include "ConnectionUtils.hpp"

void setup_wifi(const char* ssid, const char* password) {

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

void callback(char* topic, byte* payload, unsigned int length) {
    Serial.println(String("Message arrived on [") + topic + "] len: " + length );
}

void reconnect(PubSubClient* client, const char* topic) {
    // Loop until we're reconnected
    while (!client->connected()) {
        // Serial.print("Attempting MQTT connection...");
        
        // Create a random client ID
        String clientId = String("esiot-2122-client-")+String(random(0xffff), HEX);

        // Attempt to connect
        if (client->connect(clientId.c_str())) {
            // Serial.println("connected");
            // ... and resubscribe
            client->subscribe(topic);
        } else {
            // Serial.print("failed, rc=");
            // Serial.print(client->state());
            // Serial.println(" try again in 5 seconds");
            // Wait 5 seconds before retrying
            delay(5000);
        }
    }
}