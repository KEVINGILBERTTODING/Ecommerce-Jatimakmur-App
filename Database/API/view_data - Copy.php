<?php
include_once("connection.php");
$query = "SELECT * FROM barang WHERE stok-stok_min>=1";
$result = mysqli_query($koneksi,$query);
$arraydata = array();

while($baris = mysqli_fetch_assoc($result)){
    $arraydata[] = $baris;
}
echo json_encode($arraydata);
?>