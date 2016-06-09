/*
 * *
 *  * @file      ApplicationConnectionTest.java
 *  * @author    Lucas B
 *  * @version   1.0
 *  * @date      21 May 2016
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


import com.example.prose.stiot.Network.ApplicationConnection;
import com.example.prose.stiot.Network.Postman;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class ApplicationConnectionTest {

    private Postman mock; // pas encore implémenté, nom prévu à modifier si changement.
    private ApplicationConnection appCo;

    @Before

    public void setUp() throws Exception {
        mock = EasyMock.createMock(Postman.class);
        appCo = new ApplicationConnection();
    }

    @Test
    public void testConnect(){
        // problème thread
    }

    @Test
    public void testDisconnect(){
        /* expect*/ mock.closeSocket();
        EasyMock.expectLastCall();
        EasyMock.replay(mock);
        appCo.disconnect();
        EasyMock.verify(mock);
    }

    @Test
    public void testCheckConnection(){
        /* expect*/ EasyMock.expect(mock.getMyAppliConnectionState());
        EasyMock.expectLastCall();
        EasyMock.replay(mock);
        appCo.checkConnection();
        EasyMock.verify(mock);
    }
}

