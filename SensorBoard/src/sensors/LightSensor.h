#ifndef __LIGHT_SENSOR_H__
#define __LIGHT_SENSOR_H__

class LightSensor {
public:
    LightSensor(int pin);
    bool isDay();
private:
    int getLightLevel();
    int pin;
};

#endif