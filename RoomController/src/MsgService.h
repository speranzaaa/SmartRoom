#ifndef __MSGSERVICE__
#define __MSGSERVICE__

#include "Arduino.h"
#include "ArduinoJson.h"

/**
 * @brief Class used to send and receive messages over serial
 */
class MsgService {
    
public: 
    /**
     * @brief Initializes the message service
     */
    void init();  
    /**
     * @brief Sends a message
     * 
     * @param msg The message to be sent
     */
    void sendMsg(String msg);
};

#endif