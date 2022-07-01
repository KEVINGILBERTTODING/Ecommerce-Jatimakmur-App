<?php
include_once "connection.php";
class usr{}

$oauth_provider = "google";
$oauth_uid = $_POST["oauth_uid"];
$nm_konsumen = $_POST["nm_konsumen"];
$email = $_POST["email"];
$username = $_POST["username"];
$group = 2;

$query = mysqli_query($koneksi, "SELECT * FROM konsumen WHERE oauth_provider='$oauth_provider' AND oauth_uid='$oauth_uid'");

$row = mysqli_fetch_array($query);
if (!empty($row)){
 $response = new usr();
 $response->success = 1;
 $response->message = "Selamat datang ".$row['username'];
 $response->username = $row['username'];
 $response->kode_konsumen = $row['kd_konsumen'];
 die(json_encode($response));

} else if(empty($row)){
    mysqli_query($koneksi, "INSERT INTO `konsumen`(`oauth_provider`, `oauth_uid`, `nm_konsumen`, `email`, `username`, `group`)
                                    VALUES ('$oauth_provider', '$oauth_uid', '$nm_konsumen', '$email', '$username', $group)");

    $query2 = mysqli_query($koneksi, "SELECT * FROM konsumen WHERE oauth_provider='$oauth_provider' AND oauth_uid='$oauth_uid'");

    $row2 = mysqli_fetch_array($query2);
    if (!empty($row2)){
    $response = new usr();
    $response->success = 1;
    $response->message = "Selamat datang ".$row2['username'];
    $response->username = $row2['username'];
    $response->kode_konsumen = $row2['kd_konsumen'];
    die(json_encode($response));

    }else{
        $response = new usr();
        $response->success = 0;
        $response->message = "Login Gagal";
        die(json_encode($response));
    }

}
?> 
