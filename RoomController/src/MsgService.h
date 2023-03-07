#ifndef __MSGSERVICE__
#define __MSGSERVICE__

#include "Arduino.h"
#include "ArduinoJson.h"

class Msg {

private:
    String msg;

public:
    Msg(String content) {
        this->msg = content;
    }
    String getContent() {
        return this->msg;
    }
};

class MsgService {    
public:   
    void init();  
    void sendMsg(bool ledState, int servoOpening);
    void sendMsg(String msg);
    bool isMessageAvailable();
    Msg* receiveMsg();
    Msg* currentMsg;
    bool messageAvailable;
};

#endif