package com.example.prose.stiot.Model;

import org.junit.Before;
import org.junit.Test;

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
public class SensorTest {


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testInsertNewData() throws Exception {
        String nameSensor="Temperature";
        String unitSensor="C";
        float thresholdValue=20;

        final Sensor sensor = new Sensor(nameSensor,unitSensor,thresholdValue);
        sensor.insertNewData("25/06/2015",20);
        assertEquals(20,sensor.getData().get(0).getValue());

    }
}