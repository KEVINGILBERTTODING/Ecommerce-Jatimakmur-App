<?php
include_once("connection.php");

$kd_kons = $_POST['kd_kons'];
$query = "SELECT `no_nota`, `due_date`, `status`, `total_biaya` FROM penjualan WHERE `kd_konsumen`=$kd_kons ORDER BY `tgl_jual` DESC";
$result = mysqli_query($koneksi,$query);
$arraydata = array();

while($baris = mysqli_fetch_assoc($result)){
    $arraydata[] = $baris;
}
echo json_encode($arraydata);
?>