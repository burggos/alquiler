-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-11-2025 a las 00:40:32
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `rueda_libre`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alquiler`
--

CREATE TABLE `alquiler` (
  `id_alquiler` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_vehiculo` int(11) NOT NULL,
  `duracion_semanas` int(11) NOT NULL,
  `costo_total` decimal(10,2) NOT NULL,
  `estado` enum('Activo','Vencido','Finalizado') DEFAULT 'Activo',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `alquiler`
--

INSERT INTO `alquiler` (`id_alquiler`, `id_cliente`, `id_vehiculo`, `duracion_semanas`, `costo_total`, `estado`, `created_at`, `updated_at`) VALUES
(6, 5, 8, 1, 1330000.00, 'Finalizado', '2025-11-17 19:50:24', '2025-11-17 19:51:25'),
(7, 5, 9, 3, 2310000.00, 'Finalizado', '2025-11-17 19:52:12', '2025-11-17 19:52:40'),
(8, 4, 7, 1, 560000.00, 'Finalizado', '2025-11-17 22:58:13', '2025-11-17 22:58:45');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL,
  `cc` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `numero_licencia` varchar(50) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `cc`, `nombre`, `apellido`, `direccion`, `numero_licencia`, `telefono`, `created_at`, `updated_at`) VALUES
(1, '1025896741', 'Carlos', 'Ramírez', 'Cra 10 #12-45', 'LIC45812', '3104567890', '2025-11-15 19:45:10', '2025-11-15 19:45:10'),
(2, '1032456987', 'María', 'Gómez', 'Cll 8 #20-33', 'LIC87456', '3147859623', '2025-11-15 19:45:10', '2025-11-15 19:45:10'),
(3, '1015879623', 'Andrés', 'López', 'Cra 25 #15-20', 'LIC12578', '3204569874', '2025-11-15 19:45:10', '2025-11-15 19:45:10'),
(4, '1002458963', 'Sofía', 'Martínez', 'Cll 45 #12-11', 'LIC36985', '3114785963', '2025-11-15 19:45:10', '2025-11-15 19:45:10'),
(5, '1023654789', 'Julián', 'Pérez', 'Cra 78 #32-12', 'LIC47852', '3004589632', '2025-11-15 19:45:10', '2025-11-15 19:45:10'),
(6, '1014587962', 'Paula', 'Rojas', 'Cll 36 #17-89', 'LIC96587', '3156987451', '2025-11-15 19:45:10', '2025-11-15 19:45:10'),
(7, '1036987451', 'Felipe', 'Hernández', 'Cra 40 #25-10', 'LIC74512', '3025897412', '2025-11-15 19:45:10', '2025-11-15 19:45:10'),
(8, '1009874563', 'Diana', 'Castro', 'Cll 98 #34-56', 'LIC84521', '3168957420', '2025-11-15 19:45:10', '2025-11-15 19:45:10'),
(9, '1025897412', 'Camilo', 'Torres', 'Cra 15 #14-52', 'LIC12547', '3124789654', '2025-11-15 19:45:10', '2025-11-15 19:45:10'),
(10, '1012365987', 'Laura', 'Moreno', 'Cll 5 #18-09', 'LIC95841', '3196587412', '2025-11-15 19:45:10', '2025-11-15 19:45:10');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dano`
--

CREATE TABLE `dano` (
  `id_dano` int(11) NOT NULL,
  `id_devolucion` int(11) NOT NULL,
  `descripcion` text NOT NULL,
  `costo_reparacion` decimal(10,2) DEFAULT 0.00,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `dano`
--

INSERT INTO `dano` (`id_dano`, `id_devolucion`, `descripcion`, `costo_reparacion`, `created_at`) VALUES
(3, 10, 'daños leves en el parachoques trasero', 100000.00, '2025-11-17 23:13:24');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `devolucion`
--

CREATE TABLE `devolucion` (
  `id_devolucion` int(11) NOT NULL,
  `id_alquiler` int(11) NOT NULL,
  `fecha_devolucion` date NOT NULL,
  `observaciones` text DEFAULT NULL,
  `estado_vehiculo` enum('Bueno','Con Daños','Requiere Mantenimiento') DEFAULT 'Bueno',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `devolucion`
--

INSERT INTO `devolucion` (`id_devolucion`, `id_alquiler`, `fecha_devolucion`, `observaciones`, `estado_vehiculo`, `created_at`) VALUES
(9, 6, '2025-11-17', 'EN PERFECTO ESTADO', 'Bueno', '2025-11-17 19:51:25'),
(10, 7, '2025-11-17', 'DAÑOS EN LA PARTE TRASERA', 'Bueno', '2025-11-17 19:52:40'),
(11, 8, '2025-11-17', 'daños en el parachoque', 'Con Daños', '2025-11-17 22:58:45');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mantenimiento`
--

CREATE TABLE `mantenimiento` (
  `id_mantenimiento` int(11) NOT NULL,
  `id_vehiculo` int(11) NOT NULL,
  `id_devolucion` int(11) DEFAULT NULL,
  `fecha` date NOT NULL,
  `descripcion` text DEFAULT NULL,
  `costo` decimal(10,2) DEFAULT 0.00,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mantenimiento`
--

INSERT INTO `mantenimiento` (`id_mantenimiento`, `id_vehiculo`, `id_devolucion`, `fecha`, `descripcion`, `costo`, `created_at`) VALUES
(4, 9, 10, '2025-11-17', 'En perfecto estado En perfecto estado', 100000.00, '2025-11-17 23:35:29'),
(5, 9, 10, '2025-11-17', 'En perfecto estado En perfecto estado', 100000.00, '2025-11-17 23:36:53'),
(6, 9, 10, '2025-11-17', 'en perfecto estado en perfecto estado', 100000.00, '2025-11-17 23:37:55');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

CREATE TABLE `vehiculo` (
  `id_vehiculo` int(11) NOT NULL,
  `placa` varchar(15) NOT NULL,
  `marca` varchar(50) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `anio` year(4) NOT NULL,
  `tipo` enum('Sedan','SUV','Camioneta','Pickup','Deportivo') NOT NULL,
  `costo_por_dia` decimal(10,2) NOT NULL,
  `estado` enum('Disponible','Alquilado','Mantenimiento','Inactivo') DEFAULT 'Disponible',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`id_vehiculo`, `placa`, `marca`, `modelo`, `anio`, `tipo`, `costo_por_dia`, `estado`, `created_at`, `updated_at`) VALUES
(1, 'ABC123', 'Chevrolet', 'Onix', '2018', 'Sedan', 120000.00, 'Mantenimiento', '2025-11-15 19:46:08', '2025-11-17 18:49:58'),
(2, 'JKL456', 'Mazda', 'CX-5', '2020', 'SUV', 180000.00, 'Disponible', '2025-11-15 19:46:08', '2025-11-15 19:46:08'),
(3, 'QWE789', 'Renault', 'Logan', '2017', 'Sedan', 90000.00, 'Disponible', '2025-11-15 19:46:08', '2025-11-17 19:51:39'),
(4, 'RTY258', 'Kia', 'Sportage', '2021', 'SUV', 175000.00, 'Disponible', '2025-11-15 19:46:08', '2025-11-15 19:46:08'),
(5, 'UIO369', 'Hyundai', 'Tucson', '2019', 'SUV', 165000.00, 'Disponible', '2025-11-15 19:46:08', '2025-11-15 19:46:08'),
(6, 'ASD147', 'Chevrolet', 'Spark GT', '2016', 'Sedan', 85000.00, 'Disponible', '2025-11-15 19:46:08', '2025-11-17 19:51:46'),
(7, 'ZXC159', 'Volkswagen', 'Gol', '2015', 'Sedan', 80000.00, 'Mantenimiento', '2025-11-15 19:46:08', '2025-11-17 22:58:45'),
(8, 'BNM753', 'Toyota', 'Corolla', '2022', 'Sedan', 190000.00, 'Disponible', '2025-11-15 19:46:08', '2025-11-17 19:51:25'),
(9, 'PLM951', 'Nissan', 'Versa', '2019', 'Sedan', 110000.00, 'Disponible', '2025-11-15 19:46:08', '2025-11-17 23:35:29'),
(10, 'GHJ852', 'Ford', 'Raptor', '2021', 'Camioneta', 250000.00, 'Disponible', '2025-11-15 19:46:08', '2025-11-15 19:46:08');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alquiler`
--
ALTER TABLE `alquiler`
  ADD PRIMARY KEY (`id_alquiler`),
  ADD KEY `id_cliente` (`id_cliente`),
  ADD KEY `id_vehiculo` (`id_vehiculo`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `cc` (`cc`),
  ADD UNIQUE KEY `numero_licencia` (`numero_licencia`);

--
-- Indices de la tabla `dano`
--
ALTER TABLE `dano`
  ADD PRIMARY KEY (`id_dano`),
  ADD KEY `id_devolucion` (`id_devolucion`);

--
-- Indices de la tabla `devolucion`
--
ALTER TABLE `devolucion`
  ADD PRIMARY KEY (`id_devolucion`),
  ADD UNIQUE KEY `id_alquiler` (`id_alquiler`);

--
-- Indices de la tabla `mantenimiento`
--
ALTER TABLE `mantenimiento`
  ADD PRIMARY KEY (`id_mantenimiento`),
  ADD KEY `id_vehiculo` (`id_vehiculo`),
  ADD KEY `id_devolucion` (`id_devolucion`);

--
-- Indices de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD PRIMARY KEY (`id_vehiculo`),
  ADD UNIQUE KEY `placa` (`placa`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alquiler`
--
ALTER TABLE `alquiler`
  MODIFY `id_alquiler` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `dano`
--
ALTER TABLE `dano`
  MODIFY `id_dano` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `devolucion`
--
ALTER TABLE `devolucion`
  MODIFY `id_devolucion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `mantenimiento`
--
ALTER TABLE `mantenimiento`
  MODIFY `id_mantenimiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  MODIFY `id_vehiculo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alquiler`
--
ALTER TABLE `alquiler`
  ADD CONSTRAINT `alquiler_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`),
  ADD CONSTRAINT `alquiler_ibfk_2` FOREIGN KEY (`id_vehiculo`) REFERENCES `vehiculo` (`id_vehiculo`);

--
-- Filtros para la tabla `dano`
--
ALTER TABLE `dano`
  ADD CONSTRAINT `dano_ibfk_1` FOREIGN KEY (`id_devolucion`) REFERENCES `devolucion` (`id_devolucion`) ON DELETE CASCADE;

--
-- Filtros para la tabla `devolucion`
--
ALTER TABLE `devolucion`
  ADD CONSTRAINT `devolucion_ibfk_1` FOREIGN KEY (`id_alquiler`) REFERENCES `alquiler` (`id_alquiler`) ON DELETE CASCADE;

--
-- Filtros para la tabla `mantenimiento`
--
ALTER TABLE `mantenimiento`
  ADD CONSTRAINT `mantenimiento_ibfk_1` FOREIGN KEY (`id_vehiculo`) REFERENCES `vehiculo` (`id_vehiculo`) ON DELETE CASCADE,
  ADD CONSTRAINT `mantenimiento_ibfk_2` FOREIGN KEY (`id_devolucion`) REFERENCES `devolucion` (`id_devolucion`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
