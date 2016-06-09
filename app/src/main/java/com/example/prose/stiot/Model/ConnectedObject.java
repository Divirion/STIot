/**
 * Created by billy on 26/04/16.
 * @file      ConnectedObject.java
 * @author    Arnaud B
 * @version   1.0
 * @date      26 April 2016
 * @brief     Contained a model OC
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

package com.example.prose.stiot.Model;

import android.util.Log;

import com.example.prose.stiot.ItemHomeActivity.ItemOcAdapterDetail;
import com.example.prose.stiot.ItemHomeActivity.ItemOcDetail;

import java.util.ArrayList;

public class ConnectedObject {

    public ArrayList<Sensor> sensorList; /**<List of Measure of this ConnectedObject */
    private String name;              /**<Name of this ConnectedObject*/
    private int id;                   /**<ID the ID is unique for each ConnectedObject define */
    private Boolean connect;          /**<Allow to know the state of connection of this ConnectedObject true the ConnectedObject is connect*/
    private int sensorNumber;
    private boolean inView ;
    private int positionInList;

    public ArrayList<ItemOcDetail> listItemOcDetail=new ArrayList<>();
    public ItemOcAdapterDetail adapterLastMeasure = new ItemOcAdapterDetail(this.listItemOcDetail);


    public ConnectedObject(String name, int id) {
        this.sensorList = new ArrayList<>();
        this.name = name;
        this.id = id;
        this.sensorNumber = 0;
        this.inView = false;
    }


    /**
     * @brief    Insert a New Measure in the ArrayList Mesure
     *           When ConnectedObject is declared. TODO
     * @param    name   name of measure
     * @param    unit   unit of measure
     */
    public void insertSensor(String name, String unit,float thresholdValue)
    {
        this.sensorList.add(sensorNumber,new Sensor(name,unit,thresholdValue));
//        Log.d("InsertSensorInCo","sensorNumber-> "+ sensorNumber);
        sensorNumber++;
    }


    public ArrayList<Sensor> getSensorList() {
        return sensorList;
    }

    public void setSensorList(ArrayList<Sensor> mesure) {
        this.sensorList = mesure;
    }

    public String getName() {
        return name;
    }

    public int getSensorNumber() {
        return sensorNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getConnect() {
        return connect;
    }

    public void setConnect(Boolean connect) {
        this.connect = connect;
    }

    public void setInView() {
        this.inView = true;
    }

    public Boolean getInView() {
        return inView;
    }

    public int getPosition(){return  positionInList;}

    void setPosition(int mPosition){
        this.positionInList = mPosition;
    }
}

