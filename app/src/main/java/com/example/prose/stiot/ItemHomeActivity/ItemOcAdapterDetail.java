package com.example.prose.stiot.ItemHomeActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.prose.stiot.R;

import java.util.ArrayList;

/**
 * Created by billy on 19/04/16.
 */
public class ItemOcAdapterDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<ItemOcDetail> boardCoDetail;


    public ItemOcAdapterDetail(ArrayList<ItemOcDetail> tableauOc_detail) {
        boardCoDetail = tableauOc_detail;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.linelastmeasure, parent, false));
    }


    //Appplique une donnée a une cellule
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder bvh = (ItemViewHolder) holder;
        String test = "TU VAS MARCHER";


        bvh.measure.setText(boardCoDetail.get(position).getMesure());
        bvh.unit.setText(boardCoDetail.get(position).getUnite());
        bvh.value.setText(boardCoDetail.get(position).getValeur());
    }

    //NBR total de celule crée
    @Override
    public int getItemCount() {
        return boardCoDetail.size();
    }





    private class ItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView measure;
        protected TextView value;
        protected TextView unit;

        public ItemViewHolder(View v) {
            super(v);
            measure = (TextView) v.findViewById(R.id.measure);
            unit = (TextView) v.findViewById(R.id.unit);
            value = (TextView) v.findViewById(R.id.value);
        }
    }
}
