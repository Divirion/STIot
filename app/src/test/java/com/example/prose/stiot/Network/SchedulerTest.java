/**
 * @author Arnaud B
 * @version 1.0
 * @file SchedulerTest.java
 * @projet com.example.prose.stiot.Network
 * @date 19/05/16
 * @brief Contents of ItemOC
 * @copyright (The MIT Licence) Copyright (c) 2016 Prose2017-EquipeB1, ESEO, STMicroelectronics
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
 * (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.example.prose.stiot.Network;


import com.example.prose.stiot.MyService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.validator.PublicClassValidator;

import static org.junit.Assert.*;




public class SchedulerTest {

    public Scheduler scheduler;

    /**
     * @fn SetUp
     * @brief this method initialize 2 Connected Objects Motor and Conveyer
     * motor had one sensor "temperature" with 5 measures
     * Conveyer had one sensor "Speed" with 2 measures
     *
     * initial configuration test
     * */
    @Before
    public void setUp() throws Exception {
        scheduler=new Scheduler();
        scheduler.appendCoInList("0","Motor","temperature","20","C");
        scheduler.appendCoInList("1","Conveyer","Speed","20","m/S");
        scheduler.setAllDataOfOneCo("1","Speed","25","1","25/02/05","20:15:12");
        scheduler.setAllDataOfOneCo("0","temperature","25","1","25/02/05","15:15:12");
        scheduler.setAllDataOfOneCo("0","temperature","27","1","25/02/05","15:15:30");
        scheduler.setAllDataOfOneCo("0","temperature","30","1","25/02/05","15:16:12");
        scheduler.setAllDataOfOneCo("0","temperature","14","1","25/02/05","15:25:15");
    }

    /**
     * @fn testPostSetUp
     * @brief Verify that setUp is well executed.
     * it verify that :
     * -the number CO is 2
     * -Co Motor had 1 sensor
     * -Unit of sensor of Motor
     * -Co Motor had 5 measures  and that the first Co in listCo with this name
     * -Co Conveyer had 2 measures
     * */
    @Test
    public void testPostSetUp()throws  Exception{
        assertEquals(MyService.listConnectedObject.size(),2);                                   //Verify the number CO in listCo
        assertEquals(MyService.listConnectedObject.get(0).getName(),"Motor");                   //Verify the name of the first CO in listCo
        assertEquals(MyService.listConnectedObject.get(0).sensorList.size(),1);                 //Verify the number Sensor in Motor
        assertEquals(MyService.listConnectedObject.get(0).sensorList.get(0).getUnit(),"C");     //Verify sensor unit
        assertEquals(MyService.listConnectedObject.get(0).sensorList.get(0).listData.size(),5); //Verify that Motor had 5 measure
        assertEquals(MyService.listConnectedObject.get(1).getName(),"Conveyer");
    }



    /**
     * @fn testAppendCoInList
     * @brief Verify a insert one Co in listCo
     * it verify that :
     * - the listCo is 3
     * */
    @Test
    public void testAppendCoInList() throws Exception {
        scheduler.appendCoInList("2","Loader","number","20","nbr"); //Insert a new Co in listCo
        assertEquals(MyService.listConnectedObject.size(),3);                    //Verify the new size of listCO
    }

    /**
     * @fn testAppendCoInListWithTheSameID()
     * @brief Verify insert one Co in listCo with a idCo already use
     * result :
     * -the new Co is not insert
     * -sensorName of Co not insert is insert in list Sensor of Co with same id
     *  */
    @Test
    public void testAppendCoInListWithTheSameID() throws Exception {

        scheduler.appendCoInList("0","Conveyer","humidity","20","%");
        assertNotEquals(MyService.listConnectedObject.size(),3);                     //Verify that the new Co is not insert in the list CO
        assertEquals(MyService.listConnectedObject.size(),2);                        //Verify that the Co number is always 2
        assertEquals(MyService.listConnectedObject.get(0).sensorList.size(),2);      //Verify that sensor is add at the list Sensor of Oc with ID "0"
    }


    /**
     * @fn testAppendCoInListToInsertSensor
     * @brief Verify the insertion of new Sensor in a Co
     * result :
     * If the sensor name is not use this sensor is insert
     * but if the sensor name is ust this sensor is not insert
     * */
    @Test
    public void testAppendCoInListToInsertSensor() throws Exception {
        scheduler.appendCoInList("0","Motor","humidity","20","%");
        assertEquals(MyService.listConnectedObject.get(0).sensorList.size(),2);  //Verify that sensor is add at the list Sensor of Motor
        scheduler.appendCoInList("0","Motor","humidity","20","%");
        assertEquals(MyService.listConnectedObject.get(0).sensorList.size(),2);  //Verify that sensor is not add at the list Sensor of Motor
        //Because already use
    }




    /**
     * @fn testNotifyThresholdExceeded
     * @brief Verify that global variable are change with the Co name Co id sensor name
     * and the Sensor position in list sensor of this CO
     * the coId and sensor name must be correct to launch notification
     * result :
     * lanchNotification== true;
     * notificationCoName== Co name ;
     * notificationCoId== Co id;
     * notificationSensorName=sensor name;
     * notificationSensorPosition = sensor Position in listsensor
     *
     * */
    @Test
    public void testNotifyThresholdExceeded() throws Exception {
        scheduler.notifyThresholdExceeded("0","temperature");

        //Verify that launch that the notification can be launch
        assertEquals(MyService.launchNotification,true);
        //Verify the parameter of notification
        assertEquals(MyService.notificationCoId,0);
        assertEquals(MyService.notificationCoName,"Motor");
        assertEquals(MyService.notificationSensorName,"temperature");
        assertEquals(MyService.notificationCoId,0);

    }

    /**
     * @fn testNotifyCoConnection
     * @brief Verify that
     * */
    @Test
    public void testNotifyCoConnection() throws Exception {
        scheduler.notifyCoConnection("0","1");
        assertEquals(MyService.listConnectedObject.get(0).getConnect(),true);
        scheduler.notifyCoConnection("0","0");
        assertEquals(MyService.listConnectedObject.get(0).getConnect(),false);

    }

    @Test
    public void testSetAllDataOfOneSensor() throws Exception {
        scheduler.setLastMeasuresOfAllCos("0","temperature","30","1","25/06/05","15:35:55");
        assertEquals(MyService.listConnectedObject.get(0).sensorList.get(0).listData.get(0).getValue(),30);
        assertEquals(MyService.listConnectedObject.get(0).sensorList.get(0).listData.get(0).getDate(),"25/06/05 15:35:55");
        scheduler.setLastMeasuresOfAllCos("0","temperature","30","1","25/06/05","15:35:55");
        assertNotEquals(MyService.listConnectedObject.get(0).sensorList.get(0).listData.get(1).getValue(),30);
    }

    @Test
    public void testSetLastMeasuresOfAllCos() throws Exception {
        scheduler.setLastMeasuresOfAllCos("0","temperature","25","1","25/02/05","15:25:55");
        assertEquals(MyService.listConnectedObject.get(0).sensorList.get(0).listData.get(0).getValue(),25);
        assertEquals(MyService.listConnectedObject.get(0).sensorList.get(0).listData.get(0).getDate(),"25/02/05 15:25:55");
    }


    @After
    public void tearDown() throws Exception {

    }



}