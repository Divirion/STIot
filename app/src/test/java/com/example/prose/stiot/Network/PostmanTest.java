/*
 * *
 *  * @file      PostmanTest.java
 *  * @author    Arnaud B
 *  * @version   1.0
 *  * @date      19 April 2016
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

package com.example.prose.stiot.Network;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Created by billy on 02/05/16.
 */
public class PostmanTest {

    private Postman postman;

    private Decoder mock;

    private AsyncTask asyncmock;

    @Before

    public void setUp() throws Exception {
        mock = EasyMock.createMock(Decoder.class);

    }



    @Test
    public void testWriteMessage(){
        postman  = new Postman();
        postman.writeMessage("Test");

    }

    @Test
    public void testReceiveMessage() throws Exception {
        /* expect */ mock.newMessage("0 1 40 temperature");
        EasyMock.expectLastCall();
        EasyMock.replay(mock);
        postman.receiveMessage();

    }
}