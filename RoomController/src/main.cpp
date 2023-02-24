#include "Scheduler.h"
#include "Servo.h"
#include "Led.h"
#include "SmartRoom.h"
#include "BTTask.h"
#include "SerialTask.h"
#include "Utils.h"

Scheduler scheduler(SCHEDULER_PERIOD);

void setup(){
  ServoMotor* servo = new ServoMotor(SERVOMOTOR_PIN);
  Led* led = new Led(LED_PIN);
  SmartRoom* smartRoom = new SmartRoom(servo, led);
  SerialTask* serialTask = new SerialTask(smartRoom);
  BTTask* BTTask = new BTTask(BT_RX_PIN, BT_TX_PIN, smartRoom);

  scheduler.init();
  
  BTTask->init(BT_PERIOD);
  scheduler.addTask(BTTask);

  serialTask->init(SERIAL_PERIOD);
  scheduler.addTask(serialTask);
}

void loop(){
  scheduler.schedule();
}