package com.example.prose.stiot.ItemDetailOcActivity;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prose.stiot.R;
import com.example.prose.stiot.Model.Sensor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David COUET
 * @version 0.0
 * @file ItemSensorAdapter
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


/**
 * @Class ItemSensorAdapter
 * @brief ItemSensorAdapter provides access to the datas for DetailActivity
 * and makes a view for each item.
 * */
public class ItemSensorAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static List<RecyclerView> rvDatasList = new ArrayList<>();

    private List<Sensor> mSensorsList;
    private Context context;
    private RecyclerView rvDatas;



public static class ViewHolder extends RecyclerView.ViewHolder {
    // Your holder should contain a member variable
    // for any view that will be set as you render a row

    protected TextView titre,threshold,min,max;
    protected RecyclerView mrvDatas;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected DividerItemDecoration divider;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ViewHolder(View itemView) {

        super(itemView);

        titre = (TextView) itemView.findViewById(R.id.tvTitle);
        threshold = (TextView) itemView.findViewById(R.id.tvThreshold);
        min = (TextView) itemView.findViewById(R.id.tvMin);
        max = (TextView) itemView.findViewById(R.id.tvMax);
        mrvDatas = (RecyclerView) itemView.findViewById(R.id.rvDatas);
        mLayoutManager = new LinearLayoutManager(itemView.getContext());
        divider = new DividerItemDecoration(itemView.getContext(), LinearLayoutManager.VERTICAL);

        Log.e("SensorAdapter", "Layout rvDatas");
    }
}


    public ItemSensorAdapter(List<Sensor> sensors,Context mContext){
        this.mSensorsList = sensors;
        this.context = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        ViewHolder viewHolder = new ViewHolder(inflater.inflate(R.layout.itemdetailcolist, parent,false ));

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position){
        Sensor sensor = mSensorsList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;



        String thresholdValue = "Threshold value : "+Float.toString(sensor.getThresholdValue());
        viewHolder.threshold.setText(thresholdValue);

//        String minValueToString = Float.toString(sensor.getMin().getValue());
        String minValueToString = "Min Value"; // temporaire
        viewHolder.min.setText(minValueToString);

//        String maxValueToString = Float.toString(sensor.getMax().getValue());
        String maxValueToString = "Max Value"; // temporaire
        viewHolder.max.setText(maxValueToString);

        /* Second recyclerView    */
        rvDatas = viewHolder.mrvDatas;
        ItemDataAdapter adapter = new ItemDataAdapter(sensor.getData());
        RecyclerView.LayoutManager mLayoutManager = viewHolder.mLayoutManager;
        rvDatas.setLayoutManager(mLayoutManager);
        rvDatas.setItemAnimator(new DefaultItemAnimator());

        rvDatas.addItemDecoration(viewHolder.divider);
        rvDatas.setAdapter(adapter);
        rvDatasList.add(rvDatas); /* stores the recyclerViews list to be able to refresh them later on */

        String dataNumber = Integer.toString(adapter.getItemCount());
        String ocName = sensor.getName()+ " : "+sensor.getUnit();
        String toPrint = ocName + " " + dataNumber; // if you want to display the number of data
        viewHolder.titre.setText(ocName);
        };










    @Override
    public int getItemCount(){
        return mSensorsList.size();
    }
    /* Provide a direct reference to each of the views within a data item
     Used to cache the views within the item layout for fast access */

    public void swap(ArrayList<Sensor> sensors){
        mSensorsList.clear();
        mSensorsList.addAll(sensors);
        notifyDataSetChanged();
    }



    }


