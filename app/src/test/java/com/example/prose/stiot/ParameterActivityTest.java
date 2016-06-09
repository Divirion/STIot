/*
 * *
 *  * @file      ParameterActivityTest.java
 *  * @author    Lucas B
 *  * @version   1.0
 *  * @date      22 May 2016
 *  * @brief     Contents of ItemOC
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


package com.example.prose.stiot;

import com.example.prose.stiot.Model.ConnectedObject;
import com.example.prose.stiot.Model.Sensor;

import org.easymock.EasyMock;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by lucas-pc on 23/05/16.
 */
public class ParameterActivityTest {

    private int i = 0;
    private MyService mock;
    private ParameterActivity paramAct;

    @Before
    public void setUp() throws Exception {
        mock = EasyMock.createMock(MyService.class);
        paramAct = new ParameterActivity();

        for(i=0; i<3; i++) {
            mock.listConnectedObject.add(new ConnectedObject("ConnectedObject" + i, i));
            mock.listConnectedObject.get(i).getSensorList().add(new Sensor("sensor "+i, "unit "+i, 20));
            mock.listConnectedObject.get(i).getSensorList().add(new Sensor("sensor2 "+i, "unit2 "+i, 30));
        }
    }

    @Test
    public void testIncludeParamInView() throws Exception {

        paramAct.includeParamInView(10, 0, 0);
        paramAct.includeParamInView(20, 0, 1);
        paramAct.includeParamInView(30, 1, 1);

        assertEquals(3, paramAct.listItemParam.size());
        assertEquals(10, paramAct.listItemParam.get(0).getValue());
        assertEquals( "Sensor2 1", paramAct.listItemParam.get(2).getSensorName());
        assertEquals("Unit2 1", paramAct.listItemParam.get(2).getUnit());
        assertEquals(30, paramAct.listItemParam.get(2).getValue());
    }
}
