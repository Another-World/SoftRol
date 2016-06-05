-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 05-06-2016 a las 18:15:46
-- Versión del servidor: 5.5.24-log
-- Versión de PHP: 5.4.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `softrol`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alquileres_libro`
--

CREATE TABLE IF NOT EXISTS `alquileres_libro` (
  `fecha_inicio` date NOT NULL,
  `fecha_final` date NOT NULL,
  `id_libro` int(10) NOT NULL,
  `dni_socio` varchar(9) NOT NULL,
  PRIMARY KEY (`fecha_inicio`,`id_libro`),
  KEY `id_libro` (`id_libro`),
  KEY `dni_socio` (`dni_socio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `alquileres_libro`
--

INSERT INTO `alquileres_libro` (`fecha_inicio`, `fecha_final`, `id_libro`, `dni_socio`) VALUES
('2016-06-05', '2016-06-10', 22012112, '13121432R'),
('2016-06-05', '2016-06-05', 341267865, '13121432R'),
('2016-06-05', '2016-06-05', 586628680, '13121432R');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuotas`
--

CREATE TABLE IF NOT EXISTS `cuotas` (
  `TIPO` varchar(10) NOT NULL,
  `PRECIO` float NOT NULL,
  PRIMARY KEY (`TIPO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cuotas`
--

INSERT INTO `cuotas` (`TIPO`, `PRECIO`) VALUES
('adulto', 35),
('juvenil', 25);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE IF NOT EXISTS `empleados` (
  `nombre` varchar(50) NOT NULL,
  `dni_emple` varchar(9) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `contraseña` varchar(20) NOT NULL,
  `rango` varchar(15) NOT NULL,
  PRIMARY KEY (`dni_emple`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`nombre`, `dni_emple`, `telefono`, `contraseña`, `rango`) VALUES
('TesterADMIN', '1', '1', '1', 'administrador'),
('testerEMPLE', '2', '2', '2', 'empleado'),
('David Alegría Martín', '47283104A', '649793231', 'a1234567', 'administrador'),
('Alex Arranz Andrés', '51527711Y', '677776802', 'c1234567', 'empleado'),
('Marcelo Nivelo Delgado', '51749557V', '722131829', 'b1234567', 'empleado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libros`
--

CREATE TABLE IF NOT EXISTS `libros` (
  `titulo` varchar(50) NOT NULL,
  `autor` varchar(50) NOT NULL,
  `editorial` varchar(20) NOT NULL,
  `estado_alquilado` tinyint(1) NOT NULL DEFAULT '0',
  `id_libro` int(10) NOT NULL,
  PRIMARY KEY (`id_libro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `libros`
--

INSERT INTO `libros` (`titulo`, `autor`, `editorial`, `estado_alquilado`, `id_libro`) VALUES
('Aquelarre', 'Ricard Ibáñez', 'NSR', 0, 12345606),
('Cthulhu', 'Rachel Gray', 'ESP', 1, 22012112),
('Fragmentos', 'Sergio Marcos Vergara', 'Directors Cut', 0, 254411524),
('Eyes Only', 'Pedro J. Ramos', 'STR', 1, 341267865),
('Cazadores de Leyendas', 'Ismael Díaz Sacaluga', 'LORE', 0, 357684912),
('El Reino de la Sombra', 'José Luis López Morales', 'NSR', 0, 558256652),
('Masacre en la Galaxia', 'Gregor Hutton', 'LUZ', 1, 586628680),
('Ars Malefica', 'Miguel Ángel Ruíz', 'DUNGEONSPAIN', 0, 852741611);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mesas`
--

CREATE TABLE IF NOT EXISTS `mesas` (
  `n_mesa` int(2) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `estado` varchar(20) NOT NULL,
  PRIMARY KEY (`n_mesa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `mesas`
--

INSERT INTO `mesas` (`n_mesa`, `tipo`, `estado`) VALUES
(1, 'rol', 'libre'),
(2, 'rol', 'libre'),
(3, 'rol', 'libre'),
(4, 'rol', 'libre'),
(5, 'rol', 'libre'),
(6, 'estrategia', 'libre'),
(7, 'estrategia', 'libre'),
(8, 'estrategia', 'libre'),
(9, 'estrategia', 'libre'),
(10, 'estrategia', 'libre');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

CREATE TABLE IF NOT EXISTS `reservas` (
  `fecha_inicio` time NOT NULL,
  `fecha_final` time NOT NULL,
  `n_mesa` int(2) NOT NULL,
  `dni_socio` varchar(9) NOT NULL,
  PRIMARY KEY (`fecha_inicio`,`n_mesa`),
  KEY `n_mesa` (`n_mesa`),
  KEY `dni_socio` (`dni_socio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sanciones`
--

CREATE TABLE IF NOT EXISTS `sanciones` (
  `n_sancion` int(4) NOT NULL AUTO_INCREMENT,
  `fecha_inicio` date NOT NULL,
  `fecha_final` date NOT NULL,
  `motivo` varchar(50) NOT NULL,
  `dni_socio` varchar(9) NOT NULL,
  `dni_emple` varchar(9) NOT NULL,
  PRIMARY KEY (`n_sancion`),
  KEY `dni_socio` (`dni_socio`),
  KEY `dni_emple` (`dni_emple`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `socios`
--

CREATE TABLE IF NOT EXISTS `socios` (
  `nombre` varchar(50) NOT NULL,
  `dni_socio` varchar(9) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `fecha_alta` date NOT NULL,
  `tipo_cuota` varchar(10) NOT NULL,
  `cuota_pagada` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`dni_socio`),
  KEY `tipo_cuota` (`tipo_cuota`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `socios`
--

INSERT INTO `socios` (`nombre`, `dni_socio`, `telefono`, `fecha_nacimiento`, `fecha_alta`, `tipo_cuota`, `cuota_pagada`) VALUES
('Juana Benitez ', '13121432R', '912354564', '2000-09-23', '2016-05-23', 'juvenil', 1),
('Eustaquia del Carmen ', '20932658J', '722345483', '1980-05-10', '2016-05-16', 'adulto', 1),
('Pepito Arraz de la Vega', '23315996E', '911112324', '1989-01-01', '2016-05-13', 'adulto', 1),
('prueba de xml', '27105899P', '612345677', '2000-03-04', '2016-06-05', 'juvenil', 1),
('Raul Perez Castro', '35975269A', '695495049', '2000-05-09', '2016-05-09', 'juvenil', 1),
('Juan Sánchez Gutiérrez', '45456338N', '762314584', '1996-03-01', '2016-05-08', 'adulto', 1),
('Benito Pérez Nivelo', '51635476Q', '684539234', '1998-01-01', '2016-05-01', 'juvenil', 1),
('Alejandro Sanchez Gutierrez', '72803494F', '634543245', '1990-05-02', '2016-05-16', 'adulto', 1);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alquileres_libro`
--
ALTER TABLE `alquileres_libro`
  ADD CONSTRAINT `alquileres_libro_ibfk_1` FOREIGN KEY (`dni_socio`) REFERENCES `socios` (`dni_socio`),
  ADD CONSTRAINT `alquileres_libro_ibfk_2` FOREIGN KEY (`id_libro`) REFERENCES `libros` (`id_libro`);

--
-- Filtros para la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD CONSTRAINT `reservas_ibfk_1` FOREIGN KEY (`n_mesa`) REFERENCES `mesas` (`n_mesa`),
  ADD CONSTRAINT `reservas_ibfk_2` FOREIGN KEY (`dni_socio`) REFERENCES `socios` (`dni_socio`);

--
-- Filtros para la tabla `sanciones`
--
ALTER TABLE `sanciones`
  ADD CONSTRAINT `sanciones_ibfk_1` FOREIGN KEY (`dni_socio`) REFERENCES `socios` (`dni_socio`),
  ADD CONSTRAINT `sanciones_ibfk_2` FOREIGN KEY (`dni_emple`) REFERENCES `empleados` (`dni_emple`);

--
-- Filtros para la tabla `socios`
--
ALTER TABLE `socios`
  ADD CONSTRAINT `socios_ibfk_1` FOREIGN KEY (`tipo_cuota`) REFERENCES `cuotas` (`TIPO`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
