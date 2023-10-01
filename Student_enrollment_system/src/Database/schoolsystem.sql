-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 01, 2023 at 06:16 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `schoolsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `activity` varchar(20) NOT NULL,
  `date` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reports`
--

INSERT INTO `reports` (`id`, `username`, `activity`, `date`) VALUES
(1, 'Meow', 'Login', '2023-09-17'),
(2, 'Meow', 'Login', '2023-09-17'),
(3, 'Meow', 'Login', '2023-09-17'),
(4, 'WenDEVLIFE', 'Login', '2023-09-18'),
(5, 'Meow', 'Login', '2023-09-19'),
(6, 'Meow', 'Login', '2023-09-19'),
(7, 'WenDEVLIFE', 'Login', '2023-09-19'),
(8, 'WenDEVLIFE', 'Login', '2023-09-19'),
(9, 'WenDEVLIFE', 'Login', '2023-09-20'),
(10, 'WenDEVLIFE', 'Login', '2023-09-24'),
(11, 'WenDEVLIFE', 'Login', '2023-09-24'),
(12, 'WenDEVLIFE', 'Login', '2023-09-24'),
(13, 'WenDEVLIFE', 'Login', '2023-09-24'),
(14, 'WenDEVLIFE', 'Login', '2023-09-25'),
(15, 'WenDEVLIFE', 'Login', '2023-09-26'),
(16, 'WenDEVLIFE', 'Login', '2023-09-26'),
(17, 'WenDEVLIFE', 'Login', '2023-09-26'),
(18, 'WenDEVLIFE', 'Login', '2023-09-26'),
(19, 'WenDEVLIFE', 'Login', '2023-09-26'),
(20, 'WENDEVLIFE', 'Login', '2023-09-26'),
(21, 'WenDEVLIFE', 'Login', '2023-09-26'),
(22, 'WENDEVLIFE', 'Login', '2023-09-26'),
(23, 'WenDEVLIFE', 'Login', '2023-09-26'),
(24, 'WenDEVLIFE', 'Login', '2023-09-26'),
(25, 'WenDEVLIFE', 'Login', '2023-09-27'),
(26, 'WenDEVLIFE', 'Enrolled a student', '2023-09-27'),
(27, 'Wendev', 'Enrolled a student', '2023-09-27'),
(28, 'WenDEVLIFE', 'Login', '2023-09-27'),
(29, 'Anna Krustikev', 'Enrolled a student', '2023-09-27'),
(30, 'WenDEVLIFE', 'Login', '2023-09-27'),
(31, 'Anna', 'Login', '2023-09-27'),
(32, 'Johnny', 'Login', '2023-09-27'),
(35, 'Johnny', 'Login', '2023-09-28'),
(36, 'Johnny', 'Enrolled a student', '2023-09-28'),
(37, 'Johnny', 'Login', '2023-09-28'),
(38, 'Johnny', 'Enrolled a student', '2023-09-28'),
(39, 'Johnny', 'Login', '2023-09-28'),
(40, 'Johnny', 'Enrolled a student', '2023-09-28'),
(41, 'WenDEVLIFE', 'Login', '2023-09-28'),
(42, 'WenDEVLIFE', 'Enrolled a student', '2023-09-28'),
(43, 'WenDEVLIFE', 'Login', '2023-09-28'),
(44, 'WenDEVLIFE', 'Login', '2023-09-29'),
(45, 'WenDEVLIFE', 'Login', '2023-09-29'),
(46, 'WenDEVLIFE', 'Login', '2023-09-29'),
(47, 'WenDEVLIFE', 'Login', '2023-09-29'),
(48, 'WenDEVLIFE', 'Login', '2023-09-29'),
(49, 'WenDEVLIFE', 'Login', '2023-09-29'),
(50, 'WenDEVLIFE', 'Login', '2023-09-29'),
(51, 'WenDEVLIFE', 'Login', '2023-09-29'),
(52, 'Johnny', 'Login', '2023-09-29'),
(53, 'WenDEV', 'Login', '2023-09-29'),
(54, 'WenDEV', 'Login', '2023-09-29'),
(55, 'WenDEV', 'Login', '2023-09-29'),
(56, 'WenDEV', 'Login', '2023-09-29'),
(57, 'WenDEV', 'Login', '2023-09-29'),
(58, 'WenDEV', 'Login', '2023-09-29'),
(59, 'Johnny', 'Login', '2023-09-29'),
(60, 'WenDEV', 'Login', '2023-09-29'),
(61, 'WenDEV', 'Login', '2023-09-29'),
(62, 'WenDEV', 'Login', '2023-09-29'),
(63, 'WenDEV', 'Login', '2023-09-29'),
(64, 'WenDEV', 'Login', '2023-09-29'),
(65, 'WenDEV', 'Login', '2023-09-29'),
(66, 'WenDEV', 'Login', '2023-09-29'),
(67, 'WenDEV', 'Login', '2023-09-29'),
(68, 'Testing90', 'Login', '2023-09-30'),
(69, 'Testing90', 'Enrolled a student', '2023-09-30'),
(70, 'Testing90', 'Login', '2023-09-30'),
(71, 'Testing90', 'Login', '2023-09-30'),
(72, 'Testing90', 'Login', '2023-09-30'),
(73, 'Testing78', 'Login', '2023-09-30'),
(74, 'Testing89', 'Login', '2023-09-30'),
(75, 'Testing89', 'Login', '2023-09-30'),
(76, 'Testing900', 'Login', '2023-09-30'),
(77, 'Testing1000', 'Login', '2023-09-30'),
(78, 'Testing1000', 'Enrolled a student', '2023-09-30'),
(79, 'Testing640', 'Login', '2023-09-30'),
(80, 'Testing640', 'Login', '2023-10-01'),
(81, 'Testing640', 'Login', '2023-10-01'),
(82, 'Testing640', 'Login', '2023-10-01'),
(83, 'Testing640', 'Login', '2023-10-01'),
(84, 'WenDEV', 'Login', '2023-10-01'),
(85, 'WenDEV', 'Login', '2023-10-01'),
(86, 'Wenwen', 'Login', '2023-10-01'),
(87, 'Wenwen', 'Login', '2023-10-01'),
(88, 'Wenwen', 'Login', '2023-10-01'),
(89, 'Wenwen', 'Login', '2023-10-01'),
(90, 'Wenwen', 'Login', '2023-10-01'),
(91, 'Wenwen', 'Login', '2023-10-01'),
(92, 'Wenwen', 'Login', '2023-10-01'),
(93, 'Wenwen', 'Login', '2023-10-01'),
(94, 'Wenwen', 'Login', '2023-10-01'),
(95, 'WenDEV', 'Login', '2023-10-01'),
(96, 'Wenwen', 'Login', '2023-10-01'),
(97, 'Wenwen', 'Login', '2023-10-01'),
(98, 'WenDEVLIFE', 'Login', '2023-10-01'),
(99, 'WenDEVLIFE', 'Login', '2023-10-01'),
(100, 'WenDEVLIFE', 'Enrolled a student', '2023-10-01'),
(101, 'WenDEVLIFE', 'Login', '2023-10-01'),
(102, 'Meow', 'Login', '2023-10-01'),
(103, 'Meow', 'Enrolled a student', '2023-10-01'),
(104, 'ImACat', 'Login', '2023-10-01'),
(105, 'WenDEVLIFE', 'Login', '2023-10-01'),
(106, 'WenDEVLIFE', 'Login', '2023-10-01'),
(107, 'WenDEVLIFE', 'Login', '2023-10-02'),
(108, 'ANNA8', 'Login', '2023-10-02'),
(109, 'ANNA8', 'Enrolled a student', '2023-10-02'),
(110, 'WenDEVLIFE', 'Login', '2023-10-02'),
(111, 'WenDEVLIFE', 'Login', '2023-10-02'),
(112, 'WenDEVLIFE', 'Login', '2023-10-02'),
(113, 'WenDEVLIFE', 'Login', '2023-10-02');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `studentid` int(20) NOT NULL,
  `studentname` varchar(255) NOT NULL,
  `Grade` varchar(255) NOT NULL,
  `age` varchar(3) NOT NULL,
  `phonenumber` varchar(12) NOT NULL,
  `address` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`studentid`, `studentname`, `Grade`, `age`, `phonenumber`, `address`) VALUES
(2, 'Frouen M. Medina Jr.', '12 - ICT ', '20', '09912093870', 'Matina Crossing'),
(3, 'Justin Guibao', '12 - ICT ', '20', '343434343', 'Calinan , Davao City'),
(4, 'Glenn', '12 - ICT ', '20', '987867867', 'Matina Pangi'),
(5, 'Glenn Joy Ramas', '12 - ICT ', '20', '86765767', 'Matina Pangi'),
(6, 'Kev Carriedo', '12 - ICT ', '20', '09432323232', 'Buhangin,Davao City'),
(7, 'John Carl Banate', '12 - ICT ', '20', '756465656', 'Matina Bangkal'),
(8, 'meow', '11 - STEM', '18', '432434343', 'matina'),
(9, 'Wendev', '11 - ICT ', '10', '7656454545', 'matina crossing'),
(10, 'Anna Krustikev', '12 - ICT ', '20', '099134534356', 'Moscow , Russia'),
(11, 'Jonymar Barrate', '12 - ICT ', '21', '09432323434', 'Mintal'),
(12, 'Johnny Bravo', '12 - ARTS DESIGN', '25', '8909435232', 'Washington, USA'),
(13, 'Janrenz Ediable', '12 - ABM', '19', '096765656657', 'Maa, Davao City'),
(14, 'Wen wen', '12 - ICT ', '20', '0943562324', 'Matina Crossing'),
(15, 'Vladimir Kevin', '12 - HUMMS', '22', '8905645645', 'Novibrisk , Russia'),
(16, 'Meow meow', '12 - STEM', '23', '967894535', 'Matina Pangi , Davao City'),
(17, 'Ivan Kuritoksi', '7 - mango', '17', '45678907596', 'Moscow , USSR'),
(18, 'Adolf Ivan', '10 - Ecoland', '17', '0876757566', 'Berlin , Germany');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` binary(16) DEFAULT NULL,
  `Role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`id`, `username`, `password`, `salt`, `Role`) VALUES
(1, 'meow125', '137e1b769c60ae666f2179a95046466d78b7bf8f6dbbc53b4b8e5825e759eaf8', 0x373c5e9dbea76c0322c6aa8b80d09543, 'Teacher'),
(2, 'ImACat', 'd7a06f8267c4810556b95bf261949a882d78492543a85648501d73b903b2f255', 0x6cd89b450db247910263f4616fec6bef, 'Teacher'),
(5, 'Hello', '968eb1babc5c89d942b8fe7d684f889d96135dcb63b080d0875ed79801d97d69', 0x5c7d203214a18f2da3622b44481ad6bd, 'Teacher'),
(6, 'Anna', 'b5ca27b068d0879e270209a884a703c5d3a840c6e9f9bd1b23e1e9fff9f2148a', 0xd8f1791dfc1d7c973160e768b5d3cc72, 'Teacher'),
(7, 'John', 'd0c595e2ef2f583128fb39eab4590334936a5258eed6b079a5a74b4bb6d3ef5e', 0x7b849d898643623bc774452659a0c182, 'Teacher'),
(9, 'Wenwen', '66129f6af1a7960b922c7aec23d4a58bfbcc80d7bf0efa35bf39eb28d3a646c9', 0xe2bbcbdf05aaf30cc05b63a1c91b7f83, 'Admin'),
(10, 'WenDEVLIFE', '893913f30416a42595109994413b646b63dd723daa9d621456a00f7a754dea86', 0x8b1cd369022d490024c8fe96b74e1a4f, 'Admin'),
(11, 'Wenwen90', '415ba763dd6c0b1d86597644e79f6cf3f63ff80d8e27533b3052a9493566b10b', 0xf332d22f51eb6e575425df2bd78fe1bb, 'Admin'),
(12, 'Wenwen900', '0b37603812c9f30747751d717c01cfe6fc55d9009f445ce611271cfe489fc1d0', 0xd89fde00ab951d60b2116e6c903ebcd8, 'Admin'),
(13, 'Bravo1', '350318c490bd18d857faf868acf7b2a879ada71325ff161c41862f3109bb0f0c', 0x83bcedf76ccb0fc926073414d43a9c62, 'Admin'),
(14, 'Anna1', '76a970177219acbc5c3e7daff12ce86490536e41055909def7c8e251fcba4553', 0x46684a4c7af1a638a13f88c3ee3d44cc, 'Admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`studentid`,`age`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `reports`
--
ALTER TABLE `reports`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=114;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `studentid` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `teacher`
--
ALTER TABLE `teacher`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
