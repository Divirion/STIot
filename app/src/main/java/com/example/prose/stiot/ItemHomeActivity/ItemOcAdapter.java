/**
 * @file      ItemOcAdapter.java
 * @author    Arnaud B
 * @version   1.0
 * @date      19 April 2016
 * @brief     Adapter of ItemOC
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

package com.example.prose.stiot.ItemHomeActivity;

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.prose.stiot.ConnectionActivity;
import com.example.prose.stiot.DetailActivity;
import com.example.prose.stiot.HomeActivity;
import com.example.prose.stiot.ItemHomeActivity.ItemOc;
import com.example.prose.stiot.MyService;
import com.example.prose.stiot.R;

import java.util.ArrayList;



public class ItemOcAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ItemOc> boardCo;
    private Context context;
    private int position;
    final String CO_NUMBER_IN_LIST = "number in list";
    public static int test=1;

    // Provide a suitable constructor (depends on the kind of boradOc)
    public ItemOcAdapter(ArrayList<ItemOc> boardCo, Context context) {
        this.boardCo = boardCo;
        this.context=context;

    }


    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));

    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // - get element from your tableauOc at this position
        // - replace the contents of the view with that element
        final ItemOc itemOc= this.boardCo.get(position);

        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

       // itemViewHolder.nameCo.setText(itemOc.getTitle());
        itemViewHolder.nameCo.setText(itemOc.getTitle());
        String buttonTitle = itemOc.getTitle() + " TestDetails";
        itemViewHolder.buttonCo.setText(buttonTitle);
        //itemViewHolder.buttonCo.setText(itemOc.getTitleButton());
        itemViewHolder.listMeasure.setAdapter(itemOc.getAdapter());
        itemOc.setPosition(position);
        this.position = position;

        itemViewHolder.buttonCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyService.flagDisableActualizing=true;
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                Log.e("ItemsOc Adapter","Intent OK"+position);
                intent.putExtra(CO_NUMBER_IN_LIST,Integer.toString(position));

                v.getContext().startActivity(intent);

                Log.e("ItemsOc Adapter","Start activity OK"+position);






            }

        });
    }


    @Override
    public int getItemCount() {
        return boardCo.size();
    }


    public int getPosition(){return position;}



    private class ItemViewHolder extends RecyclerView.ViewHolder{
        protected TextView nameCo;
        protected Button buttonCo;
        protected RecyclerView  listMeasure;

        public ItemViewHolder(View v) {
            super(v);
            nameCo= (TextView) v.findViewById(R.id.tvTitle);
            buttonCo = (Button) v.findViewById(R.id.bpV);



           listMeasure = (RecyclerView) v.findViewById(R.id.listdonne);
            LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            listMeasure.setLayoutManager(llm);
        }


    }


}
