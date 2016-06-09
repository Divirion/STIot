/**
 * *
 *  * @file      ItemParamAdapter.java
 *  * @author    Lucas B
 *  * @version   1.0
 *  * @date      19 May 2016
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

package com.example.prose.stiot.ItemParameterActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prose.stiot.R;

import java.util.ArrayList;

/**
 * Created by Lucas B on 19/05/16.
 */
public class ItemParamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ItemParam> boardCoParam;                                  /**< List of ItemParam to integer in Adapter */
    public static ArrayList<EditText> listEdtTxt = new ArrayList<>();           /**< List of Sensor's EditText to save values */


    public ItemParamAdapter(ArrayList<ItemParam> listParam) {
        boardCoParam = listParam;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.linecosettings, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder bvh = (ItemViewHolder) holder;

        bvh.coName.setText(boardCoParam.get(position).getCoName());
        bvh.unit.setText(boardCoParam.get(position).getUnit());
        bvh.value.setText(boardCoParam.get(position).getValue());
        bvh.sensorName.setText(boardCoParam.get(position).getSensorName());

        // Add of sensor's EditText in the list
        this.listEdtTxt.add(((ItemViewHolder) holder).getValue());

    }


    @Override
    public int getItemCount() {
        return boardCoParam.size();
    }

    // Class ItemViewHolder used by the recyclerVew of ParameterActivity
    private class ItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView sensorName;
        protected EditText value;
        protected TextView coName;
        protected TextView unit;

        public ItemViewHolder(View v) {
            super(v);

            coName = (TextView) v.findViewById(R.id.coName);
            unit = (TextView) v.findViewById(R.id.unitParam);
            value = (EditText) v.findViewById(R.id.edt);
            sensorName = (TextView) v.findViewById(R.id.sensorName);

        }
        public EditText getValue(){ return this.value; }
    }

}

