<?php
include_once("connection.php");

$username = $_POST["kode_konsumen"];

$query = "SELECT * FROM konsumen WHERE kd_konsumen='$username'";
$result = mysqli_query($koneksi,$query);
$arraydata = array();

while($baris = mysqli_fetch_assoc($result)){
    $arraydata[] = $baris;
}
echo json_encode($arraydata);
?>