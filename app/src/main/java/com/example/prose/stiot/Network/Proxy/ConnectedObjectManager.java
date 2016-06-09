/**
 * *
 *  * @file      ConnectedObjectManager.java
 *  * @author    Arnaud B
 *  * @version   1.0
 *  * @date      19 April 2016
 *  * @copyright (The MIT Licence) Copyright (c) 2016 Prose2017-EquipeB1, ESEO, STMicroelectronics
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
 *  * (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge,
 *  * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 *  * subject to the following conditions:
 *  * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 *  * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 *  * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.example.prose.stiot.Network.Proxy;

import com.example.prose.stiot.Network.Postman;

/**
 * @fn ConnectedObjectManager
 * @brief this class is a proxy to communicate with Sgi
 * the postman allow to send messages
 * there are 3 proxy :
 * modifyThresholds
 * askCoList ask to Sgi to send the list of ConnectedObject
 * this method is the first call by the programme.
 * setSettings
 */
public class ConnectedObjectManager {
    private Postman postman;
    public FctUsedFromSgaToSgi fctUsedFromSgaToSgi;
    private static String SEPARATOR=" ";/**Character to split the message to send between each argument in the message */


    public ConnectedObjectManager(Postman postman) {
        this.postman = postman;

    }
    /**
     * @fn modifyThresholds
     * @brief this method send to Sgi a Thresholds of one ConnectedObject with the measure
     *
     * */
    public void modifyThresholds(int coId, int treashold,String sensorNameReceived){
        postman.writeMessage(Integer.toString(fctUsedFromSgaToSgi.NOTIFY_THRESHOLDS.ordinal())
                +SEPARATOR+Integer.toString(coId)
                +SEPARATOR+Integer.toString(treashold)
                +SEPARATOR+sensorNameReceived);
    }

    /**
     * @fn askCoList
     * @brief this method ask le list of ConnectedObject this method
     * is call in fist to SGA
     * the message send to postman is "2"
     * */
    public void askCoList(){
        this.postman.writeMessage(Integer.toString(fctUsedFromSgaToSgi.ASK_CO_LIST.ordinal()));
    }

    /**
     * @fn setSettings
     * @brief
     *
     * */
    public  void setTimingScan(int frequency){
        /*TODO Definit le format du TIME*/
        this.postman.writeMessage(Integer.toString(fctUsedFromSgaToSgi.SET_TIMING_SCAN.ordinal()));
    }
}
