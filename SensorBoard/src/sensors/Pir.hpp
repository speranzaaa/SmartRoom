#ifndef __PIR_H__
#define __PIR_H__

/**
 * @brief Class that controls a pir sensor
 * 
 */
class Pir {
private:
    const unsigned long CALIBRATION_TIME = 10000;
    unsigned int pin;
public:
    /**
     * @brief Construct a new Pir object, calls a delay
     * to calibrate the sensor
     * 
     * @param pin the pin the pir is connected to
     */
    Pir(unsigned int pin);

    /**
     * @brief Returns a boolean value based on the
     * detection of movement
     * 
     * @return true if movement is detected
     * @return false if no movement is detected
    */
    bool isDetected();
};

#endif