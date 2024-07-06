-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 06, 2024 at 03:36 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `absensi`
--

-- --------------------------------------------------------

--
-- Table structure for table `data_akun`
--

CREATE TABLE `data_akun` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nip` varchar(10) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `access_level` enum('pegawai','petugas','manager','') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `data_akun`
--

INSERT INTO `data_akun` (`id`, `username`, `password`, `nip`, `email`, `phone`, `access_level`) VALUES
(1, 'Joko', 'joko123', '11144', 'joko@gmail.com', '081122334455', 'pegawai'),
(2, 'Tono', 'Tono123', '11145', 'tono@gmail.com', '081122774499', 'pegawai'),
(3, 'Jono', 'Jono123', '11146', 'jono@gmail.com', '082222774499', 'pegawai'),
(4, 'petugas1', 'petugas1', '00011', 'petugas1@gmai.com', NULL, 'petugas'),
(5, 'Budi', 'Budi12345', '11147', 'budi@gmail.com', '08123456789', 'pegawai'),
(6, 'Dave', 'Dave123', '000212', 'dave@gmail.com', NULL, 'petugas'),
(7, 'Vincent', 'Vincent123', '00001', 'vincent@gmail.com', NULL, 'manager'),
(22, 'johan', '$2a$10$r4nskNyaEx.wimCF7m8We.KE25iJ.x1ckOzvcrBVhEfwmXYf70X7i', '012322', 'johanextradrive@gmail.com', NULL, 'petugas'),
(23, 'Darren', 'darren', '00023', 'darren@gmail.com', NULL, 'petugas'),
(24, 'manager', 'manager', '00001', 'manager@gmail.com', NULL, 'manager'),
(27, 'tes', 'tes', '111411', 'tes', '214', 'pegawai'),
(28, 'hash', 'hash', '123123', 'hash', NULL, 'petugas'),
(36, 'petugas', '$2a$10$GdYtTA0mTGaGeilp47fIpeolViI3XUPNk0jWAF3BG.9XEr3/ZS6jm', '00022', 'petugas@gmail.com', NULL, 'petugas'),
(37, 'pegawai', '$2a$10$2l79e625MoR/nnyPY5G5J.BhkkJGzLyYgw4LRSmzi9jXOLm.GpTue', '15', 'pegawai@gmail.com', '08264647836123', 'pegawai'),
(38, 'budak', '$2a$10$EUa80eLeYBXZkT23X2q7PeBilHMKHBIJzR2pufX0VMKXWyuoZiMOi', '16', 'budak', '683746644', 'pegawai'),
(39, 'new_man', '$2a$10$VoJWSaagAnwzZ41cyK/vU.j1AoXheNTH0HMxHnxbL4OWy4RUZKDgq', '12321', 'new_man@gmail.com', NULL, 'manager'),
(40, 'Budi', '$2a$10$RtB173gcKdt4G3zC2Pv8A.qpE8w0ujNMARCYJBwKrpSfCCjEa41/i', '11150', 'budi@gmail.com', '08123456789', 'pegawai');

-- --------------------------------------------------------

--
-- Table structure for table `log_absensi`
--

CREATE TABLE `log_absensi` (
  `id` int(11) NOT NULL,
  `nip_pegawai` varchar(10) NOT NULL,
  `waktu_masuk` datetime NOT NULL,
  `waktu_pulang` datetime DEFAULT NULL,
  `foto_bukti_masuk` varchar(200) NOT NULL,
  `foto_bukti_pulang` varchar(200) DEFAULT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'Menunggu Konfirmasi'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `log_absensi`
--

INSERT INTO `log_absensi` (`id`, `nip_pegawai`, `waktu_masuk`, `waktu_pulang`, `foto_bukti_masuk`, `foto_bukti_pulang`, `status`) VALUES
(11, '11144', '2024-04-22 09:24:37', '2024-04-22 09:34:59', 'tzuyu1.jpg', 'tzuyu2.jpg', 'pulang_awal'),
(12, '11145', '2024-06-16 14:12:45', '2024-06-16 14:12:45', 'jisoo1.jpg', 'jisoo2.jpg', 'rejected'),
(13, '11146', '2024-06-16 14:12:45', '2024-06-16 14:12:45', 'rose1.jpg', 'rose2.jpg', 'telat'),
(14, '11147', '2024-06-17 01:58:16', NULL, 'lisa.png', NULL, 'pulang_awal'),
(16, '15', '2024-06-29 15:22:00', '2024-07-02 20:31:16', 'coba', 'foto_bukti_pulang-1719927073855-84979716.jpg', 'Menunggu Konfirmasi'),
(17, '15', '2024-06-29 15:48:18', '2024-07-02 20:31:16', 'coba', 'foto_bukti_pulang-1719927073855-84979716.jpg', 'Menunggu Konfirmasi'),
(18, '15', '2024-06-29 15:53:54', '2024-07-02 20:31:16', 'coba', 'foto_bukti_pulang-1719927073855-84979716.jpg', 'Menunggu Konfirmasi'),
(19, '15', '2024-06-29 16:04:28', '2024-07-02 20:31:16', 'coba', 'foto_bukti_pulang-1719927073855-84979716.jpg', 'Menunggu Konfirmasi'),
(20, '15', '2024-06-29 16:06:41', '2024-07-02 20:31:16', 'coba', 'foto_bukti_pulang-1719927073855-84979716.jpg', 'Menunggu Konfirmasi'),
(21, '15', '2024-06-29 16:22:15', '2024-07-02 20:31:16', 'coba', 'foto_bukti_pulang-1719927073855-84979716.jpg', 'Menunggu Konfirmasi'),
(22, '15', '2024-06-29 16:23:34', '2024-07-02 20:31:16', 'coba', 'foto_bukti_pulang-1719927073855-84979716.jpg', 'Menunggu Konfirmasi'),
(23, '15', '2024-06-29 16:24:06', '2024-07-02 20:31:16', 'foto_bukti_masuk-1719653045697-492944944.jpg', 'foto_bukti_pulang-1719927073855-84979716.jpg', 'Menunggu Konfirmasi'),
(24, '15', '2024-06-29 16:48:27', '2024-07-02 20:31:16', 'foto_bukti_masuk-1719654506873-684735508.jpg', 'foto_bukti_pulang-1719927073855-84979716.jpg', 'Menunggu Konfirmasi'),
(25, '15', '2024-06-29 16:56:19', '2024-07-02 20:31:16', 'foto_bukti_masuk-1719654979155-671091733.jpg', 'foto_bukti_pulang-1719927073855-84979716.jpg', 'Menunggu Konfirmasi'),
(31, '15', '2024-07-02 20:03:29', '2024-07-02 20:31:16', 'foto_bukti_masuk-1719925404478-733860761.jpg', 'foto_bukti_pulang-1719927073855-84979716.jpg', 'Menunggu Konfirmasi'),
(32, '15', '2024-07-02 00:00:00', '2024-07-02 21:06:24', 'foto_bukti_masuk-1719927394879-451251159.jpg', 'foto_bukti_pulang-1719929180409-385016003.jpg', 'Menunggu Konfirmasi'),
(38, '15', '2024-07-05 19:32:02', '2024-07-06 09:22:43', 'foto_bukti_masuk-1720182721319-936472190.jpg', 'foto_bukti_pulang-1720232562496-930369332.jpg', 'Menunggu Konfirmasi'),
(40, '15', '2024-07-06 09:45:03', NULL, 'foto_bukti_masuk-1720233902514-85912069.jpg', NULL, 'Menunggu Konfirmasi'),
(46, '16', '2024-07-06 20:05:59', '2024-07-06 20:06:56', 'foto_bukti_masuk-1720271158983-97152810.jpg', 'foto_bukti_pulang-1720271215697-482606938.jpg', 'Menunggu Konfirmasi');

-- --------------------------------------------------------

--
-- Table structure for table `lokasi`
--

CREATE TABLE `lokasi` (
  `id` int(11) NOT NULL,
  `nip_pegawai` varchar(100) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `waktu` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `lokasi`
--

INSERT INTO `lokasi` (`id`, `nip_pegawai`, `latitude`, `longitude`, `waktu`) VALUES
(2, '15', -7.277752876281738, 112.78984832763672, '2024-07-05 19:32:02'),
(3, '15', -7.274104118347168, 112.78797149658203, '2024-07-06 09:15:07'),
(4, '15', -7.274104118347168, 112.78797149658203, '2024-07-06 09:22:43'),
(5, '15', -7.274104118347168, 112.78797149658203, '2024-07-06 09:45:03'),
(6, '16', -7.27414472, 112.78796618, '2024-07-06 17:03:12'),
(7, '16', -7.27414472, 112.78796618, '2024-07-06 17:03:13'),
(8, '16', -7.27414472, 112.78796618, '2024-07-06 17:04:56'),
(9, '16', -7.27414472, 112.78796618, '2024-07-06 17:05:08'),
(10, '16', -7.27414106, 112.78798104, '2024-07-06 20:05:59'),
(11, '16', -7.27414106, 112.78798104, '2024-07-06 20:06:56'),
(12, '16', -7.27414956, 112.78793156, '2024-07-06 20:13:27');

-- --------------------------------------------------------

--
-- Table structure for table `lokasi_penugasan`
--

CREATE TABLE `lokasi_penugasan` (
  `id` int(11) NOT NULL,
  `nip_pegawai` varchar(20) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `assignment_date` date NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `lokasi_penugasan`
--

INSERT INTO `lokasi_penugasan` (`id`, `nip_pegawai`, `latitude`, `longitude`, `assignment_date`, `created_at`) VALUES
(1, '11145', -7.274104118347168, 112.78797149658203, '2024-07-06', '2024-07-06 03:55:41'),
(2, '15', -7.32104, 113.78797287, '2024-08-06', '2024-07-06 09:39:52'),
(3, '11147', -7.32104, 113.78797287, '2024-07-07', '2024-07-06 09:54:36');

-- --------------------------------------------------------

--
-- Table structure for table `pembagian_tugas`
--

CREATE TABLE `pembagian_tugas` (
  `id` int(11) NOT NULL,
  `nip_pegawai` varchar(10) NOT NULL,
  `nip_petugas` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pembagian_tugas`
--

INSERT INTO `pembagian_tugas` (`id`, `nip_pegawai`, `nip_petugas`) VALUES
(2, '11145', '00022'),
(5, '11146', '00022'),
(9, '11147', '00022'),
(17, '11144', '00022'),
(18, '11147', '00011'),
(19, '11146', '00011'),
(20, '11144', '00011'),
(21, '16', '00022');

-- --------------------------------------------------------

--
-- Table structure for table `reset_tokens`
--

CREATE TABLE `reset_tokens` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `expires_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `reset_tokens`
--

INSERT INTO `reset_tokens` (`id`, `email`, `token`, `expires_at`) VALUES
(7, 'johanmc8703@gmail.com', 'a1496304e079e95ce95d67caa49296475aa22e40', '2024-04-22 10:07:16'),
(8, 'c11210018@john.petra.ac.id', 'a67206b103ae31af3803df6c0426b06ec4de38c5', '2024-04-24 16:44:34'),
(9, 'johanmc8703@gmail.com', '5db8e2f64de96d1ec09ae5b72e9930c4b5e7d8df', '2024-07-03 01:30:54'),
(10, 'johanmc8703@gmail.com', '8aa8d2e6f0fdac5a85e0c22ce302549280edf5c8', '2024-07-03 01:32:33'),
(11, 'johanmc8703@gmail.com', 'b54956b40943418da91607be005bf2ca16aeba86', '2024-07-03 01:33:12'),
(12, 'johanmc8703@gmail.com', 'fe2d54f4b3e7339cefe5d3955c758643833ae310', '2024-07-03 01:36:35'),
(13, 'johanextradrive@gmail.com', '5c4750af2a875635bd8434f45cfbe5acffc4871d', '2024-07-03 01:39:16'),
(14, 'johanextradrive@gmail.com', '76511fcdc82036545eeef06db0bcc9218b0c3919', '2024-07-03 01:39:34'),
(15, 'johanmc8703@gmail.com', '8834c2513431727c6365b6d409f1b645c9502348', '2024-07-03 01:41:48'),
(16, 'johanmc8703@gmail.com', '88d977892978bb6c5febb54ec0882dcbff6ce978', '2024-07-03 01:41:54'),
(17, 'johanmc8703@gmail.com', '1d4029fa45ea83c4d73b2cbbcddeeca24cd517b6', '2024-07-03 01:42:03'),
(18, 'johanextradrive@gmail.com', 'ecf5db8e30770a33c6e78aa1ccd0fb456e7e79de', '2024-07-03 01:42:30'),
(19, 'johanextradrive@gmail.com', 'fba451410403d4a193fa9cc9c2c80fe46958867c', '2024-07-03 01:46:38'),
(22, 'johanextradrive@gmail.com', '60a1be638be387c8d32c45c584e8b845eeff10cc', '2024-07-03 14:22:28'),
(23, 'johanmc8703@gmail.com', '43b92fd0b1b3e79033d512219b9c533564f0ee33', '2024-07-03 14:26:23'),
(24, 'johanmc8703@gmail.com', '7e8c5f4b6cc9453cd968b7643080626bc49f3dad', '2024-07-03 14:28:12'),
(25, 'johanmc8703@gmail.com', '1a356d92fe665e8a3c2b6024896c786857787c38', '2024-07-03 16:20:09'),
(26, 'johanmc8703@gmail.com', '148f4bc71e3047493871aa5e5ccd7ca919527be4', '2024-07-03 16:20:09');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `data_akun`
--
ALTER TABLE `data_akun`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `log_absensi`
--
ALTER TABLE `log_absensi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lokasi`
--
ALTER TABLE `lokasi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lokasi_penugasan`
--
ALTER TABLE `lokasi_penugasan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pembagian_tugas`
--
ALTER TABLE `pembagian_tugas`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reset_tokens`
--
ALTER TABLE `reset_tokens`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `data_akun`
--
ALTER TABLE `data_akun`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `log_absensi`
--
ALTER TABLE `log_absensi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `lokasi`
--
ALTER TABLE `lokasi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `lokasi_penugasan`
--
ALTER TABLE `lokasi_penugasan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `pembagian_tugas`
--
ALTER TABLE `pembagian_tugas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `reset_tokens`
--
ALTER TABLE `reset_tokens`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
