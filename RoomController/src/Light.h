#ifndef LIGHT
#define LIGHT

class Light {
public:
    virtual bool isOn() = 0;
    virtual void turnOn() = 0;
    virtual void turnOff() = 0;
};

#endif