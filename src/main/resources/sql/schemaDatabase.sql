CREATE TABLE `USERS` (
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `email` varchar(255),
    `name` varchar(255),
    `password` varchar(255),
    `created_at` timestamp,
    `updated_at` timestamp,
    `role` varchar(255),
    `active` boolean
);

CREATE TABLE `RENTALS` (
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255),
    `surface` numeric,
    `price` numeric,
    `picture` text,
    `description` text,
    `owner_id` integer NOT NULL,
    `created_at` timestamp,
    `updated_at` timestamp
);

CREATE TABLE `MESSAGES` (
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `rental_id` integer,
    `user_id` integer,
    `message` text,
    `created_at` timestamp,
    `updated_at` timestamp
);

CREATE TABLE `TOKENS` (
    `id` integer PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `token` text,
    `user_id` integer,
    `token_type` text NOT NULL,
    `expired` boolean NOT NULL DEFAULT false,
    `created_at` timestamp,
    `valid` boolean
);

CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);

ALTER TABLE `RENTALS` ADD FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`);

ALTER TABLE `TOKENS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
