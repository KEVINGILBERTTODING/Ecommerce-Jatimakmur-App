<?php
include_once("connection.php");

$username = $_POST["kode_konsumen"];
$nmkonsumen = $_POST["nm_konsumen"];
$alamat = $_POST["alamat"];
$kodepos = $_POST["kodepos"];
$kota = $_POST["kota"];
$no_hp = $_POST["no_hp"];
$email = $_POST["email"];

$query = "UPDATE konsumen SET nm_konsumen='$nmkonsumen', alamat='$alamat', kodepos='$kodepos', kota='$kota', no_hp='$no_hp', email='$email' WHERE kd_konsumen='$username'";
$result = mysqli_query($koneksi,$query);

$response = array();
if($result)
{
$response['message'] = "Success ! Data berhasil diperbarui";
}
else
{
$response['message'] = "Failed ! Data gagal diperbarui";
}
echo json_encode($response);
?>