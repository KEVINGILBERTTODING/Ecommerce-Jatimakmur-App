package com.JatimakmurApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private static ArrayList<Pembelian> listPembelian;
    Context context;
    private ItemClickListener clickListener;

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public HistoryAdapter(ArrayList<Pembelian> listPembelian, Context context)
    {
        this.context=context;
        this.listPembelian = listPembelian;
    }

    //Mengatur Inflater
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_pembelian, parent, false);
        return new HistoryViewHolder(view);
    }

    //Menampilkan Data pada tampilan cardview
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(final HistoryViewHolder holder, int position){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

        holder.due_date.setText(listPembelian.get(position).getDue_date());
        holder.biaya.setText("Rp. "+decimalFormat.format(listPembelian.get(position).getTotal_biaya()));
        String status = listPembelian.get(position).getStatus();
        holder.status.setText(status);
        if(status.equalsIgnoreCase("Sudah dibayar")){
            holder.status.setTextColor(Color.parseColor("#FF4CAF50"));
        }else if(status.equalsIgnoreCase("Belum dibayar")){
            holder.status.setTextColor(Color.parseColor("#FFE91E63"));
        }

        Log.d("listPembelian", "onBindViewHolder: "+listPembelian);
    }

    //mengitung jumlah data yang akan ditampilkan
    @Override
    public int getItemCount() {
        return listPembelian.size();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView due_date, biaya, status;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            due_date = (TextView) itemView.findViewById(R.id.tv_due_date);
            biaya = (TextView) itemView.findViewById(R.id.tv_biayaBeli);
            status = (TextView) itemView.findViewById(R.id.tv_status);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
//            txtNama.setOnClickListener(this);
//            icon.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(clickListener != null) clickListener.onClick(view,
                    getAdapterPosition());
        }
    }

}
