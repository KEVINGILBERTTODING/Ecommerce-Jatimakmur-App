package com.kevin.jatimakmur;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> implements Filterable {
    //initial list
    private static ArrayList<Produk> listBarang;
    protected static ArrayList<Produk> listBarangfull = new ArrayList<>();
    Context context;
    private ItemClickListener clickListener;

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public ProdukAdapter(ArrayList<Produk> dataList, Context context)
    {
        this.context=context;
        this.listBarang = dataList;
    }

    //Mengatur Inflater
    @NonNull
    @Override
    public ProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new ProdukViewHolder(view);
    }

    //Menampilkan Data pada tampilan cardview
    @Override
    public void onBindViewHolder(final ProdukViewHolder holder, int position){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

        holder.txtNama.setText(listBarang.get(position).getNama());
        holder.txtDeskripsi.setText(listBarang.get(position).getDeskripsi());
        holder.txtHarga.setText("Rp. "+decimalFormat.format(listBarang.get(position).getHarga()));
        Glide.with(holder.itemView.getContext()) //konteks bisa didapat dari activity yang sedang berjalan
                .load(listBarang.get(position).getImg()) // mengambil data dengan cara "list.get(position)" mendapatkan isi berupa objek Menu. kemudian "Menu.geturlGambar"
                .thumbnail(0.5f) // resize gambar menjadi setengahnya
                .into(holder.icon); // mengisikan ke imageView
        Log.d("gambar : ","listBarang.get(position).getImg()");
    }

    //mengitung jumlah data yang akan ditampilkan
    @Override
    public int getItemCount() {
        return listBarang.size();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
    public class ProdukViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtNama, txtHarga, txtDeskripsi;
        private ImageView icon;
        public ProdukViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama_produk);
            txtHarga = (TextView) itemView.findViewById(R.id.txt_harga_produk);
            txtDeskripsi = (TextView) itemView.findViewById(R.id.txt_deskripsi);
            icon=(ImageView) itemView.findViewById(R.id.img_card);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            txtNama.setOnClickListener(this);
            icon.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(clickListener != null) clickListener.onClick(view,
                    getAdapterPosition());
        }
    }

    //filter(pencarian)
    @Override
    public Filter getFilter() {
        return barangFilter;
    }

    //method filter
    private Filter barangFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Produk> filteredList = new ArrayList<>();

            // jika tidak tertulis apa2 di searchview
            Log.d("constraint", constraint.toString());
            if(constraint.length() == 0){
                filteredList.addAll(ProdukAdapter.listBarangfull);
            }else{
                //jika terdapat character pada searchview
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Produk item: ProdukAdapter.listBarangfull){
                    if(item.getNama().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //fungsi realtime searching
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.d("list produk", listBarang.toString());
            Log.d("list produk full", listBarangfull.toString());
            listBarang.clear();
            listBarang.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
