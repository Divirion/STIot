/**
 * @author David COUET
 * @version 0.0
 * @file DetailActivity
 * @date 14/05/16
 * @brief
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
package com.example.prose.stiot.Network.Proxy;

import com.example.prose.stiot.Network.Postman;
//import com.example.prose.stiot.oc.TimeSpan;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;


public class DataManagerTest {

    private DataManager dataManager;
    private Postman mock;


    @Before
    public void setUp() throws Exception {
        mock = EasyMock.createMock(Postman.class);
       // mock = new Postman();
        dataManager = new DataManager(mock);

    }
    @Test
    public void testAskAllDataOfOneCo() throws Exception {

        DateFormat toDay = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String actualDate = toDay.format(new Date());
        /* expect */ mock.writeMessage("3 1 "+actualDate+" 2012/03/23 23:43:00");
        EasyMock.expectLastCall();
        EasyMock.replay(mock);
        dataManager.askAllDataOfOneCo(1,"2012/03/23","23:43");
        EasyMock.verify(mock);
    }

    @Test
    public void testAskLastMeasuresOfAllCos() throws Exception {
        /*expect*/ mock.writeMessage("4");
        EasyMock.expectLastCall();
        EasyMock.replay(mock);
        dataManager.askLastMeasuresOfAllCos();
        EasyMock.verify(mock);


    }
}
/*
public enum FctUsedFromSgaToSgi {
    NOTIFY_THRESHOLDS,
    SET_TIMING_SCAN,
    ASK_CO_LIST,
    ASK_ALL_DATA_OF_ONE_CO,
    ASK_LAST_MEASURES_OF_ALL_COS,
    NB_FCT_USED_FROM_SGA_TO_SGI
}*/