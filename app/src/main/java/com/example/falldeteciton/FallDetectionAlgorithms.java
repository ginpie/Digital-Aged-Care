/**
 * This class identifies the behavior pattern based on the data collected
 * in a period of time (normally 500 ~ 1000 ms).
 *
 * Authors:         Jinpei Chen
 *                  Yuzhao Li
 *
 * Created data:    02/09/2019
 * Last modified:   02/09/2019
 */
package com.example.falldeteciton;

public class FallDetectionAlgorithms {

    /**
     * The method determines if a fall-like event happens
     * @param acc
     * @return true/false
     */
    public boolean isfall(float[] acc){
        // TODO: Fall-like event identification
        return false;
    }


    /**
     * The method determines the current posture based on a data sequence of the Gyroscope.
     * @param gyro
     * @return The pattern number,  0 - no data, 1 - swing, 2 - stretch, 3 - throw, 4 - ...
     */
    public int gyroPosture(float[] gyro){
        // TODO: POSTURE IDENTIFICATION ALGORITHMS
        return 0;
    }

    /**
     * The method determines if a data sequence is a falling event
     * @param data
     * @return The pattern number, 0 - not fall, 1 - fall, 2 - trip, 3 - slip
     */
    public int fallPattern(float[] data) {
        // TODO: Pattern recognition
        return 0;
    }
}
