package com.example.prose.stiot.Network;

//import com.example.prose.stiot.co.Model;
//import com.example.prose.stiot.oc.ConnectionState;
//import com.example.prose.stiot.oc.ThresholdState;

import org.junit.Before;
import org.junit.Test;
import org.easymock.*;

import static org.junit.Assert.*;

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

public class DecoderTest {

    private Decoder decoder;

        private Scheduler mock; // pas encore implémenté, nom prévu à modifier si changement.

        @Before

        public void setUp() throws Exception {
            mock = EasyMock.createMock( Scheduler.class);
            decoder  = new Decoder(mock);

        }

   // @Test
   // public void testDecodeSetConnectionState(){
    /* expect*/ // mock.setConnectionState(ConnectionState.CONNECTED);
   /*    EasyMock.expectLastCall();
        EasyMock.replay(mock);
        decoder.newMessage("5 CONNECTED");
        EasyMock.verify(mock);
    }*/


    @Test
    public void testDecodeNotifyThresholdExceeded(){
        /* expect*/ mock.notifyThresholdExceeded("1", "temperature");
        EasyMock.expectLastCall();
        EasyMock.replay(mock);
        decoder.newMessage("0 1 temperature");
        EasyMock.verify(mock);


    }
    @Test
    public void testDecodeNotifyCoConnection(){
        /* expect*/ mock.notifyCoConnection("1","1");
        EasyMock.expectLastCall();
        EasyMock.replay(mock);
        decoder.newMessage("1 1 1");
        EasyMock.verify(mock);

    }
    @Test
    public void testDecodeSetAllDataOfOneSensor(){
        /* expect*/ mock.setAllDataOfOneCo("1","temperature","10", "1","DATE","15h12");
        EasyMock.expectLastCall();
        EasyMock.replay(mock);
        decoder.newMessage("2 1 temperature 10 1 DATE 15h12");
        EasyMock.verify(mock);

    }
    @Test
    public void testDecodeSetLastMeasuresOfAllCos(){
        /* expect*/ mock.setLastMeasuresOfAllCos("1","temperature","20","1","20/03/05","15h25");
        EasyMock.expectLastCall();
        EasyMock.replay(mock);
        decoder.newMessage("3 1 temperature 20 1 20/03/05 15h25");
        EasyMock.verify(mock);

    }


}

/* typedef enum {
        NOTIFY_THRESHOLD_EXCEEDED,
        NOTIFY_CO_CONNECTION,
        SET_ALL_DATA_OF_ONE_SENSOR,
        SET_LAST_MEASURES_OF_ALL_COS,
        APPEND_CO_IN_LIST,
        NB_FCT_USED_FROM_SGI_TO_SGA
        }FctUsedFromSgiToSga;
*/