/**
 * *
 *  * @file      DataManager.java
 *  * @author    Arnaud B
 *  * @version   1.0
 *  * @date      5 Mai 2016
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

import android.util.Log;

import com.example.prose.stiot.DetailActivity;
import com.example.prose.stiot.MyService;
import com.example.prose.stiot.Network.Postman;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @fn  DataManager
 * @brief this class is a proxy to communicate with Sgi
 * the postman allow to send messages
 * there are 2 proxy :
 * askAllDataOfOneCo : this proxy is call when the user is in DetailActivity
 * in fact we must to know all data of each captor of one ConnectedObject
 * askLastMeasuresOfAllCos : this proxy is call when user is in HomeActivity
 * this methode allow to know all last measures of all ConnectedObject
 */
public class DataManager {
    Postman postman;
    FctUsedFromSgaToSgi fctUsedFromSgaToSgi;
    private static String SEPARATOR=" ";/**Character to split the message to send between each argument in the message */


    /**
     * @fn DataManager
     * @brief Constructor of DataManager need a postman to send messages
     * @param postman
     */
    public DataManager(Postman postman){
        this.postman=postman;
    }

    /**
     *@fn askAllDaraOfOneCo
     *@brief this methode is call in the DetailActivity
     *it ask to Sgi all data of each measure of one ConnectedObject up to the specific date,
     *the message send to Sgi is "3 coId"
     *@param coId the ID ConnectedObject,it select the ConnectedObject to get all data
     *@param date date selected by the user it determine  up to the specific date to observe data
     *@param hour hour selected by the user it determine  up to the specific hour to observe data
     * */
    public  void askAllDataOfOneCo(int coId,String date, String hour){
        DateFormat toDay = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String actualDate = toDay.format(new Date());
        postman.writeMessage(3
                +SEPARATOR+coId
                +SEPARATOR+actualDate
                +SEPARATOR+date
                +SEPARATOR+hour+":00");
        Log.e("DataManager","AskAllDataOfOneCo ok with"+ 3
                +SEPARATOR+coId
                +SEPARATOR+actualDate
                +SEPARATOR+date
                +SEPARATOR+hour+":00");
    }

    /**
     * @fn askLastMeasuresOfAllCos
     * @brief this method ask to Sgi all last measure of each ConnectedObject
     * it call in the HomeActivity
     * the message send to Sgi is "4"
     * */
    public void askLastMeasuresOfAllCos(){
        postman.writeMessage(Integer.toString(fctUsedFromSgaToSgi.ASK_LAST_MEASURES_OF_ALL_COS.ordinal()));
    }


}