-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 17-05-2016 a las 07:23:02
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
  `estado_peticion` varchar(20) NOT NULL,
  `id_libro` int(10) NOT NULL,
  `dni_socio` varchar(9) NOT NULL,
  PRIMARY KEY (`fecha_inicio`,`id_libro`),
  KEY `id_libro` (`id_libro`),
  KEY `dni_socio` (`dni_socio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
('Cthulhu', 'Rachel Gray', 'ESP', 0, 22012112),
('Fragmentos', 'Sergio Marcos Vergara', 'Directors Cut', 0, 254411524),
('Eyes Only', 'Pedro J. Ramos', 'STR', 0, 341267865),
('Cazadores de Leyendas', 'Ismael Díaz Sacaluga', 'LORE', 0, 357684912),
('El Reino de la Sombra', 'José Luis López Morales', 'NSR', 0, 558256652),
('Masacre en la Galaxia', 'Gregor Hutton', 'LUZ', 0, 586628680),
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
(1, 'rol', 'ocupado'),
(2, 'rol', 'libre'),
(3, 'rol', 'libre'),
(4, 'rol', 'libre'),
(5, 'rol', 'libre'),
(6, 'estrategia', 'ocupado'),
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

--
-- Volcado de datos para la tabla `reservas`
--

INSERT INTO `reservas` (`fecha_inicio`, `fecha_final`, `n_mesa`, `dni_socio`) VALUES
('14:00:00', '15:30:00', 1, '47302193A'),
('18:30:00', '20:00:00', 6, '51293458B');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `sanciones`
--

INSERT INTO `sanciones` (`n_sancion`, `fecha_inicio`, `fecha_final`, `motivo`, `dni_socio`, `dni_emple`) VALUES
(1, '2016-05-13', '2016-05-20', 'No ha devuelto el libro a tiempo', '12345678Z', '47283104A');

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
('Paco Martinez Soria', '11111111A', '666666666', '1996-12-12', '2016-05-16', 'adulto', 1),
('Pepito Arraz de la Vega', '12345678Z', '911112324', '1989-01-01', '2016-05-13', 'adulto', 1),
('Juan Sánchez Gutiérrez', '47302193A', '762314584', '1996-03-01', '2016-05-08', 'adulto', 1),
('Señor de Prueba', '50304956W', '656567843', '1992-03-02', '2016-05-16', 'adulto', 1),
('Benito Pérez Nivelo', '51293458B', '722492545', '1998-01-01', '2016-05-01', 'juvenil', 1),
('Niño de prueba', '94584345E', '914356543', '1999-09-23', '2016-05-16', 'juvenil', 1),
('Bartolo', '98765432D', '913456781', '1990-07-20', '2016-05-17', 'adulto', 1);

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
