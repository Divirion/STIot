/**
 * *
 *  * @file      MyService.java
 *  * @author    Lucas B
 *  * @version   1.0
 *  * @date      06 may 2016
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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.prose.stiot.ItemDetailOcActivity.ItemSensorAdapter;
import com.example.prose.stiot.Model.Sensor;
import com.example.prose.stiot.Network.ApplicationConnection;
import com.example.prose.stiot.Model.ConnectedObject;

import java.util.ArrayList;


/**
 * Created by Lucas B on 06/05/16.
 */

public class MyService extends Service {

    public static ArrayList<ConnectedObject> listConnectedObject;        /**< List of Connected Objects add at the initialization  */
    public static ArrayList<Sensor> mSensorList;                         /**< List of sensors */

    private ApplicationConnection applicationConnection;                 /**< Declaration of the ApplicationConnection */
    public static DetailActivity details;                            /**< Declaration of the DetailActivity */
    static HomeActivity home;                                                 /**< Declaration of the HomeActivity */
    static ParameterActivity pa;                                         /**< Declaration of the ParameterActivity */

    public static int valeur = 0;                                                           /**< value sent to the views */
    public static int coPosition = 0;                                                       /**< Id of the Co sent which the sensor values are sent to the views */
    public static int valuePosition = 0;                                                    /**< Id of the sensor in the Co which the value is sent to the views */
    public static int coId=0;

    private final IBinder mBinder = new MonBinder();                                        /**< Binder Declaration */

    public static boolean newParameters, newTiming, newDate;                                         /**< Booleans to say at the SendToSgi Thread that they are new parameters or timing */
    public static int idCo, value, frequency;                                               /**< Id of the Co which have a new threshold, the value oh this threshold and the new frequency */
    public static String sensorName;                                                        /**< Name of the sensor which have a new threshold  */

     static String mServiceHour ="and Time";                                                              /**< Text of the DetailActivity */
     static String mServiceDate = "Date";                                                             /**< Text of the DetailActivity */


    private static final int REQUEST_CODE = 1;                                              /**<Use to create a notification  */
    public static boolean flagDisableActualizing=false,updateFlag=false;
    public  static boolean launchNotification=false;                                        /**<allows you to know to launch a notification  */
    public static String notificationCoName="-1";                                           /**<Name of Co concern by the notification  */
    public  static int notificationCoId=-1;                                                 /**< Allows to create the ID notification */
    public static String notificationSensorName="-1";                                       /**<Name of sensor concern by the notification   */
    public  static int notificationSensorPosition=-1;                                       /**<Allows to create the ID notification  */

    ConnectedObject connectedObject;
    // Class MonBinder
    public class MonBinder extends Binder {
        public MyService getService() {
            //Log.e("MyService","Je suis dans le onCreate du service !!! ");
            return MyService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        // Implements :
        listConnectedObject = new ArrayList<>();
        mSensorList = new ArrayList<>();
        applicationConnection = new ApplicationConnection();
        details = new DetailActivity();
        home = new HomeActivity();
        pa = new ParameterActivity();

        newParameters = false;
        newTiming = false;

        applicationConnection.connect();
        Log.e("MyService", " onCreate du service !!! ");
        //sendToView.start();
        sendToSgi.start();
        notifyThreshold.start();


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MyService", "Je suis dans le onStartCommand du service !!! ");
        Log.e("MyService", "flags->"+flags+"startId->"+startId);

        return Service.START_NOT_STICKY;
    }

    /**
     * @fn sendToView
     * @brief Thread which send the new data from OCs to the views in the good OC and at the good position
     * (use functions into HomeActivity and ParameterActivity)
     */
    /**
    Thread sendToView = new Thread(new Runnable() {
        public void run() {
            try {
                do {
                    //Thread.sleep(1);
                    Log.e("MyService", "dans le thread");

                    Message message = home.handlerHome.obtainMessage(coPosition, valeur, valuePosition);
                    Log.e("MyService", "dans le thread" + "CoPosition : " +coPosition+"Valeur : " + valeur+"ValuePosition : " + valuePosition);


                    Message message2 = pa.handlerParam.obtainMessage(coPosition, 3, valuePosition);
                    pa.handlerParam.sendMessage(message2);
                    // }
                } while (true);

           } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
*/



    /**
     * @fn sendLastValue
     * @brief Thread which send asks and orders to Sgi
     * It ask of new Data from the Service to the proxy DataManager
     * It send the new parameters to Sgi
     * It send too the TimingScan (time scale) to Sgi
     */
    Thread sendToSgi = new Thread(new Runnable() {      //Créer une valeur seuil dans Sensor si elle n'existe pas !!!
        public void run() {
          //  if(applicationConnection.checkConnection()){
                applicationConnection.connectedObjectManager.askCoList();
                Log.e("MyService", "Envoie demande last value");
          //  }
            try {
                do {
                    Thread.sleep(1000);            //Envoie de la demande
                    Log.e("MyService", "dans le thread");
                    if(!flagDisableActualizing) {
                        applicationConnection.dataManager.askLastMeasuresOfAllCos();
                        Log.e("MyService", "AskLastMeasuresOfAllCos OK");
                    }

                    if(newParameters == true) {
                        applicationConnection.connectedObjectManager.modifyThresholds(idCo, value, sensorName);
                        newParameters = false;
                        Log.e("sendThread :::"," Envoi Parameters !");
                    }

                    if(newTiming == true) {
                        applicationConnection.connectedObjectManager.setTimingScan(frequency);
                        newTiming = false;
                        Log.e("sendThread :::"," Envoi Frequency !");
                    }
                    if(newDate){
                        applicationConnection.dataManager.askAllDataOfOneCo(coId,mServiceDate,mServiceHour);
                        Log.e("threadAskAllData", "Coid : " +coId+ " Date : "+ mServiceDate + "Hour : "+ mServiceHour);
                        newDate= false;

                    }
                    /*if(updateFlag) {
                        int size = ItemSensorAdapter.rvDatasList.size();
                        for (int counter = 0; counter < size; counter++) {
                            ItemSensorAdapter.rvDatasList.get(counter).getAdapter().notifyDataSetChanged();
                        }
                        updateFlag=false;
                    }*/

                }while(true);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public static void updateView(int coPosition,int valeur,int valuePosition) {
        Log.e("MyService", "dans le thread");
        Message message = home.handlerHome.obtainMessage(coPosition, valeur, valuePosition);
        Log.e("MyService", "dans le thread" + "CoPosition : " + coPosition + "Valeur : " + valeur + "ValuePosition : " + valuePosition);
        home.handlerHome.sendMessage(message);

        Message message2 = pa.handlerParam.obtainMessage(coPosition, 3, valuePosition);
        pa.handlerParam.sendMessage(message2);

    }





    /**
     * @fn notifyThreshold
     * @brief Thread which send the ask of new Data from the Service to the proxy DataManager
     */
    Thread notifyThreshold = new Thread(new Runnable() {
        public void run() {
            try {
                do {
                    Thread.sleep(1000);
                    if(launchNotification) {
                        createNotification(notificationCoId,notificationCoName,notificationSensorName,notificationSensorPosition);
                        launchNotification=false;
                    }

                } while (true);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });


    public Handler handlerFromDetail = new Handler(){
        @Override
        public void handleMessage(Message msg){
            coId=msg.arg1;
            String[] splitArray = null;
            String str =(String) msg.obj;
            splitArray=str.split("@");
            mServiceDate = splitArray[0];
            mServiceHour = splitArray[1];

            newDate = true;


            Log.e("Quelle erreur", coId+mServiceDate+mServiceHour);
        }
    };

    /**
     * @fn handlerService
     * @brief Messages received from the Scan new Parameters thread
     * @param msg The message received from ParameterActivity
     */
    public Handler handlerService= new Handler(){

        @Override
        public void handleMessage(Message msg) {

            if(msg.what == -1){
                Log.e("handlerMessage  :   ", " frequency :  "+msg.arg2);
                frequency = msg.arg2;
                newTiming = true;
            }else {
                Log.e("handlerMessage  :   ", " idCo :   " + msg.what + "     idSensor :   " + msg.arg1 + "     Value :   " + msg.arg2);
                sendValueToSgi(msg.what, msg.arg2, listConnectedObject.get(msg.what).getSensorList().get(msg.arg1).getName());
            }
        }
    };


    /**
     * @fn sendValueToSgi
     * @brief Initialization of the values which will be sent to Sgi by the sendThread
     * @param iDCo the Id of the Co concerned by the new Parameters.
     * @param vaLue Value of threshold which will be sent to Sgi by the sendThread.
     * @param sensorNaMe the name of the sensor concerned by the new Parameters.
     */
    private void sendValueToSgi(int iDCo, int vaLue, String sensorNaMe){

        Log.e("EnvoiValeursToSgi : ", "idCo :  " + idCo + "    value :  " + value + "    sensorName :  " + sensorName);

        idCo = iDCo;
        value = vaLue;
        sensorName = sensorNaMe;

        newParameters = true;
    }

   ///////////////////////////////////////////////////////////////////////////////

    /**
     * @fn  createNotification
     * @brief this method launch a notification to user,
     * it informs the user when a Threshold Exceeded
     * @param coId   allow to make ID unique to each notification
     * @param coName  Inform the user what co is concern by the notification
     * @param sensorName Inform the user what sensor is concern by the notification
     * @param sensorPosition allow to make ID unique to each notification
     */
    private void createNotification(int coId,String coName,String sensorName,int sensorPosition) {
        int notificationId;

        notificationId = createNotificationId(coId,sensorPosition);
        NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent launchNotificationIntent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, launchNotificationIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setVibrate(new long[] { 1000, 1000,1000,1000})
                .setLights(Color.BLUE,500,500)
                .setTicker("WARNING")
                .setSmallIcon(R.drawable.stlogo)
                .setContentTitle(coName)
                . setAutoCancel(true)
                .setContentText(sensorName + " Threshold Exceeded")
                //.setSound()
                .setContentIntent(pendingIntent);
        mNotification.notify(notificationId, builder.build());
    }
    /**
     * @fn  createNotificationId
     * @brief create a unique ID to each sensor
     * each ID is compose to coId(int)follow sensor Position in the list sensor concern(int)
     *
     * @return  unique ID
     * */
    private int createNotificationId(int coId, int sensorPosition){
        String notificationId= Integer.toString(coId);
        notificationId+=Integer.toString(sensorPosition);
        return Integer.valueOf(notificationId);
    }
}
