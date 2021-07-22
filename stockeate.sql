-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-07-2021 a las 20:59:47
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 7.4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `stockeate`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `idcategoria` int(11) NOT NULL,
  `nombre_categoria` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`idcategoria`, `nombre_categoria`) VALUES
(1, 'General'),
(2, 'Tubérculos'),
(3, 'Frutas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientos`
--

CREATE TABLE `movimientos` (
  `id_movimiento` int(11) NOT NULL,
  `id_prod` int(11) NOT NULL,
  `id_tipo_movimiento` int(11) NOT NULL,
  `cantidad` double NOT NULL DEFAULT 0,
  `fecha_movimiento` datetime NOT NULL DEFAULT current_timestamp(),
  `idusuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `movimientos`
--

INSERT INTO `movimientos` (`id_movimiento`, `id_prod`, `id_tipo_movimiento`, `cantidad`, `fecha_movimiento`, `idusuario`) VALUES
(1, 1, 1, 0, '2021-06-20 20:05:21', 1),
(2, 2, 1, 0, '2021-06-20 20:08:17', 1),
(3, 3, 1, 0, '2021-06-20 20:08:17', 1),
(4, 4, 1, 0, '2021-06-20 20:08:17', 1),
(5, 5, 1, 0, '2021-06-20 20:08:17', 1),
(6, 6, 1, 0, '2021-06-20 20:08:17', 1),
(7, 7, 1, 0, '2021-06-20 20:08:17', 1),
(42, 7, 2, 1000, '2021-07-21 03:08:22', 1),
(43, 7, 2, 500, '2021-07-21 03:09:30', 1),
(44, 7, 2, 300, '2021-07-21 03:12:37', 1),
(45, 7, 2, 200, '2021-07-21 03:13:39', 1),
(46, 7, 2, 100, '2021-07-21 03:14:52', 1),
(47, 7, 2, 200, '2021-07-21 03:15:30', 1),
(48, 7, 2, 100, '2021-07-21 03:16:45', 1),
(49, 7, 2, 100, '2021-07-21 03:18:59', 1),
(50, 7, 3, 200, '2021-07-21 03:22:32', 1),
(51, 7, 2, 200, '2021-07-21 03:25:21', 1),
(52, 7, 2, 100, '2021-07-21 03:30:51', 1),
(53, 7, 2, 100, '2021-07-21 03:32:08', 1),
(54, 7, 2, 100, '2021-07-21 03:35:33', 1),
(55, 7, 2, 100, '2021-07-21 03:37:09', 1),
(56, 7, 3, 300, '2021-07-21 03:37:23', 1),
(57, 7, 5, 0, '2021-07-21 03:38:55', 1),
(58, 7, 3, 400, '2021-07-21 03:41:53', 1),
(59, 7, 3, 1500, '2021-07-21 16:32:24', 1),
(60, 7, 3, 100, '2021-07-21 16:32:35', 1),
(61, 7, 3, 50, '2021-07-21 16:32:52', 1),
(62, 7, 2, 50, '2021-07-21 16:40:34', 1),
(63, 7, 3, 50, '2021-07-21 16:40:43', 1),
(64, 7, 2, 300, '2021-07-21 16:41:58', 1),
(65, 4, 5, 0, '2021-07-21 16:42:46', 1),
(66, 7, 3, 400, '2021-07-21 16:43:03', 1),
(67, 7, 2, 200, '2021-07-21 16:57:20', 1),
(68, 7, 3, 50, '2021-07-21 18:37:59', 1),
(69, 7, 3, 1, '2021-07-21 18:38:16', 1),
(70, 7, 5, 0, '2021-07-21 18:38:39', 1),
(71, 7, 5, 0, '2021-07-21 18:38:58', 1),
(72, 4, 2, 1000, '2021-07-21 19:12:50', 2),
(73, 1, 5, 0, '2021-07-21 19:13:16', 2),
(74, 1, 5, 0, '2021-07-21 19:13:32', 2),
(75, 1, 5, 0, '2021-07-21 19:13:44', 2),
(76, 1, 5, 0, '2021-07-21 19:15:11', 2),
(77, 1, 5, 0, '2021-07-21 19:15:20', 2),
(78, 1, 5, 0, '2021-07-21 19:16:02', 2),
(79, 1, 5, 0, '2021-07-21 19:16:09', 2),
(80, 1, 5, 0, '2021-07-21 19:25:49', 2),
(81, 1, 5, 0, '2021-07-21 19:25:56', 2),
(82, 1, 5, 0, '2021-07-21 19:26:56', 2),
(83, 1, 5, 0, '2021-07-21 19:27:49', 2),
(84, 1, 5, 0, '2021-07-21 19:28:27', 2),
(85, 1, 5, 0, '2021-07-21 19:29:21', 2),
(86, 1, 5, 0, '2021-07-21 19:32:52', 2),
(87, 1, 5, 0, '2021-07-21 19:35:07', 2),
(90, 7, 2, 100, '2021-07-21 23:05:45', 2),
(91, 7, 3, 300, '2021-07-21 23:06:18', 2),
(92, 7, 2, 101, '2021-07-21 23:07:00', 2),
(93, 1, 2, 3500, '2021-07-21 23:08:44', 2),
(94, 2, 5, 0, '2021-07-21 23:18:21', 2),
(95, 3, 5, 0, '2021-07-21 23:18:36', 2),
(96, 5, 5, 0, '2021-07-21 23:18:56', 2),
(97, 6, 5, 0, '2021-07-21 23:19:08', 2),
(98, 2, 2, 2500, '2021-07-21 23:19:40', 2),
(99, 3, 2, 800, '2021-07-21 23:19:46', 2),
(100, 5, 2, 500, '2021-07-21 23:19:53', 2),
(101, 6, 2, 700, '2021-07-21 23:20:00', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id_prod` int(11) NOT NULL,
  `idcategoria` int(11) NOT NULL DEFAULT 1,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `idunidad` int(11) NOT NULL,
  `stock` decimal(7,0) NOT NULL,
  `precio` decimal(7,2) NOT NULL,
  `stockMin` decimal(7,0) NOT NULL DEFAULT 0,
  `stockMax` decimal(7,0) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id_prod`, `idcategoria`, `nombre`, `descripcion`, `idunidad`, `stock`, `precio`, `stockMin`, `stockMax`) VALUES
(1, 1, 'Papa Canchán', 'Papa de la sierra peruana', 1, '3500', '5.00', '3000', '0'),
(2, 1, 'Camote del Norte', 'Camote del Norte tamaño mediano', 1, '2500', '2.00', '2000', '0'),
(3, 1, 'Café Altomayo', 'Café altomayo original peruano', 2, '800', '1.00', '500', '0'),
(4, 2, 'Pollo San Fernando', 'Pollo de la marca San Fernando', 1, '1000', '7.50', '800', '0'),
(5, 1, 'Agua Cielo', 'Agua Cielo de KR Company', 2, '500', '2.50', '300', '0'),
(6, 1, 'WishCats', 'Comida para gatos nutritiva', 1, '700', '2.30', '600', '0'),
(7, 1, 'Pilsen Trigo', 'Cerveza con toques de amargor trigo', 2, '500', '6.00', '400', '0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipos_movimientos`
--

CREATE TABLE `tipos_movimientos` (
  `id_tipo_movimiento` int(11) NOT NULL,
  `nombre_tipo_mov` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tipos_movimientos`
--

INSERT INTO `tipos_movimientos` (`id_tipo_movimiento`, `nombre_tipo_mov`) VALUES
(1, 'Creado'),
(2, 'Entrada'),
(3, 'Salida'),
(4, 'Borrado'),
(5, 'Editado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidades`
--

CREATE TABLE `unidades` (
  `idunidad` int(11) NOT NULL,
  `nombre_unidad` varchar(45) NOT NULL,
  `simbolo` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `unidades`
--

INSERT INTO `unidades` (`idunidad`, `nombre_unidad`, `simbolo`) VALUES
(1, 'Kilogramo', 'kg'),
(2, 'Unidad', 'unid'),
(3, 'Saco', 'saco'),
(4, 'Lata', 'lata'),
(5, 'Botella', 'botella');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `idusuario` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `tipo` int(11) NOT NULL DEFAULT 1,
  `last_session` datetime DEFAULT '1970-01-02 00:00:00',
  `fecha_creacion` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`idusuario`, `username`, `password`, `tipo`, `last_session`, `fecha_creacion`) VALUES
(1, 'bodega', 'bodega', 1, '2021-07-21 19:25:35', '2021-06-20 19:30:50'),
(2, 'admin', 'admin', 2, '2021-07-22 03:01:52', '2021-07-21 18:48:08');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios_info`
--

CREATE TABLE `usuarios_info` (
  `idusuarios_info` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `idusuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios_info`
--

INSERT INTO `usuarios_info` (`idusuarios_info`, `nombre`, `apellido`, `idusuario`) VALUES
(1, 'José', 'Martinez', 1),
(2, 'Administrador', 'Admin', 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`idcategoria`);

--
-- Indices de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD PRIMARY KEY (`id_movimiento`),
  ADD KEY `mov_ibfk_1` (`idusuario`),
  ADD KEY `mov_ibfk_2` (`id_prod`),
  ADD KEY `mov_ibfk_3` (`id_tipo_movimiento`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id_prod`),
  ADD KEY `prod_ibfk_1` (`idcategoria`),
  ADD KEY `prod_ibfk_2` (`idunidad`);

--
-- Indices de la tabla `tipos_movimientos`
--
ALTER TABLE `tipos_movimientos`
  ADD PRIMARY KEY (`id_tipo_movimiento`);

--
-- Indices de la tabla `unidades`
--
ALTER TABLE `unidades`
  ADD PRIMARY KEY (`idunidad`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`idusuario`);

--
-- Indices de la tabla `usuarios_info`
--
ALTER TABLE `usuarios_info`
  ADD PRIMARY KEY (`idusuarios_info`),
  ADD KEY `usri_ibfk_1` (`idusuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `idcategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  MODIFY `id_movimiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id_prod` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de la tabla `tipos_movimientos`
--
ALTER TABLE `tipos_movimientos`
  MODIFY `id_tipo_movimiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `unidades`
--
ALTER TABLE `unidades`
  MODIFY `idunidad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `idusuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuarios_info`
--
ALTER TABLE `usuarios_info`
  MODIFY `idusuarios_info` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD CONSTRAINT `mov_ibfk_1` FOREIGN KEY (`idusuario`) REFERENCES `usuarios` (`idusuario`),
  ADD CONSTRAINT `mov_ibfk_2` FOREIGN KEY (`id_prod`) REFERENCES `productos` (`id_prod`),
  ADD CONSTRAINT `mov_ibfk_3` FOREIGN KEY (`id_tipo_movimiento`) REFERENCES `tipos_movimientos` (`id_tipo_movimiento`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `prod_ibfk_1` FOREIGN KEY (`idcategoria`) REFERENCES `categorias` (`idcategoria`),
  ADD CONSTRAINT `prod_ibfk_2` FOREIGN KEY (`idunidad`) REFERENCES `unidades` (`idunidad`);

--
-- Filtros para la tabla `usuarios_info`
--
ALTER TABLE `usuarios_info`
  ADD CONSTRAINT `usri_ibfk_1` FOREIGN KEY (`idusuario`) REFERENCES `usuarios` (`idusuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
