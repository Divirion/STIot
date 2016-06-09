package com.example.prose.stiot.ItemDetailOcActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.prose.stiot.R;
import com.example.prose.stiot.Model.Data;

import java.util.List;

/**
 * @author David COUET
 * @version 0.0
 * @file ItemDataAdapter
 * @date 13/05/16
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
 * @Class ItemDataAdapter
 * @brief ItemDataAdapter provides access to the datas for ItemSensorAdapter's recyclerView
 * and makes a view for each item.
 * */
public class ItemDataAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Data> mDataList;

    public static class ViewHolder extends RecyclerView.ViewHolder {


        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        protected TextView values;
        protected TextView date;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            values = (TextView) itemView.findViewById(R.id.measure);
            date = (TextView) itemView.findViewById(R.id.tvDate);

        }
    }

    public ItemDataAdapter(List<Data> data){
        this.mDataList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.itemdetailcolistlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position){
        Data data = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        String valueString = Float.toString(data.getValue());
        String dateString = data.getDate();
        String finalString = dateString + " : " + valueString;
        TextView textView =viewHolder.values;
        textView.setText(valueString);
        TextView tvDate = viewHolder.date;
        tvDate.setText(dateString);





    }



    @Override
    public int getItemCount(){
        return mDataList.size();
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access


}