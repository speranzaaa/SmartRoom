#ifndef __CONN_UTILS_H__
#define __CONN_UTILS_H__
#include <WiFi.h>
#include <PubSubClient.h>

void reconnect(PubSubClient* client, char* topic) {
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

#endif