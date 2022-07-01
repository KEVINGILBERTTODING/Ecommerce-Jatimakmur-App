<?php
	include_once('../connection.php'); 
	
	class emp{}
	
	$image = $_POST['gambar'];
	$no_nota = $_POST['no_nota'];
	
	$nama_file = "BuktiPembayaran-".$no_nota.".png";
		
		$path = "../../uploads/bukti/".$nama_file;
		
		// sesuiakan ip address laptop/pc atau URL server
		$actualpath = "http://172.20.10.8/android/upload_image/$path";
		
		$query = mysqli_query($koneksi, "UPDATE `penjualan` SET `image`='$nama_file', `status`='Sudah Dibayar' WHERE `no_nota`=$no_nota ");
		
		if ($query){
			file_put_contents($path,base64_decode($image));
			
			$response = new emp();
			$response->success = 1;
			$response->message = "Nota sudah disimpan";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Upload nota gagal";
			die(json_encode($response)); 
		}
	mysqli_close($con);
