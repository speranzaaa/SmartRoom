#include "SmartRoom.h"

SmartRoom::SmartRoom(RollerBlinds* servoMotor, Led* led) : 
  servoMotor(servoMotor), led(led) {}

void SmartRoom::setLedState(bool state){
  if(state){
    led->turnOn();
  }
  else{
    led->turnOff();
  }
}

bool SmartRoom::getLedState(){
  return led->isOn();
}

void SmartRoom::setServoOpening(int percentage){
  servoMotor->move(percentage);
}

int SmartRoom::getServoOpening(){
  return servoMotor->getOpeningPercentage();
}