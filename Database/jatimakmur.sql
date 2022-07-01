-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 01, 2022 at 06:13 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jatimakmur`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `kd_barang` int(5) NOT NULL,
  `nm_barang` varchar(30) DEFAULT NULL,
  `kategori` varchar(255) NOT NULL,
  `satuan` varchar(15) DEFAULT NULL,
  `deskripsi` text DEFAULT NULL,
  `harga` double DEFAULT NULL,
  `harga_beli` double DEFAULT NULL,
  `stok` int(5) DEFAULT NULL,
  `stok_min` int(5) DEFAULT NULL,
  `gambar` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kd_barang`, `nm_barang`, `kategori`, `satuan`, `deskripsi`, `harga`, `harga_beli`, `stok`, `stok_min`, `gambar`) VALUES
(1, 'Ayam Kampung Segar', 'Daging', 'Ekor', 'Daging ayam kampung segar berkualitas tinggi, memiliki tekstur daging yang elastis dan lembut, tanpa formalin, berwarna merah muda karena dipotong pada hari yang sama, berkualitas tinggi.  Sayangi keluarga anda dengan mengonsumsi makanan sehat. Jangan sam', 45000, 40000, 58, 5, 'ayam.png'),
(2, 'Ayam Potong Segar', 'Daging', 'Ekor', 'Daging ayam kampung segar berkualitas tinggi, memiliki tekstur daging yang elastis dan lembut, tanpa formalin, berwarna merah muda karena dipotong pada hari yang sama, berkualitas tinggi.  Sayangi keluarga anda dengan mengonsumsi makanan sehat. Jangan sam', 40000, 25000, 19, 0, 'ayam2.png'),
(3, 'Sawi Putih Segar', 'Sayur', 'Ikat', 'Sawi hijau berkulitas terbaik, tanpa menggunakan bahan kimia', 13000, 10000, 16, 5, 'sawi.png'),
(4, 'Jeruk Khas Jatimakmur', 'Buah', 'Kilogram', 'Jeruk manis, khas  jatimakmur, tanpa menggunakan pengawet', 56000, 43000, 65, 10, 'jeruk.png'),
(5, 'Jagung Manis Premium', 'Buah', 'Kilogram', 'Jagung merupakan makanan pengganti beras yang memiliki berbagai kelebihan dari segi gizi dan manfaatnya. Kandungan yang dimiliki, menjadikan jagung layak untuk dikonsumsi sebagai makanan utama dan diharapkan mampu mengurangi ketergantungan masyarakat akan beras. Untuk menjadikan jagung sebagai makanan pengganti beras, diperlukan data-data yang valid hingga akhirnya dapat menentukan media yang sesuai.', 25000, 20000, 26, 10, 'jagung.png'),
(6, 'Nanas Asli Jawa Tengah', 'Buah', 'Kilogram', 'Lorem ipsum dolor sit amet consectetur, adipisicing elit. Nisi dolorem quod officia! Optio, animi. Quod adipisci, expedita dignissimos facere illo aperiam facilis dolore qui laudantium vero quos animi et magnam.', 35000, 30000, 17, 5, 'nanas.png'),
(7, 'Apel Merah', 'Buah', 'Kilogram', 'Apel Merah kualitas premium', 30000, 35000, 35, 5, 'apel.png'),
(8, 'Telur Ayam Premium', 'Telur', 'Rak', 'Telur ayam, potong premium', 30000, 35000, 26, 5, 'telur.png');

-- --------------------------------------------------------

--
-- Table structure for table `detail_penjualan`
--

CREATE TABLE `detail_penjualan` (
  `id` int(5) NOT NULL,
  `no_nota` int(5) DEFAULT NULL,
  `kd_brg` int(5) DEFAULT NULL,
  `hrg_brg` decimal(12,2) DEFAULT NULL,
  `jml_brg` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_penjualan`
--

INSERT INTO `detail_penjualan` (`id`, `no_nota`, `kd_brg`, `hrg_brg`, `jml_brg`) VALUES
(32, 104, 14, '30000.00', 1),
(33, 105, 13, '30000.00', 2),
(34, 106, 8, '30000.00', 4),
(35, 107, 6, '35000.00', 1),
(36, 0, 2, '40000.00', 1),
(37, 0, 5, '25000.00', 1),
(38, 109, 4, '56000.00', 1),
(39, 110, 2, '40000.00', 1),
(40, 111, 2, '40000.00', 1),
(41, 112, 15, '30000.00', 1),
(42, 113, 2, '40000.00', 1),
(43, 114, 7, '30000.00', 1),
(44, 114, 9, '30000.00', 1),
(45, 114, 10, '30000.00', 1),
(46, 115, 4, '56000.00', 1),
(47, 115, 6, '35000.00', 1),
(48, 115, 8, '30000.00', 1),
(49, 116, 4, '56000.00', 1),
(50, 117, 7, '30000.00', 1),
(51, 117, 8, '30000.00', 2),
(52, 118, 1, '30000.00', 1),
(53, 119, 2, '40000.00', 1),
(54, 119, 4, '56000.00', 1),
(55, 120, 9, '30000.00', 1),
(56, 121, 2, '40000.00', 1),
(57, 122, 2, '40000.00', 1),
(58, 123, 2, '40000.00', 1),
(59, 124, 7, '30000.00', 1),
(60, 125, 11, '30000.00', 1),
(61, 126, 11, '30000.00', 1),
(62, 127, 16, '30000.00', 1),
(63, 128, 17, '30000.00', 4),
(64, 129, 11, '30000.00', 1),
(65, 130, 23, '22.00', 1),
(66, 131, 22, '90.00', 1),
(67, 132, 12, '30000.00', 1),
(68, 133, 15, '30000.00', 1),
(69, 134, 24, '22.00', 1),
(70, 135, 24, '22.00', 1),
(71, 136, 11, '30000.00', 1),
(72, 137, 11, '30000.00', 2),
(73, 138, 5, '25000.00', 1),
(74, 139, 11, '30000.00', 1),
(75, 140, 13, '30000.00', 1),
(76, 141, 10, '30000.00', 1),
(77, 142, 2, '40000.00', 1),
(78, 142, 7, '30000.00', 1),
(79, 143, 2, '40000.00', 1),
(80, 144, 20, '30000.00', 1),
(81, 145, 1, '30000.00', 1),
(82, 146, 9, '30000.00', 1),
(83, 147, 3, '38000.00', 1),
(84, 148, 7, '30000.00', 1),
(85, 148, 10, '30000.00', 1),
(86, 149, 10, '30000.00', 1),
(87, 149, 11, '30000.00', 1),
(88, 149, 12, '30000.00', 1),
(89, 150, 4, '56000.00', 5),
(90, 150, 10, '30000.00', 3),
(91, 151, 2, '40000.00', 1),
(92, 152, 8, '30000.00', 1),
(93, 153, 8, '30000.00', 3),
(94, 153, 2, '40000.00', 1),
(95, 154, 8, '30000.00', 1),
(96, 154, 20, '30000.00', 2),
(97, 154, 9, '30000.00', 2),
(98, 155, 3, '38000.00', 1),
(99, 156, 2, '40000.00', 1),
(100, 157, 2, '40000.00', 1),
(101, 158, 12, '30000.00', 1),
(102, 158, 13, '30000.00', 1),
(103, 158, 14, '30000.00', 1),
(104, 159, 1, '30000.00', 1),
(105, 160, 9, '30000.00', 1),
(106, 160, 10, '30000.00', 1),
(107, 160, 12, '30000.00', 1),
(108, 161, 4, '56000.00', 2),
(109, 162, 4, '56000.00', 1),
(110, 163, 4, '56000.00', 1),
(111, 163, 11, '30000.00', 1),
(112, 163, 12, '30000.00', 1),
(113, 164, 2, '40000.00', 1),
(114, 165, 4, '56000.00', 1),
(115, 166, 6, '35000.00', 1),
(116, 167, 6, '35000.00', 1),
(117, 167, 8, '30000.00', 1),
(118, 168, 2, '40000.00', 1),
(119, 169, 2, '40000.00', 1),
(120, 170, 8, '30000.00', 1),
(121, 171, 6, '35000.00', 1),
(122, 172, 8, '30000.00', 1),
(123, 173, 8, '30000.00', 1),
(124, 174, 19, '30000.00', 1),
(125, 174, 20, '30000.00', 1),
(126, 175, 6, '35000.00', 1),
(127, 175, 12, '30000.00', 1),
(128, 177, 2, '40000.00', 1),
(129, 178, 18, '30000.00', 1),
(130, 179, 4, '56000.00', 1),
(131, 179, 14, '30000.00', 1),
(132, 180, 2, '40000.00', 1),
(133, 181, 4, '56000.00', 1),
(134, 181, 6, '35000.00', 1),
(135, 181, 7, '30000.00', 1),
(136, 181, 8, '30000.00', 1),
(137, 182, 6, '35000.00', 1),
(138, 183, 2, '40000.00', 1),
(139, 184, 2, '40000.00', 1),
(140, 185, 2, '40000.00', 1),
(141, 186, 6, '35000.00', 1),
(142, 187, 2, '40000.00', 1),
(143, 188, 2, '40000.00', 1),
(144, 189, 2, '40000.00', 1),
(145, 190, 2, '40000.00', 1),
(146, 191, 1, '45000.00', 3),
(147, 192, 2, '40000.00', 1),
(148, 193, 2, '40000.00', 1),
(149, 194, 2, '40000.00', 1),
(150, 195, 2, '40000.00', 1),
(151, 196, 2, '40000.00', 1),
(152, 198, 1, '45000.00', 1),
(153, 199, 1, '45000.00', 1),
(154, 200, 6, '35000.00', 1),
(155, 201, 2, '40000.00', 1),
(156, 208, 2, '40000.00', 1),
(157, 209, 1, '45000.00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `konsumen`
--

CREATE TABLE `konsumen` (
  `kd_konsumen` int(5) NOT NULL,
  `oauth_provider` enum('facebook','google','twitter','') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `oauth_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `nm_konsumen` varchar(35) DEFAULT '',
  `alamat` text DEFAULT '',
  `kodepos` varchar(5) DEFAULT '',
  `kota` varchar(35) DEFAULT '',
  `no_hp` varchar(13) DEFAULT '',
  `email` varchar(50) NOT NULL DEFAULT '',
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `foto` tinytext DEFAULT 'default.jpg',
  `group` int(1) NOT NULL DEFAULT 2
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `konsumen`
--

INSERT INTO `konsumen` (`kd_konsumen`, `oauth_provider`, `oauth_uid`, `nm_konsumen`, `alamat`, `kodepos`, `kota`, `no_hp`, `email`, `username`, `password`, `foto`, `group`) VALUES
(54, '', '', '', '', '', '', '', 'natalia@gmail.com', 'natalia', '376e0b8ada5d1e4b70ae72e6348ec1cc', 'default.jpg', 2),
(55, '', '', '', '', '', '', '', 'kevintoding@gmail.com', 'Kevin Toding', '0e27aa65c7a8945b59dc0d615ba8837d', 'default.jpg', 2),
(56, '', '', '', '', '', '', '', 'eve@gmail.com', 'Eve Ichwan', '6a1fadadd676b115a3ba0cc457fb556c', 'default.jpg', 2),
(57, '', '', '', '', '', '', '', 'chika@gmail.com', 'chika zeruya', '0316e6d284704290d6253015c2e021a6', 'default.jpg', 2),
(58, '', '', '', '', '', '', '', 'Indah@gmail.com', 'Indah Cahaya', 'fe291d37d6bad6cacaa5c6df06060305', 'default.jpg', 2),
(60, '', '', 'kevin', 'pemanikan', '91831', 'rantepao', '090990232', 'kevin@gmail.com', 'kevingilbert', '0e27aa65c7a8945b59dc0d615ba8837d', 'default.jpg', 1);

-- --------------------------------------------------------

--
-- Table structure for table `penjualan`
--

CREATE TABLE `penjualan` (
  `no_nota` int(5) NOT NULL,
  `kd_konsumen` int(5) DEFAULT NULL,
  `tgl_jual` datetime NOT NULL DEFAULT current_timestamp(),
  `due_date` datetime DEFAULT NULL,
  `pembelian` decimal(12,2) DEFAULT NULL,
  `tujuan` text DEFAULT NULL,
  `kode_kab` varchar(255) DEFAULT NULL,
  `kurir` varchar(5) DEFAULT NULL,
  `expedisi` varchar(40) DEFAULT NULL,
  `provinsi` varchar(255) NOT NULL,
  `kabupaten` varchar(255) NOT NULL,
  `wkt_pengiriman` varchar(20) NOT NULL,
  `ongkir` decimal(12,2) DEFAULT NULL,
  `total_biaya` decimal(12,2) DEFAULT NULL,
  `image` text DEFAULT NULL,
  `status` enum('Belum dibayar','Sudah dibayar') NOT NULL DEFAULT 'Belum dibayar'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `penjualan`
--

INSERT INTO `penjualan` (`no_nota`, `kd_konsumen`, `tgl_jual`, `due_date`, `pembelian`, `tujuan`, `kode_kab`, `kurir`, `expedisi`, `provinsi`, `kabupaten`, `wkt_pengiriman`, `ongkir`, `total_biaya`, `image`, `status`) VALUES
(179, 58, '2022-06-04 09:47:07', '2022-06-11 09:47:07', '86000.00', 'adasasd', '17', 'pos', 'POS - Paket Kilat Khusus', '', '', '4 HARI', '58000.00', '144000.00', 'BuktiPembayaran-179.png', 'Sudah dibayar'),
(180, 58, '2022-06-17 11:46:26', '2022-06-24 11:46:26', '40000.00', 'Pemnakan', '232', 'jne', 'JNE - OKE', '', '', '3-6', '38000.00', '78000.00', 'BuktiPembayaran-180.png', 'Sudah dibayar'),
(181, 58, '2022-07-01 15:09:36', '2022-07-08 15:09:36', '151000.00', 'adsdsad', '210', 'pos', 'POS - Paket Kilat Khusus', '', '', '2 HARI', '20000.00', '171000.00', 'BuktiPembayaran-181.png', 'Sudah dibayar'),
(182, 58, '2022-07-01 15:27:19', '2022-07-08 15:27:19', '35000.00', 'jldadsaasd', '175', 'jne', 'JNE - OKE', '', '', '3-6', '80000.00', '115000.00', 'BuktiPembayaran-182.png', 'Sudah dibayar'),
(183, 58, '2022-07-01 15:37:34', '2022-07-08 15:37:34', '40000.00', 'Jl. Pembagunnan', '486', 'jne', 'JNE - OKE', '', '', '6-8', '94000.00', '134000.00', 'BuktiPembayaran-183.png', 'Sudah dibayar'),
(184, 58, '2022-07-01 15:42:51', '2022-07-08 15:42:51', '40000.00', 'Jl. Pahlawan no 17', '28', 'jne', 'JNE - OKE', '', '', '3-6', '88000.00', '128000.00', 'BuktiPembayaran-184.png', 'Sudah dibayar'),
(185, 58, '2022-07-01 15:45:36', '2022-07-08 15:45:36', '40000.00', 'Jl  mMekaa', '130', 'pos', 'POS - Paket Kilat Khusus', '', '', '5 HARI', '126000.00', '166000.00', 'BuktiPembayaran-185.png', 'Sudah dibayar'),
(186, 58, '2022-07-01 16:04:18', '2022-07-08 16:04:18', '35000.00', 'Jl. Pahlawan no 17', '451', 'jne', 'JNE - OKE', '', '', '6-8', '94000.00', '129000.00', 'BuktiPembayaran-186.png', 'Sudah dibayar'),
(187, 58, '2022-07-01 16:09:07', '2022-07-08 16:09:07', '40000.00', 'jl kkk', '28', 'jne', 'JNE - OKE', '', '', '3-6', '88000.00', '128000.00', 'BuktiPembayaran-187.png', 'Sudah dibayar'),
(188, 58, '2022-07-01 16:21:55', '2022-07-08 16:21:55', '40000.00', 'dasddsad', '27', 'jne', 'JNE - OKE', '', '', '3-6', '88000.00', '128000.00', 'BuktiPembayaran-188.png', 'Sudah dibayar'),
(189, 58, '2022-07-01 16:40:47', '2022-07-08 16:40:47', '40000.00', 'dfsdfsdfsf', '486', 'jne', 'JNE - OKE', '28', '486', '6-8', '94000.00', '134000.00', NULL, 'Belum dibayar'),
(190, 58, '2022-07-01 16:42:11', '2022-07-08 16:42:11', '40000.00', 'sdadsd', '64', 'jne', 'JNE - OKE', '4', '64', '3-6', '80000.00', '120000.00', NULL, 'Belum dibayar'),
(191, 58, '2022-07-01 16:44:24', '2022-07-08 16:44:24', '135000.00', 'hgfhgfh', '94', 'pos', 'POS - Paket Kilat Khusus', '1', '94', '4 HARI', '72000.00', '207000.00', 'BuktiPembayaran-191.png', 'Sudah dibayar'),
(192, 58, '2022-07-01 16:49:11', '2022-07-08 16:49:11', '40000.00', 'kkk', '27', 'jne', 'JNE - OKE', '', '', '3-6', '88000.00', '128000.00', 'BuktiPembayaran-192.png', 'Sudah dibayar'),
(193, 58, '2022-07-01 17:02:37', '2022-07-08 17:02:37', '40000.00', 'jalana maranatha', '106', 'jne', 'JNE - OKE', '3', '106', '2-3', '28000.00', '68000.00', NULL, 'Belum dibayar'),
(194, 58, '2022-07-01 17:06:21', '2022-07-08 17:06:21', '40000.00', 'sfdsfs', '331', 'jne', 'JNE - OKE', '3', '331', '3-6', '38000.00', '78000.00', NULL, 'Belum dibayar'),
(195, 58, '2022-07-01 17:09:51', '2022-07-08 17:09:51', '40000.00', 'err', '233', 'jne', 'JNE - OKE', '', '', '3-6', '80000.00', '120000.00', NULL, 'Belum dibayar'),
(196, 58, '2022-07-01 17:11:17', '2022-07-08 17:11:17', '40000.00', 'gsgdg', '175', 'jne', 'JNE - OKE', '', '', '3-6', '80000.00', '120000.00', NULL, 'Belum dibayar'),
(197, 58, '2022-07-01 17:11:17', '2022-07-08 17:11:17', '0.00', 'gsgdg', '175', 'jne', 'JNE - OKE', '', '', '3-6', '80000.00', '80000.00', 'BuktiPembayaran-197.png', 'Sudah dibayar'),
(198, 58, '2022-07-01 17:12:36', '2022-07-08 17:12:36', '45000.00', 'jl. pahalawan', '63', 'jne', 'JNE - OKE', '', '', '3-6', '80000.00', '125000.00', 'BuktiPembayaran-198.png', 'Sudah dibayar'),
(199, 58, '2022-07-01 17:31:07', '2022-07-08 17:31:07', '45000.00', 'ggdfgdg', 'Bengkulu Utara', 'jne', 'JNE - OKE', '', '', '3-6', '80000.00', '125000.00', 'BuktiPembayaran-199.png', 'Sudah dibayar'),
(200, 58, '2022-07-01 17:35:16', '2022-07-08 17:35:16', '35000.00', 'fdfdsfsdf', 'Pandeglang', 'jne', 'JNE - OKE', '', '', '3-6', '38000.00', '73000.00', 'BuktiPembayaran-200.png', 'Sudah dibayar'),
(201, 58, '2022-07-01 17:41:10', '2022-07-08 17:41:10', '40000.00', 'jl. pattimura', 'Bangka Tengah', 'jne', 'JNE - OKE', 'Bangka Belitung', '', '3-6', '88000.00', '128000.00', 'BuktiPembayaran-201.png', 'Sudah dibayar'),
(202, 58, '2022-07-01 17:46:33', '2022-07-08 17:46:33', '40000.00', 'gdgd', '175', 'jne', 'JNE - OKE', '', '', '3-6', '80000.00', '120000.00', NULL, 'Belum dibayar'),
(203, 58, '2022-07-01 17:46:38', '2022-07-08 17:46:38', '40000.00', 'gdgd', '175', 'jne', 'JNE - OKE', '', '', '3-6', '80000.00', '120000.00', NULL, 'Belum dibayar'),
(204, 58, '2022-07-01 17:47:06', '2022-07-08 17:47:06', '40000.00', 'gdgd', '175', 'jne', 'JNE - OKE', '', '', '3-6', '80000.00', '120000.00', NULL, 'Belum dibayar'),
(205, 58, '2022-07-01 17:47:07', '2022-07-08 17:47:07', '40000.00', 'gdgd', '175', 'jne', 'JNE - OKE', '', '', '3-6', '80000.00', '120000.00', NULL, 'Belum dibayar'),
(206, 58, '2022-07-01 17:47:08', '2022-07-08 17:47:08', '40000.00', 'gdgd', '175', 'jne', 'JNE - OKE', '', '', '3-6', '80000.00', '120000.00', NULL, 'Belum dibayar'),
(207, 58, '2022-07-01 17:47:49', '2022-07-08 17:47:49', '40000.00', 'dfdsfsd', '331', 'jne', 'JNE - OKE', '', '', '3-6', '38000.00', '78000.00', NULL, 'Belum dibayar'),
(208, 58, '2022-07-01 17:48:04', '2022-07-08 17:48:04', '40000.00', 'dfdsfsd', '331', 'jne', 'JNE - OKE', '', '', '3-6', '38000.00', '78000.00', 'BuktiPembayaran-208.png', 'Sudah dibayar'),
(209, 58, '2022-07-01 17:53:40', '2022-07-08 17:53:40', '45000.00', 'Jl. oembangunan', '232', 'jne', 'JNE - OKE', 'Banten', 'Lebak', '3-6', '38000.00', '83000.00', 'BuktiPembayaran-209.png', 'Sudah dibayar');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`kd_barang`);

--
-- Indexes for table `detail_penjualan`
--
ALTER TABLE `detail_penjualan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `konsumen`
--
ALTER TABLE `konsumen`
  ADD PRIMARY KEY (`kd_konsumen`);

--
-- Indexes for table `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`no_nota`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `kd_barang` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `detail_penjualan`
--
ALTER TABLE `detail_penjualan`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=158;

--
-- AUTO_INCREMENT for table `konsumen`
--
ALTER TABLE `konsumen`
  MODIFY `kd_konsumen` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT for table `penjualan`
--
ALTER TABLE `penjualan`
  MODIFY `no_nota` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=210;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
