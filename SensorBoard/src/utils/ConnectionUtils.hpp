#ifndef __CONN_UTILS_H__
#define __CONN_UTILS_H__
#include <WiFi.h>
#include <PubSubClient.h>

void setup_wifi(const char* ssid, const char* password);

void callback(char* topic, byte* payload, unsigned int length);

void reconnect(PubSubClient* client, const char* topic);

#endif