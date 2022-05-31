package com.kencana.kampunganggrek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ProdukViewHolder>{
    private static ArrayList<Produk> listcart;


    public CartAdapter(ArrayList<Produk> dataList)
    {
        this.listcart = dataList;
    }

    //Mengatur Inflater
    @NonNull
    @Override
    public ProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cart, parent, false);
        return new ProdukViewHolder(view);
    }

    //Menampilkan Data pada tampilan cardview
    @Override
    public void onBindViewHolder(final ProdukViewHolder holder, int position){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

        holder.txtNama.setText(listcart.get(position).getNama());
        holder.txtSubtotal.setText("Rp. "+decimalFormat.format((listcart.get(position).getHarga() * listcart.get(position).getJmlBeli())));
        holder.txtHarga.setText("Rp. "+decimalFormat.format(listcart.get(position).getHarga())+" X "+ listcart.get(position).getJmlBeli());
    }

    //mengitung jumlah data yang akan ditampilkan
    @Override
    public int getItemCount() {
        return listcart.size();
    }

    public class ProdukViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtHarga, txtSubtotal;
        private ImageView icon;
        public ProdukViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.tv_cart_nama);
            txtHarga = (TextView) itemView.findViewById(R.id.tv_cart_harga);
            txtSubtotal = (TextView) itemView.findViewById(R.id.tv_cart_jumlah);
        }

    }
}
