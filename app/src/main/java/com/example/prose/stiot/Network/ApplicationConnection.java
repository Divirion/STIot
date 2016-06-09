
/*
 * *
 *  * @file      ApplicationConnection.java
 *  * @author    Arnaud B
 *  * @version   1.0
 *  * @date      2 April 2016
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

import android.util.Log;
import com.example.prose.stiot.Network.Proxy.ConnectedObjectManager;
import com.example.prose.stiot.Network.Proxy.DataManager;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;


/**
 * @fn ApplicationConnection
 * @brief this class allow to connect or disconnect
 * to Gateway,  Send message and receive and decrypt messages
 */
public class ApplicationConnection extends Thread {
    public Postman postman ;                               /**  */
    public Decoder decoder;                         /** */
    public ConnectedObjectManager  connectedObjectManager; /** */
    public DataManager dataManager;                        /** */

    /**
     * @fn ApplicationConnection
     * @brief The constructor instantiate the postman
     * the Postman try to connect to the server
     * connectedObjectManager and dataManager are proxy.
     * They send  a message to Sgi with the postman
     * decoder receive a message and decrypt
     */
    public ApplicationConnection() {
//        Log.i("Constructor"," AppliConnection");
        postman =new Postman();
        decoder =new Decoder();
        connectedObjectManager=new ConnectedObjectManager(this.postman);
        dataManager=new DataManager(this.postman);
    }

    /**
     * @fn connect
     * @brief allow to connect the postman to Sgi by socket
     * it sleep is to initialize the communication     *
     * */
    public void connect() {
        this.postman.toConnect();
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (checkConnection()) {
            Log.e("Connect AppliConnection","lancement du dispatchor");
            this.start();
        }

    }

    /**
     * @fn disconnect
     * @brif Close the communication. It disconnect the socket
     * of postman if the socket is connected  *
     * */

    public void disconnect(){
        if(postman!=null) {              /*If the postman is create*/
            postman.closeSocket();              /*Close the socket*/
        }
    }

    public boolean checkConnection(){
        return postman.getMyAppliConnectionState();
    }




    /**
     * Calls the <code>run()</code> method of the Runnable object the receiver
     * holds. If no Runnable is set, does nothing.
     *
     * @see Thread#start
     */
    @Override
    public void run() {
        super.run();

        String receivedMessage;
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            String str;
            do {
                str = new String();
                str = this.postman.receiveMessage();
                Log.i("reception", "Decoder called with : " + str);
                if (str != null) {
                    this.decoder.newMessage(str);
                }
            } while (str != null);
            Log.i("reception", "After while ");
        } catch (IOException e) {
            // e.printStackTrace();
            Log.e("AppliConnection run", "Connection lost");
        }
        Log.i("AppliConnection", "fin de la boucle du thread");
        disconnect();
    }







}