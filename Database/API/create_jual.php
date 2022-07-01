<?php
include_once('connection.php');
$username = $_POST['username'];
$get_kd_kons = mysqli_fetch_row(mysqli_query($koneksi, "select kd_konsumen from konsumen where username='$username'"));

$kd_kons = $get_kd_kons[0];
$tanggal = date('Y-m-d H:i:s', mktime(date('H'), date('i'), date('s'), date('m'), date('d'), date('Y')));
$due_date = date('Y-m-d H:i:s', mktime(date('H'), date('i'), date('s'), date('m'), date('d') + 7, date('Y')));
$pembelian = $_POST['pembelian'];
$tujuan = $_POST['tujuan'];
$kode_kab = $_POST['kode_kab'];
$kurir = $_POST['kurir'];
$expedisi = $_POST['expedisi'];
$wkt_pengiriman = $_POST['wkt_pengiriman'];
$ongkir = $_POST['ongkir'];
$total = $_POST['total'];

$kabupaten = $_POST['kabupaten'];
$provinsi = $_POST['provinsi'];


$insert = "INSERT INTO penjualan(kd_konsumen,tgl_jual, due_date, pembelian, tujuan, kode_kab, kabupaten, provinsi, kurir, expedisi, wkt_pengiriman, ongkir, total_biaya) 
            VALUES($kd_kons,'$tanggal', '$due_date', '$pembelian', '$tujuan', '$kode_kab', '$kabupaten', '$provinsi', '$kurir', '$expedisi', '$wkt_pengiriman', '$ongkir', '$total')";
$exeinsert = mysqli_query($koneksi, $insert);

$get_no_nota = mysqli_fetch_row(mysqli_query($koneksi, "select no_nota from penjualan where kd_konsumen='$kd_kons' and tgl_jual='$tanggal' and total_biaya='$total'"));
$no_nota = $get_no_nota[0];

$response = array();
if ($exeinsert) {
	$response['code'] = 1;
	$response['no_nota'] = $no_nota;
	$response['message'] = "Success ! Pemesanan dibuat";
} else {
	$response['code'] = 0;
	$response['no_nota'] = $no_nota;
	$response['message'] = "Failed ! Pemesanan gagal dibuat";
}
echo json_encode($response);
