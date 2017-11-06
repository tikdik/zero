--liquibase formatted sql logicalFilePath:0001_init.sql
--changeset tikdik:201711061801

CREATE TABLE `zero` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB;
