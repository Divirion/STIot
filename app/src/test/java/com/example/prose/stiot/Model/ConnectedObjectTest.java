package com.example.prose.stiot.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Arnaud B
 * @version 1.0
 * @file $module.name
 * @projet com.example.prose.stiot.ConnectedObject
 * @date 18/05/16
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
public class ConnectedObjectTest {

    public ConnectedObject connectedObject;
    public ArrayList<Sensor> sensorList;

    @Before
    public void setUp() throws Exception {
        String coName="Conveyer";
        int coId =0;
        connectedObject = new ConnectedObject(coName, coId);
        sensorList =new ArrayList<>();

        Sensor sensor1= new Sensor("Temperature","C",20);
        sensorList.add(0,sensor1);
        Sensor sensor2= new Sensor("Humidity","%",20);
        sensorList.add(1,sensor2);
        Sensor sensor3= new Sensor("speed","m/s",20);
        sensorList.add(2,sensor3);

      }

    @Test
    public void testInsertSensor() throws Exception {

        connectedObject.insertSensor("Temperature","C",20);
        assertEquals("Temperature", connectedObject.getSensorList().get(0).getName());
        assertEquals("C", connectedObject.getSensorList().get(0).getUnit());
        assertEquals(20, connectedObject.getSensorList().get(0).getThresholdValue(),0.001);
    }

    @Test
    public void testInsertThreeSensor() throws Exception {

        connectedObject.insertSensor("Temperature","C",20);
        connectedObject.insertSensor("Humidity","%",20);
        connectedObject.insertSensor("speed","m/s",20);

        assertEquals(connectedObject.sensorList.get(0).getName(),sensorList.get(0).getName());
        assertEquals(connectedObject.sensorList.get(1).getName(),sensorList.get(1).getName());
        assertEquals(connectedObject.sensorList.get(2).getName(),sensorList.get(2).getName());

    }



}