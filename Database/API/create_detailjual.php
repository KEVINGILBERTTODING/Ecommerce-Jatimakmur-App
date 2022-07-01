<?php
include_once('connection.php');

$no_nota    = $_POST['no_nota'];
$kode       = $_POST['kode'];
$harga      = $_POST['harga'];
$jumlah     = $_POST['jumlah'];

$insert = "INSERT INTO detail_penjualan(no_nota,kd_brg,hrg_brg,jml_brg)VALUES('$no_nota','$kode','$harga','$jumlah')";
$exeinsert = mysqli_query($koneksi,$insert);

$response = array();
if($exeinsert)
{
$response['code'] =1;
$response['message'] = "Success ! Detail pemesanan dibuat";
}
else
{
$response['code'] =0;
$response['message'] = "Failed ! Detail pemesanan gagal dibuat";
$response['query'] = $insert;
}
echo json_encode($response);
?>