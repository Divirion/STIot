
/*
 * *
 *  * @file      Postman.java
 *  * @author    Arnaud B
 *  * @version   1.0
 *  * @date      19 April 2016
 *  * @brief     Postman to communicate with the Gateway Iot
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

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by billy on 02/05/16.
 */
public class Postman {

    private static int PORT =1234;/**< The port on the target host to connect to */
    private String string;
    private String ipAdresse ="192.168.5.219";       /**< the target host IP address to connect to.*/
    private Socket socket;                          /**socket make the communication between the gateway and ANDROID */
    private Boolean myAppliConnectionState =false;  /** Indicator the connexion state  if the socket
     is connect =true else false */

    private BufferedReader br;
    static final int bufferSize =128;
    /**
     * @fn Postman
     *@brief Constructor of the Postman,
     * TODO
     * */
    public Postman() {
        this.myAppliConnectionState=false;
    }


    /**
     * @fn toConnect
     * @brief try to connect at the gateway
     *
     */
    public void toConnect(){
        setUp setUpConnexion = new setUp();
        setUpConnexion.execute();
        Log.d("Postman ToConnect","try to connec"+"Connect State"+myAppliConnectionState);
    }


    /**
     * @fn writeMessage
     * @brief Allow to send a message
     * this method is use by the proxy s
     * @param message The message to send to the serveur
     */

    public void writeMessage(String message){
        writeData writeData = new writeData();
        Log.e("Postman WriteMessage","Send Message" + message);
        writeData.execute(message);


    }

    /**
     * @fn receiveMessage
     * @brief return le contents of the inputstream of the socket,
     * the contents finish by \n to indicate the end of the message
     * @return return the contents of the socket in string
     * @throws IOException
     */

    public String receiveMessage() throws IOException {
        String str = new String();
        str=br.readLine();
        String[] splitArray = null;
        splitArray=str.split("&");
        Log.i("reception","This was received from Postman str"+str);
        try {
            Log.i("reception", "This was received from Postman splitArray" + splitArray[1]);

            return splitArray[1];
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("reception","splitarray[1] don't exist"+e);
        }
        return null;
    }

    /**
     * @fn WriteData
     * @brief the method is call only by send message with the message to send to Gateway.
     * If the connection fail the The variable myAppliConnectionState is changed to false
     */
    private class writeData extends AsyncTask <String, Void,Void >{
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(String... params) {
            PrintWriter out = null;

            if(socket!=null){
                if(socket.isConnected()){
                    try {
                        out = new PrintWriter(socket.getOutputStream());
                        out.println(params[0]);                      /**Write in the outputStream of buffer with the message send by
                         * sendMessage. */
                        out.flush();
                        Log.d("WriteData Portman","Send message->"+params[0]+"Connect State"+myAppliConnectionState);
                    } catch (IOException e) {
                        e.printStackTrace();
                        setMyAppliConnectionState(false);
                        Log.e("WriteData Portman","Echec Envoie du message->"+params[0]+"Connect State"+myAppliConnectionState);
                    }
                }
            }
            return null;
        }


        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param aVoid The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    /**
     * @fn setUp
     * @brief the method is call only by toConnect, it establishes a socket connection
     * to Gateway. The variable myAppliConnectionState is changed to true is the connection is good
     *
     * It's a AsynTask
     */
    private class setUp extends AsyncTask<Void, Void, Void> {
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */

        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket = new Socket(ipAdresse, PORT);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                setMyAppliConnectionState(true);
                Log.e("PortMand SetUp","connection establish"+"Connect State"+myAppliConnectionState);
            } catch (UnknownHostException e) {
                // e.printStackTrace();
                Log.e("PortMand SetUp","erreur de connexion UnknownHostException"+"Connect State"+myAppliConnectionState, e);
            }catch (ConnectException e){
                Log.e("PortMand SetUp","erreur Connect Exception socket"+"Connect State"+myAppliConnectionState,e);
                e.printStackTrace();
                setMyAppliConnectionState(false);
            }
            catch (IOException e) {
                Log.e("PortMand SetUp","erreur de connexion socket"+"Connect State"+myAppliConnectionState,e);
                // e.printStackTrace();
                setMyAppliConnectionState(false);
            }
            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param aVoid The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
//4 2 moteur temp 20 1 C
    //3 2 temp 30 1 DATE TEST

    /**
     * @fn closeSocket
     * @brief close the socket from application to Gateway
     * use if the user want change the
     * */
    public void closeSocket() {
        if (socket != null) {               /** If socket is create*/
            if (socket.isConnected()) {
                try {
                    socket.close();
                    setMyAppliConnectionState(false);
                }  catch (IOException e) {
                    e.printStackTrace();

                    Log.e("Postman Close Socket", "Error to close socket"+"Connect State"+myAppliConnectionState);
                }
            }
        }
    }


    public String getIpAdresse() {
        return ipAdresse;
    }

    public void setIp(String ipAdresse) {
        this.ipAdresse = ipAdresse;
    }


    public Boolean getMyAppliConnectionState() {
        return myAppliConnectionState;
    }

    public void setMyAppliConnectionState(Boolean myAppliConnectionState) {
        this.myAppliConnectionState = myAppliConnectionState;
    }
}