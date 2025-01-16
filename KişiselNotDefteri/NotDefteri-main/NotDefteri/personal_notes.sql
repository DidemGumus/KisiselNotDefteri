-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 25 Ara 2024, 20:30:50
-- Sunucu sürümü: 10.4.32-MariaDB
-- PHP Sürümü: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `personal_notes`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `attachments`
--

CREATE TABLE `attachments` (
  `id` int(11) NOT NULL,
  `note_id` int(11) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `file_type` enum('image','audio','video','document') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `notes`
--

CREATE TABLE `notes` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `notes`
--

INSERT INTO `notes` (`id`, `user_id`, `title`, `content`, `status`, `category`, `created_at`, `updated_at`) VALUES
(4, 4, 'a', 'eded', 'Active', NULL, '2024-12-08 20:29:13', NULL),
(9, 4, 'gdscgdc', 'dcdcc', 'Active', NULL, '2024-12-09 13:55:48', NULL),
(13, 4, 'dad', 'ad', 'Active', NULL, '2024-12-14 23:07:50', NULL),
(19, 4, 'de', 'deneme', 'Active', NULL, '2024-12-14 23:32:56', NULL),
(20, 4, 'dad11', 'ad', 'Active', NULL, '2024-12-14 23:32:56', NULL),
(21, 4, 'kadira122', 'Didemi ç', 'Active', NULL, '2024-12-14 23:33:03', NULL),
(22, 4, '11', '11', 'Active', NULL, '2024-12-14 23:33:28', NULL),
(23, 4, 'kadir', 'Didemi ç', 'Active', NULL, '2024-12-14 23:33:45', NULL),
(24, 6, '11', '11', 'Tamamlandı', NULL, '2024-12-14 23:45:10', NULL),
(26, 6, 'sed', 'dede', 'Active', NULL, '2024-12-14 23:46:50', NULL),
(44, 8, 'mat sınav', '90', 'Tamamlandı', NULL, '2024-12-19 13:55:51', NULL),
(46, 8, 'deneme21', 'aa', 'Taslak', NULL, '2024-12-19 13:56:44', NULL),
(47, 11, 'alınacaklar', 'yumutra \nekmek', 'Taslak', NULL, '2024-12-19 14:05:52', NULL),
(49, 11, 'maaşom', '50000TL', 'Active', NULL, '2024-12-19 14:06:23', NULL),
(50, 12, 'matematik notlarım', '12', 'Taslak', NULL, '2024-12-19 14:38:03', NULL),
(51, 12, 'maaşım', '50000', 'Tamamlandı', NULL, '2024-12-19 14:38:27', NULL),
(53, 12, 'asd', 'asdasd', 'Active', NULL, '2024-12-19 14:38:59', NULL),
(70, 17, 'maaşım11', '50000tl', 'Tamamlandı', NULL, '2024-12-19 16:07:06', NULL),
(71, 17, 'ödevler', 'matematil', 'Taslak', NULL, '2024-12-19 16:07:20', NULL),
(74, 4, 'dad', 'ad', 'Active', NULL, '2024-12-19 19:08:19', NULL);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `note_tags`
--

CREATE TABLE `note_tags` (
  `note_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `note_tags`
--

INSERT INTO `note_tags` (`note_id`, `tag_id`) VALUES
(24, 31),
(70, 69),
(71, 70);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `message` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `is_read` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `reminders`
--

CREATE TABLE `reminders` (
  `id` int(11) NOT NULL,
  `note_id` int(11) NOT NULL,
  `reminder_time` datetime NOT NULL,
  `status` enum('pending','completed') DEFAULT 'pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tags`
--

CREATE TABLE `tags` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `tags`
--

INSERT INTO `tags` (`id`, `name`, `user_id`, `created_at`) VALUES
(31, '22', 6, '2024-12-14 23:45:03'),
(69, 'iş', 17, '2024-12-19 16:06:37'),
(70, 'okul1', 17, '2024-12-19 16:06:40');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `user_type` enum('user','admin') DEFAULT 'user',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `user_type`, `created_at`) VALUES
(4, 'didi', '123', 'didi@gmail.com', 'user', '2024-12-08 20:27:51'),
(5, 'as', '1414', 'as@gmail.com', 'user', '2024-12-09 14:10:42'),
(6, 'de', 'de', 'a', 'user', '2024-12-14 23:44:43'),
(8, 'kadir', '123', 'kadir1@gmail.com', 'user', '2024-12-19 13:52:57'),
(9, 'kadir123', '123', '1ae@gmail.com', 'user', '2024-12-19 14:00:55'),
(11, 'deneme1', '123', 'deneme1@gmail.com', 'user', '2024-12-19 14:05:00'),
(12, 'betül2', '123', 'be@gmail.com', 'user', '2024-12-19 14:36:58'),
(17, 'betül', '123', 'adsdas@gmail.com', 'user', '2024-12-19 16:06:16');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `attachments`
--
ALTER TABLE `attachments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `note_id` (`note_id`);

--
-- Tablo için indeksler `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Tablo için indeksler `note_tags`
--
ALTER TABLE `note_tags`
  ADD PRIMARY KEY (`note_id`,`tag_id`),
  ADD KEY `tag_id` (`tag_id`);

--
-- Tablo için indeksler `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Tablo için indeksler `reminders`
--
ALTER TABLE `reminders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `note_id` (`note_id`);

--
-- Tablo için indeksler `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Tablo için indeksler `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `attachments`
--
ALTER TABLE `attachments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Tablo için AUTO_INCREMENT değeri `notes`
--
ALTER TABLE `notes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- Tablo için AUTO_INCREMENT değeri `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Tablo için AUTO_INCREMENT değeri `reminders`
--
ALTER TABLE `reminders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Tablo için AUTO_INCREMENT değeri `tags`
--
ALTER TABLE `tags`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;

--
-- Tablo için AUTO_INCREMENT değeri `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

--
-- Tablo kısıtlamaları `attachments`
--
ALTER TABLE `attachments`
  ADD CONSTRAINT `attachments_ibfk_1` FOREIGN KEY (`note_id`) REFERENCES `notes` (`id`);

--
-- Tablo kısıtlamaları `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Tablo kısıtlamaları `note_tags`
--
ALTER TABLE `note_tags`
  ADD CONSTRAINT `note_tags_ibfk_1` FOREIGN KEY (`note_id`) REFERENCES `notes` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `note_tags_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE;

--
-- Tablo kısıtlamaları `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Tablo kısıtlamaları `reminders`
--
ALTER TABLE `reminders`
  ADD CONSTRAINT `reminders_ibfk_1` FOREIGN KEY (`note_id`) REFERENCES `notes` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
