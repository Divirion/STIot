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

import static org.easymock.EasyMock.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.easymock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class ConnectedObjectManagerTest{

    private ConnectedObjectManager connectedObjectManager;
    private Postman mock;

    @Before
    public void setUp() throws Exception {
        mock = EasyMock.createMock(Postman.class);
        connectedObjectManager = new ConnectedObjectManager(mock);

    }
/*

*/
    @Test
    public void testmodifyThresholds(){
        //String expectedResult = "1 40 temperature";
       /* expect */ mock.writeMessage("0 1 40 temperature");
        EasyMock.expectLastCall();
        EasyMock.replay(mock);
        connectedObjectManager.modifyThresholds(1,40,"temperature");
        EasyMock.verify(mock);


    }

    @Test
    public void testaskCoList(){
        /* expect */ mock.writeMessage("2");
        EasyMock.expectLastCall();
        EasyMock.replay(mock);
        connectedObjectManager.askCoList();
        EasyMock.verify(mock);
    }
/*
    @Test
    public void testsetTimingScan(){
         expect  mock.writeMessage("1 30");
        EasyMock.expectLastCall();
        EasyMock.replay(mock);

        connectedObjectManager.setTimingScan(30);
        EasyMock.verify(mock);
    }*/

}


/*typedef enum {
        NOTIFY_THRESHOLDS=0,
        SET_TIMING_SCAN,
        ASK_CO_LIST,
        ASK_ALL_DATA_OF_ONE_CO,
        ASK_LAST_MEASURES_OF_ALL_COS,
        NB_FCT_USED_FROM_SGA_TO_SGI
        }FctUsedFromSgaToSgi;

*/