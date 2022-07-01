<?php
include_once("connection.php");

$kd_konsumen = $_POST["kd_konsumen"];
$username = $_POST["username"];
$password = md5($_POST["password"]);

$query = "UPDATE konsumen SET `username`='$username', `password`='$password' WHERE kd_konsumen='$kd_konsumen'";
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