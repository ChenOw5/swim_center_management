-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 27, 2025 at 06:40 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chark`
--

-- --------------------------------------------------------

--
-- Table structure for table `payment_data`
--

CREATE TABLE `payment_data` (
  `payment_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `payment_method` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `session_data`
--

CREATE TABLE `session_data` (
  `session_id` int(11) NOT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `session_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `session_pax` int(11) NOT NULL,
  `payment_id` int(11) DEFAULT NULL,
  `is_deleted` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `term_data`
--

CREATE TABLE `term_data` (
  `term_id` int(255) NOT NULL,
  `term` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `term_data`
--

INSERT INTO `term_data` (`term_id`, `term`) VALUES
(2, 'Operating Hours: Monday to Sunday, 8:00 – 22:00'),
(3, 'Rate per Hour: RM15.00 per Participant'),
(4, 'Payments Made Are Non-Refundable'),
(5, 'Session Booking Is Only Allowed For Future Hours'),
(6, 'Session Rescheduling Is Permitted Only with a Minimum of 6 Hours\' Notice'),
(7, 'Session Rescheduling Only Includes Postponing Sessions');

-- --------------------------------------------------------

--
-- Table structure for table `user_data`
--

CREATE TABLE `user_data` (
  `user_email` varchar(255) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_privilege` varchar(255) DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_data`
--

INSERT INTO `user_data` (`user_email`, `user_name`, `user_password`, `user_privilege`) VALUES
('admin@gmail.com', 'admin', 'a', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `payment_data`
--
ALTER TABLE `payment_data`
  ADD PRIMARY KEY (`payment_id`);

--
-- Indexes for table `session_data`
--
ALTER TABLE `session_data`
  ADD PRIMARY KEY (`session_id`),
  ADD KEY `user_email` (`user_email`),
  ADD KEY `fk_session_payment` (`payment_id`);

--
-- Indexes for table `term_data`
--
ALTER TABLE `term_data`
  ADD PRIMARY KEY (`term_id`);

--
-- Indexes for table `user_data`
--
ALTER TABLE `user_data`
  ADD PRIMARY KEY (`user_email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `payment_data`
--
ALTER TABLE `payment_data`
  MODIFY `payment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `session_data`
--
ALTER TABLE `session_data`
  MODIFY `session_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `term_data`
--
ALTER TABLE `term_data`
  MODIFY `term_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `session_data`
--
ALTER TABLE `session_data`
  ADD CONSTRAINT `fk_session_payment` FOREIGN KEY (`payment_id`) REFERENCES `payment_data` (`payment_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `session_data_ibfk_1` FOREIGN KEY (`user_email`) REFERENCES `user_data` (`user_email`),
  ADD CONSTRAINT `session_data_ibfk_2` FOREIGN KEY (`payment_id`) REFERENCES `payment_data` (`payment_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
