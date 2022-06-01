package com.kevin.jatimakmur;

import static com.kevin.jatimakmur.Util.ServerAPI.URL;

public class Produk {
    private String kode;
    private String nama;
    private String satuan;
    private String deskripsi;
    private Integer harga;
    private Integer harga_beli;
    private Integer stok;
    private Integer stok_min;
    private String img;

    private Integer jmlBeli=0;

    public Produk(){}
    public Produk(String kode, String nama, String satuan, String deskripsi, String harga, String harga_beli, String stok, String stok_min, String img) {
        this.kode = kode;
        this.nama = nama;
        this.satuan = satuan;
        this.deskripsi = deskripsi;
        this.harga = Integer.parseInt(harga);
        this.harga_beli = Integer.parseInt(harga_beli);
        this.stok = Integer.parseInt(stok);
        this.stok_min = Integer.parseInt(stok_min);
        this.img=img;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getHarga_beli() {
        return harga_beli;
    }

    public void setHarga_beli(Integer harga_beli) {
        this.harga_beli = harga_beli;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public Integer getStok_min() {
        return stok_min;
    }

    public void setStok_min(Integer stok_min) {
        this.stok_min = stok_min;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() { return URL+"/assets/images/"+img;  }
    public String getImgName() { return img;  }

    public String toString(){
        return "\nkode : " + kode + " Nama : " + nama + " Harga : " + harga +"\n";
    }

    public Integer getJmlBeli() {
        return jmlBeli;
    }

    public void setJmlBeli(Integer jmlBeli) {
        this.jmlBeli = jmlBeli;
    }
}
