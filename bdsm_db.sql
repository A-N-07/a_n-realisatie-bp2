-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 05, 2026 at 09:10 PM
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
-- Database: `bdsm_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `id` int(10) NOT NULL,
  `title` varchar(100) NOT NULL,
  `isbn_number` varchar(13) NOT NULL,
  `cover_location_id` int(10) DEFAULT NULL,
  `special_feature` varchar(200) DEFAULT NULL,
  `page_number` int(7) NOT NULL,
  `favourite` tinyint(1) NOT NULL DEFAULT 0,
  `book_location_id` int(10) DEFAULT NULL,
  `file_type_id` int(10) DEFAULT NULL,
  `book_status_id` int(10) DEFAULT NULL,
  `cover_type_id` int(10) DEFAULT NULL,
  `book_type_id` int(10) DEFAULT NULL,
  `author_id` int(10) DEFAULT NULL,
  `narrator_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`id`, `title`, `isbn_number`, `cover_location_id`, `special_feature`, `page_number`, `favourite`, `book_location_id`, `file_type_id`, `book_status_id`, `cover_type_id`, `book_type_id`, `author_id`, `narrator_id`) VALUES
(9, 'moby dick', '1234567891011', 3, '', 0, 1, 4, NULL, 2, NULL, NULL, 4, NULL),
(10, 'Hemmingway', '1234567892012', 3, '', 0, 0, 4, NULL, 4, NULL, NULL, 5, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `book_location`
--

CREATE TABLE `book_location` (
  `id` int(10) NOT NULL,
  `value` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book_location`
--

INSERT INTO `book_location` (`id`, `value`) VALUES
(2, 'C:\\Users\\User_2\\Desktop'),
(3, 'c:\\'),
(4, 'c://');

-- --------------------------------------------------------

--
-- Table structure for table `book_status`
--

CREATE TABLE `book_status` (
  `id` int(10) NOT NULL,
  `value` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book_status`
--

INSERT INTO `book_status` (`id`, `value`) VALUES
(1, 'READ'),
(2, 'READING'),
(3, 'STARTED_READING'),
(4, 'UNREAD');

-- --------------------------------------------------------

--
-- Table structure for table `book_type`
--

CREATE TABLE `book_type` (
  `id` int(10) NOT NULL,
  `value` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book_type`
--

INSERT INTO `book_type` (`id`, `value`) VALUES
(9, 'E_BOOK'),
(10, 'AUDIO_BOOK'),
(11, 'PHYSICAL_BOOK'),
(12, 'LUXURY_BOOK');

-- --------------------------------------------------------

--
-- Table structure for table `cover_location`
--

CREATE TABLE `cover_location` (
  `id` int(10) NOT NULL,
  `value` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cover_location`
--

INSERT INTO `cover_location` (`id`, `value`) VALUES
(2, 'C:\\Users\\User_2\\Desktop'),
(3, '');

-- --------------------------------------------------------

--
-- Table structure for table `cover_type`
--

CREATE TABLE `cover_type` (
  `id` int(10) NOT NULL,
  `value` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cover_type`
--

INSERT INTO `cover_type` (`id`, `value`) VALUES
(1, 'HARDCOVER'),
(2, 'SOFTCOVER');

-- --------------------------------------------------------

--
-- Table structure for table `file_type`
--

CREATE TABLE `file_type` (
  `id` int(10) NOT NULL,
  `value` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `file_type`
--

INSERT INTO `file_type` (`id`, `value`) VALUES
(1, 'PDF'),
(2, 'EPUB'),
(3, 'MOBI'),
(4, 'TXT'),
(5, 'MP3'),
(6, 'M4B');

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `id` int(10) NOT NULL,
  `name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `name`) VALUES
(1, 'John Sjaak'),
(2, 'Yur Mom'),
(3, 'Gretchen Felker-Martin'),
(4, 'johan'),
(5, 'Shakespeare');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_isbn` (`isbn_number`),
  ADD KEY `book_location_id` (`book_location_id`),
  ADD KEY `file_type_id` (`file_type_id`,`book_status_id`,`cover_type_id`,`book_type_id`),
  ADD KEY `fk_book__book_status_id__book_status` (`book_status_id`),
  ADD KEY `fk_book__book_type_id__book_type` (`book_type_id`),
  ADD KEY `fk_book__cover_type_id__cover_type` (`cover_type_id`),
  ADD KEY `fk_book__author_id__person` (`author_id`),
  ADD KEY `fk_book__narrator_id__person` (`narrator_id`),
  ADD KEY `fk_book__cover_location_id__cover_location` (`cover_location_id`);

--
-- Indexes for table `book_location`
--
ALTER TABLE `book_location`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `book_status`
--
ALTER TABLE `book_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `book_type`
--
ALTER TABLE `book_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cover_location`
--
ALTER TABLE `cover_location`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cover_type`
--
ALTER TABLE `cover_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `file_type`
--
ALTER TABLE `file_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `book_location`
--
ALTER TABLE `book_location`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `book_status`
--
ALTER TABLE `book_status`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `book_type`
--
ALTER TABLE `book_type`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `cover_location`
--
ALTER TABLE `cover_location`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `cover_type`
--
ALTER TABLE `cover_type`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `file_type`
--
ALTER TABLE `file_type`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `fk_book__author_id__person` FOREIGN KEY (`author_id`) REFERENCES `person` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_book__book_location_id__book_location` FOREIGN KEY (`book_location_id`) REFERENCES `book_location` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_book__book_status_id__book_status` FOREIGN KEY (`book_status_id`) REFERENCES `book_status` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_book__book_type_id__book_type` FOREIGN KEY (`book_type_id`) REFERENCES `book_type` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_book__cover_location_id__cover_location` FOREIGN KEY (`cover_location_id`) REFERENCES `cover_location` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_book__cover_type_id__cover_type` FOREIGN KEY (`cover_type_id`) REFERENCES `cover_type` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_book__file_type_id__file_type` FOREIGN KEY (`file_type_id`) REFERENCES `file_type` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_book__narrator_id__person` FOREIGN KEY (`narrator_id`) REFERENCES `person` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
