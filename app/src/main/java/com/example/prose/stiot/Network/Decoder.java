/**
 * *
 *  * @file      Dispatchor.java
 *  * @author    Arnaud B
 *  * @version   1.0
 *  * @date      3 May 2016
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

package com.example.prose.stiot.Network;

import android.util.Log;

/**
 * @fn Decoder
 * @brief this class decrypts
 * the first information message to know
 * what method use to Scheduler
 */
public class Decoder extends Thread{
    private String message;                 /**< message receive by ApplicationConnection */
    private final String SEPARATOR =" ";    /**< Character that separate information in message */
    public int value;
    private Scheduler scheduler;            /**<  */
    public String testString;


    public Decoder(){
        this.message=null;
        this.scheduler =new Scheduler();
    }
    public Decoder(Scheduler scheduler){
        this.message=null;
        this.scheduler = scheduler;
    }
    /**
     * @fn newMessage
     * @brief this method is call in ApplicationConnection when a message is receive
     * by the postman, it's send the message to processMessage
     *
     * @param message message receive by ApplicationConnection.
     *                ApplicationConnection call this method to treat
     *                the message
     * */
    public void newMessage (String message){
        this.message=message;
        Log.d("Decoder newMessage","Start thread");
        processMessage(message);
    }

    /**
     * @fn processMessage
     * @brief this method spilt the message to ApplicationConnection
     * each information are contain in a array
     * the first information message define what method use
     * the other information are method parameters
     *
     * @param message the message to split
     * */

    private void processMessage(String message){
        String[] splitArray;                      //Array contain each information message
        splitArray = message.split(SEPARATOR);    //The message is split each SEPaRATOR
//        testString = splitArray[1];
        Log.e("decoder splitArray->",splitArray[0]);

        switch(splitArray[0]) {
            case "0":/**NOTIFY_THRESHOLD_EXCEEDED*/
                scheduler.notifyThresholdExceeded(splitArray[1],splitArray[2]) ;   //slitArray[1]= ID ConnectedObject      slitArray[2]= Mesure name*/
                Log.e("Decoder value->","0  call fct NOTIFY_THRESHOLD_EXCEEDED ");
                break;

            case "1":/**NOTIFY_CO_CONNECTION*/
                scheduler.notifyCoConnection(splitArray[1],splitArray[2]);     //slitArray[1]= ID ConnectedObject      slitArray[2]= Connection State ConnectedObject*/
                Log.e("Decoder value->","1  call fct NOTIFY_CO_CONNECTION ");
                //testString = "OKCO";
                break;

            case "2":/**SET_ALL_DATA_OF_ONE_SENSOR*/
                scheduler.setAllDataOfOneCo(splitArray[1],splitArray[2],splitArray[3],splitArray[4],splitArray[5],splitArray[6]);   //coId sensorName oneMeasureForThisSensor oneThresholdStateForThisSensor oneDateForThisMeasure
                Log.e("Decoder value->","2  call fct SET_ALL_DATA_OF_ONE_SENSOR ");;
                break;

            case "3":/**SET_LAST_MEASURES_OF_ALL_COS*/
                scheduler.setLastMeasuresOfAllCos(splitArray[1],splitArray[2],splitArray[3],splitArray[4],splitArray[5],splitArray[6]); //coId sensorName measureForThisSensor thresholdStateForThisSensor dateForThisMeasure
                break;

            case "4":/**APPEND_CO_IN_LIST*/
                scheduler.appendCoInList(splitArray[1],splitArray[2],splitArray[3],splitArray[4],splitArray[5]);
                Log.e("Decoder value->","4 call fcT APPEND_CO_IN_LIST ");
                break;

            default:
                Log.e("Decoder value->","call nothing");
                break;
        }
    }

    public int getValue() {
        return value;
    }
    public void setValue(int a){
        this.value=a;
    }
    public String getTestString(){return testString;}
    public void TestFunction(){
        scheduler.notifyThresholdExceeded("1", "temperature");
    }

}




