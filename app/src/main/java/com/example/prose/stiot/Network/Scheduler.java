package com.example.prose.stiot.Network;

import android.util.Log;

import com.example.prose.stiot.ItemDetailOcActivity.ItemSensorAdapter;
import com.example.prose.stiot.Model.ConnectedObject;
import com.example.prose.stiot.MyService;

/**
 * @author Arnaud B
 * @version 1.0
 * @file Scheduler
 * @projet com.example.prose.stiot.Network
 * @date 12/05/16
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

/**
 * @fn Scheduler
 * @brief This class decrypts the message received by the dispatcher
 *
 * */
public class Scheduler
{

    public Scheduler() {
    }

    /**
     * @fn  appendCoInList
     * @brief this method  inserts a New ConnectedObject in the ConnectedObject list
     * @param coId the Id of the new Id use to instant a new ConnectedObject
     * @param coName the ConnectedObject name of the new ConnectedObject use to instant a new ConnectedObject
     * @param nameSensor the Sensor name of the new measure use to instant a new measure in the ConnectedObject with id -> coId
     * @param thresholdValueForThisSensor the critical value of the new measure use to instant a new measure in the ConnectedObject with id -> coId
     * @param unit the unit measure of the new measure use to instant a new measure in the ConnectedObject with id ->coId
     * */
    public void appendCoInList(String coId,String coName, String nameSensor, String thresholdValueForThisSensor, String unit ){
        int indexCoBoard=searchPositionInArrayOfCoWithCoId(coId);
        int i=0;
        if(indexCoBoard==-1){                   //If searchPositionInArrayOfCoWithCoId don't find the co
            insertNewCoInListCo(coId,coName);
//            Log.e("Protocol appendCo","Oc insert in the listConnectedObject");
        }
        indexCoBoard=searchPositionInArrayOfCoWithCoId(coId);

        if(indexCoBoard!=-1){
            MyService.coPosition=indexCoBoard;
            insertSensorInCo(nameSensor,unit,thresholdValueForThisSensor,indexCoBoard);

        }
    }


    /**
     * @fn notifyThresholdExceeded
     * @brief inform service to launch a notification
     *
     * @param coId Co concern by the notification
     * @param sensorName Sensor of Co concern by the notification
     * */
    public  void notifyThresholdExceeded(String coId,String sensorName){
        int indexCoBoard=searchPositionInArrayOfCoWithCoId(coId);
        int i=0;
        if(indexCoBoard!=-1) {      //If Co exist
            do {
                //If sensor of ConnectedObject is find */
                if (compareNameSensor(sensorName, indexCoBoard, i)) {
                    MyService.listConnectedObject.get(indexCoBoard).sensorList.get(i).setThresholdState("1");

                    insertInfoNotification(coId,MyService.listConnectedObject.get(indexCoBoard).getName(),sensorName,i);
                    //Log.d("Notify Threashol", " Event threashold");
                }
                i++;
            } while (i < MyService.listConnectedObject.get(indexCoBoard).sensorList.size());
        }

    }

    /**
     * @fn notifyCoConnection
     * @brief this method set the state of one ConnectedObject
     * if the CO  it's connect or not connect at the Gateway     *
     * @param coId     the Id of ConnectedObject concern it allow to find the ConnectedObject among other
     * @param connectionState  State of ConnectedObject it TODO
     * */
    public void notifyCoConnection(String coId,String connectionState){
        int indexCoBoard=searchPositionInArrayOfCoWithCoId( coId);
        if(indexCoBoard!=-1){ /** If the the ConnectedObject is find this ConnectedObject is insert the state connection */
            insertStateCoConnection(connectionState,indexCoBoard);
        }
    }

    /**
     *@fn setAllDataOfOneCo
     *@brief this method insert a data with this date in the measure
     *if they are 1000 data in this measure this method is call *1000
     *@param coId  the Id of ConnectedObject concern it allow to find the ConnectedObject among other
     *@param sensorName  the sensor name of measure it allow to find the measure among other
     *@param measure     the value of data to insert
     *@param thresholdStateForThisSensor      state of threshold
     *@param date       the date of the data to insert
     **/
    //Envoye autant de fois que necessaire. Cad envoye pour chaque mesure de chaque sensor d'un co.
    public void setAllDataOfOneCo(String coId,String sensorName,String measure,String thresholdStateForThisSensor,String date,String time) {   //coId sensorName oneMeasureForThisSensor oneThresholdStateForThisSensor oneDateForThisMeasure
        int indexCoBoard=searchPositionInArrayOfCoWithCoId(coId);
        int i=0;
        date+=" "+time;
        if(indexCoBoard!=-1) {//indexCoBoard = -1 CoId don't find in the List ConnectedObject
            do {                //If in the ConnectedObject the method find the measure
                if (compareNameSensor(sensorName,indexCoBoard,i)) {
                    if (!dateIsTheSame(date, indexCoBoard, i)) { //If the measure is not insert
                        insertMeasureInMeasureList(date, measure, indexCoBoard, i);
                        MyService.listConnectedObject.get(indexCoBoard).sensorList.get(i).setThresholdState(thresholdStateForThisSensor);
//                        Log.d("Set All data", coId + sensorName + "NewDAta->" + measure + date);
                        MyService.valuePosition = i;
                        MyService.coPosition = indexCoBoard;
                        MyService.valeur = Integer.parseInt(measure);
                        MyService.updateView(indexCoBoard,Integer.parseInt(measure),i);
                        MyService.updateFlag = true;


                    }
                }
                i++;

            } while (i < MyService.listConnectedObject.get(indexCoBoard).sensorList.size());
        }
    }

    /**
     *@fn setLastMeasuresOfAllCos
     *@brief this method insert a data with this date in the measure
     *if they are 3 ConnectedObject and in each CO they are 3 measure method is call 3*3=9
     *@param coId  the Id of ConnectedObject concern it allow to find the ConnectedObject among other
     *@param sensorName  the sensor name of measure it allow to find the measure among other
     *@param measure     the value of data to insert
     *@param thresholdStateForThisSensor      state of threshold
     *@param date       the date of the data to insert
     **/
    //Envoye autant de fois que necessaire. Cad envoye pour chaque mesure de chaque sensor de chaque co.
    public void setLastMeasuresOfAllCos(String coId ,String sensorName,String measure,String thresholdStateForThisSensor,String date,String time) { //coId sensorName measureForThisSensor thresholdStateForThisSensor dateForThisMeasure
        int indexCoBoard=searchPositionInArrayOfCoWithCoId(coId);
        int i=0;
        date+=" "+time;
        if(indexCoBoard!=-1) { //If the ConnectedObject is in listConnectedObject
            do {
                if (compareNameSensor(sensorName,indexCoBoard,i) ) { //If in the ConnectedObject find the measure
                    if(!dateIsTheSame(date, indexCoBoard, i)) {  // it insert the new data */
                        insertMeasureInMeasureList(date, measure, indexCoBoard, i);
                        MyService.listConnectedObject.get(indexCoBoard).sensorList.get(i).setThresholdState(thresholdStateForThisSensor);
//                          Log.d("Set lase data", coId + sensorName + "NewDAta->" + measure + date);
                        Log.e("values", "valPosition : "+ i + "coPosion : "+indexCoBoard);
                        Log.e("values", "valeur : "+ Integer.parseInt(measure));

                      /*  MyService.valuePosition = i;
                        MyService.coPosition = indexCoBoard;
                        MyService.valeur = Integer.parseInt(measure);*/
                        MyService.updateView(indexCoBoard,Integer.parseInt(measure),i);
                    }
                }
                i++;
            } while (i < MyService.listConnectedObject.get(indexCoBoard).sensorList.size());
        }

    }

    /**
     * @fn searchPositionInArrayOfCoWithCoId
     * @brief Allow to know the position in the Array of ConnectedObject  one ConnectedObject with this ID
     * @param coId Id of ConnectedObject  to search in the Array
     * @return the position in the Array of ConnectedObject  if it return -1 the ConnectedObject was not found
     * */
    private int searchPositionInArrayOfCoWithCoId(String coId){
        int position=-1;
        int i=0;
        if (!MyService.listConnectedObject.isEmpty()) {
            do {
                try {
                    if (MyService.listConnectedObject.get(i).getId() == Integer.parseInt(coId)) {
                        position = i;
                        i = MyService.listConnectedObject.size();
                    } else {
                        i++;
                    }
                }catch(IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }while (i < MyService.listConnectedObject.size()) ;
        }
//       Log.d("PositionInArrayOfCo",coId+" Position in array ConnectedObject is -> "+position);
        return position;
    }


    /**
     * @fn insertStateCoConnection
     * @brief set the state connection of One Co
     * @param indexCoBoard the position of Co in list Co
     * @param connectionState the state of Co
     * */
    private void insertStateCoConnection(String connectionState, int indexCoBoard) {
        if (connectionState.equals("0")) {
            MyService.listConnectedObject.get(indexCoBoard).setConnect(false);
//        Log.d("NotifyCoConnection",coId+"Stat"+ connectionState);
        }else{
            MyService.listConnectedObject.get(indexCoBoard).setConnect(true);
        }

    }

    /**
     * @fn compareNameSensor
     * @brief this method compare a sensor name with
     * one sensor name of one Co
     *
     * @param sensorName   Sensor to compare with all sensor name of one Co
     * @param indexCoBoard Co concern
     * @param i position of sensor in the Co
     *
     * @return true if the sensor already exists else false
     *
     * */
    private Boolean compareNameSensor(String sensorName, int indexCoBoard , int i){
        return MyService.listConnectedObject.get(indexCoBoard).sensorList.get(i).getName().equals(sensorName);
    }

    /**
     * @fn insertMeasureInMeasureList
     * @brief Insert a measure in measure list of one sensor
     *
     * @param date date of measure to insert
     * @param measure measure to insert
     * @param indexCoBoard  Co concern by the measure
     * @param i  sensor of Co concern by the measure
     * */
    private  void insertMeasureInMeasureList(String date,String measure,int indexCoBoard , int i){
        int measureToInt=Integer.parseInt(measure);
        MyService.listConnectedObject.get(indexCoBoard).sensorList.get(i).insertNewData(date, measureToInt);
    }

    private void insertSensorInCo(String nameSensor, String unit, String thresholdValueForThisSensor, int indexCoBoard) {
        int i=0;
        boolean sensorAlreadyUses =false;
        try{
            do{
                if(compareNameSensor(nameSensor,indexCoBoard,i)){
                    sensorAlreadyUses=true;
//                        Log.e("InsertSensorInCo","Sensor ALREADY use");
                }
                i++;
            }while(i<MyService.listConnectedObject.get(indexCoBoard).sensorList.size());
        }catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if(!sensorAlreadyUses) {
            MyService.listConnectedObject.get(indexCoBoard).insertSensor(nameSensor, unit, Float.parseFloat(thresholdValueForThisSensor));
            MyService.valuePosition=i;

//            Log.d("InsertSensorInCo","Oc insert a Sensor  valuePos "+MyService.valuePosition);
        }
    }


    /**
     * @fn insertNewCoInListCo
     * @brief this method insert a new Co in list Co
     * it compare id Co if the Co id is Already use
     * the new Co isn't insert.
     * @param coId   co Id of new Co to insert
     * @param coName co name of new Co to insert
     *
     * */
    private void insertNewCoInListCo(String coId, String coName) {
        int i=0;
        Boolean idAlreadyPresence=false;
        do{
            try{
                if(MyService.listConnectedObject.get(i).getId()==Integer.parseInt(coId)){
                    idAlreadyPresence=true;
                    Log.e("Protocol insertNewOc","ConnectedObject already presence");
                }
                i++;
            }catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }while(i< MyService.listConnectedObject.size());
        if(idAlreadyPresence==false){
            try{
                //Insert the co at listConnectedObject*/
                MyService.listConnectedObject.add(new ConnectedObject(coName, Integer.parseInt(coId)));
            }catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * @fn dateIsTheSame
     * @brief Compare a date with the last date of sensor
     * Co and sensor name must be verify before call this method
     *
     * @param date  date to compare with the last date
     * @param indexCoBoard co of sensor to compare
     * @param i sensor to compare with date
     *
     * @return true if the date is the same
     *
     * */
    private Boolean dateIsTheSame(String date, int indexCoBoard, int i){
        return MyService.listConnectedObject.get(indexCoBoard).sensorList.get(i).getData().get(0).getDate().equals(date);
    }

    /**
     * @fn insertInfoNotification
     * @brief insert information to the notification to launch
     * the coId and sensorName must be verify before to use this method
     *
     * @param coId    Use to create the notification ID
     * @param coName  inform the user that Co is concern by the notification
     * @param sensorName inform the user that Sensor is concern by the notification
     * @param sensorPosition  Use to create the notification ID
     *
     * @*/
    private void insertInfoNotification(String coId,String coName,String sensorName,int sensorPosition){
        MyService.notificationCoId=Integer.parseInt(coId);
        MyService.notificationCoName=coName;
        MyService.notificationSensorName=sensorName;
        MyService.notificationSensorPosition=sensorPosition;
        MyService.launchNotification=true;
    }



}
