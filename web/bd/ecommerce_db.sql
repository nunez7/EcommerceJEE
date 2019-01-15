-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-01-2019 a las 16:45:37
-- Versión del servidor: 10.1.9-MariaDB
-- Versión de PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ecommerce_db`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_consultarProducto` (`p_moneda` CHAR(3), `webid` INT)  BEGIN
if p_moneda<> 'MXN' THEN
	SELECT p.*, m.precio AS precio2, m.precionuevo AS precion2 FROM producto p 
    INNER JOIN producto_moneda m ON m.cve_web=p.cve_web
    INNER JOIN marca marc ON marc.codigo=p.codigo_marca
    INNER JOIN categoria c ON c.codigo=p.codigo_categoria
    WHERE p.visible=true AND c.visible=true AND marc.visible=true
    AND m.moneda = p_moneda AND p.cve_web = webid;
else
	SELECT p.* FROM producto p 
    INNER JOIN marca marc ON marc.codigo=p.codigo_marca
    INNER JOIN categoria c ON c.codigo=p.codigo_categoria
    WHERE p.visible=true AND c.visible=true AND marc.visible=true AND p.cve_web = webid;
END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_contarProductosMarca` (`marc` INT)  BEGIN
 SELECT COUNT(*) FROM producto WHERE codigo_marca=marc;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_contarSubCategorias` (`codcat` INT)  BEGIN
	SELECT COUNT(*) AS cantidad FROM categoria	WHERE categoria_superior=codcat AND visible=true AND codigo<>codcat;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarCategoriaSuperior` ()  BEGIN
	SELECT codigo, nombre FROM categoria 		WHERE codigo=categoria_superior AND 		visible=true
    ORDER BY nombre;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarPorCategoria` (`p_moneda` CHAR(3), `p_categoria` INT)  BEGIN
if p_moneda<> 'MXN' THEN
	SELECT p.*, m.precio AS precio2, m.precionuevo AS precion2 FROM producto p 
    INNER JOIN producto_moneda m ON m.cve_web=p.cve_web
    INNER JOIN marca marc ON marc.codigo=p.codigo_marca
    INNER JOIN categoria c ON c.codigo=p.codigo_categoria
    WHERE p.visible=true AND c.visible=true AND marc.visible=true
    AND m.moneda = p_moneda AND p.codigo_categoria = p_categoria
    ORDER BY p.nombre;
else
	SELECT p.* FROM producto p 
    INNER JOIN marca marc ON marc.codigo=p.codigo_marca
    INNER JOIN categoria c ON c.codigo=p.codigo_categoria
    WHERE p.visible=true AND c.visible=true AND marc.visible=true AND p.codigo_categoria = p_categoria
    ORDER BY p.nombre;
END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarPorMarca` (`p_moneda` CHAR(3), `marc` INT)  BEGIN
if p_moneda<> 'MXN' THEN
	SELECT p.*, m.precio AS precio2, m.precionuevo AS precion2 FROM producto p 
    INNER JOIN producto_moneda m ON m.cve_web=p.cve_web
    INNER JOIN marca marc ON marc.codigo=p.codigo_marca
    INNER JOIN categoria c ON c.codigo=p.codigo_categoria
    WHERE p.visible=true AND c.visible=true AND marc.visible=true
    AND m.moneda = p_moneda AND p.codigo_marca = marc
    ORDER BY p.nombre;
else
	SELECT p.* FROM producto p 
    INNER JOIN marca marc ON marc.codigo=p.codigo_marca
    INNER JOIN categoria c ON c.codigo=p.codigo_categoria
    WHERE p.visible=true AND c.visible=true AND marc.visible=true AND p.codigo_marca = marc
    ORDER BY p.nombre;
END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarRecomendados` (`p_moneda` CHAR(3))  BEGIN
if p_moneda<> 'MXN' THEN
	SELECT p.*, m.precio AS precio2, m.precionuevo AS precion2 FROM producto p 
    INNER JOIN producto_moneda m ON m.cve_web=p.cve_web
    INNER JOIN marca marc ON marc.codigo=p.codigo_marca
    INNER JOIN categoria c ON c.codigo=p.codigo_categoria
    WHERE p.visible=true AND c.visible=true AND marc.visible=true
    AND m.moneda = p_moneda
    ORDER BY p.nombre;
else
	SELECT p.* FROM producto p 
    INNER JOIN marca marc ON marc.codigo=p.codigo_marca
    INNER JOIN categoria c ON c.codigo=p.codigo_categoria
    WHERE p.visible=true AND c.visible=true AND marc.visible=true
    ORDER BY p.nombre;
END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarSubCategoria` (`csuperior` INT)  BEGIN
	SELECT codigo, nombre FROM categoria WHERE    codigo<>categoria_superior AND visible=true AND categoria_superior=csuperior
    ORDER BY nombre;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarTodoDeCategoria` ()  BEGIN 
SELECT codigo, nombre FROM categoria WHERE visible = true ORDER BY nombre;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarTodoDeMarca` ()  BEGIN 
SELECT codigo, nombre FROM marca WHERE visible = true ORDER BY nombre;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_registrarProducto` (IN `p_nombre` VARCHAR(30), IN `p_precio` DECIMAL(10,2), IN `p_precionuevo` DECIMAL(10,2), IN `p_stock` INT, IN `p_nuevo` BOOLEAN, IN `p_recomendado` BOOLEAN, IN `p_descripcion` VARCHAR(255), IN `p_visible` BOOLEAN, IN `p_codigo_marca` INT, IN `p_codigo_categoria` INT, IN `p_img` VARCHAR(100), IN `p_moneda_cop` CHAR(3), IN `p_precio_cop` DECIMAL(10,2), IN `p_precionuevo_cop` DECIMAL(10,2), IN `p_moneda_usa` CHAR(3), IN `p_precio_usa` DECIMAL(10,2), IN `p_precionuevo_usa` DECIMAL(10,2), IN `p_moneda_pen` CHAR(3), IN `p_precio_pen` DECIMAL(10,2), IN `p_precionuevo_pen` DECIMAL(10,2))  BEGIN
 	DECLARE webid INT;
	INSERT INTO producto VALUES(NULL, p_nombre, p_precio, p_precionuevo, p_stock, p_nuevo, p_recomendado, p_descripcion, p_visible, p_codigo_marca,p_codigo_categoria, p_img);
    SET webid = (SELECT LAST_INSERT_ID());
    INSERT INTO producto_moneda VALUES(p_moneda_cop, p_precio_cop, p_precionuevo_cop,webid);
    INSERT INTO producto_moneda VALUES(p_moneda_usa, p_precio_usa, p_precionuevo_usa, webid);
    INSERT INTO producto_moneda VALUES(p_moneda_pen, p_precio_pen, p_precionuevo_pen, webid);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `codigo` int(11) NOT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `visible` tinyint(1) DEFAULT '1',
  `categoria_superior` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`codigo`, `nombre`, `visible`, `categoria_superior`) VALUES
(1, 'ROPA DEPORTIVA', 1, 1),
(2, 'NIKE', 1, 1),
(3, 'ADIDAS', 1, 1),
(4, 'PUMA', 1, 1),
(5, 'HOMBRES', 1, 5),
(6, 'SACOS', 1, 5),
(7, 'PANTALONES', 1, 5),
(8, 'MUJERES', 1, 8),
(9, 'NIÑOS', 1, 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE `marca` (
  `codigo` int(11) NOT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `visible` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`codigo`, `nombre`, `visible`) VALUES
(1, 'NIKE', 1),
(2, 'ADIDAS', 1),
(3, 'PUMA', 1),
(4, 'LACOSTE', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `cve_web` int(11) NOT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `precionuevo` decimal(10,2) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `nuevo` tinyint(1) DEFAULT '1',
  `recomendado` tinyint(1) DEFAULT '1',
  `descripcion` varchar(255) DEFAULT NULL,
  `visible` tinyint(1) DEFAULT '1',
  `codigo_marca` int(11) DEFAULT NULL,
  `codigo_categoria` int(11) DEFAULT NULL,
  `img` varchar(100) DEFAULT 'demo.png'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`cve_web`, `nombre`, `precio`, `precionuevo`, `stock`, `nuevo`, `recomendado`, `descripcion`, `visible`, `codigo_marca`, `codigo_categoria`, `img`) VALUES
(6, 'Camiseta para hombre', '200.00', '0.00', 10, 1, 0, '                                        ', 1, 1, 5, '14120191210224334427100026759231pu.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto_moneda`
--

CREATE TABLE `producto_moneda` (
  `moneda` char(3) NOT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `precionuevo` decimal(10,2) DEFAULT NULL,
  `cve_web` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producto_moneda`
--

INSERT INTO `producto_moneda` (`moneda`, `precio`, `precionuevo`, `cve_web`) VALUES
('COP', '12000.00', '0.00', 6),
('PEN', '201.00', '0.00', 6),
('USD', '12.00', '0.00', 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `revision`
--

CREATE TABLE `revision` (
  `codigo` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `correo` varchar(60) DEFAULT NULL,
  `comentario` varchar(200) DEFAULT NULL,
  `estrellas` int(11) DEFAULT '3',
  `fecha` datetime DEFAULT NULL,
  `cve_web` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`codigo`),
  ADD UNIQUE KEY `nombre` (`nombre`),
  ADD KEY `categoria_superior` (`categoria_superior`);

--
-- Indices de la tabla `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`codigo`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`cve_web`),
  ADD KEY `codigo_marca` (`codigo_marca`),
  ADD KEY `codigo_categoria` (`codigo_categoria`);

--
-- Indices de la tabla `producto_moneda`
--
ALTER TABLE `producto_moneda`
  ADD PRIMARY KEY (`moneda`,`cve_web`),
  ADD KEY `cve_web` (`cve_web`);

--
-- Indices de la tabla `revision`
--
ALTER TABLE `revision`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `cve_web` (`cve_web`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT de la tabla `marca`
--
ALTER TABLE `marca`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `cve_web` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `revision`
--
ALTER TABLE `revision`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD CONSTRAINT `categoria_ibfk_1` FOREIGN KEY (`categoria_superior`) REFERENCES `categoria` (`codigo`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`codigo_marca`) REFERENCES `marca` (`codigo`),
  ADD CONSTRAINT `producto_ibfk_2` FOREIGN KEY (`codigo_categoria`) REFERENCES `categoria` (`codigo`);

--
-- Filtros para la tabla `producto_moneda`
--
ALTER TABLE `producto_moneda`
  ADD CONSTRAINT `producto_moneda_ibfk_1` FOREIGN KEY (`cve_web`) REFERENCES `producto` (`cve_web`);

--
-- Filtros para la tabla `revision`
--
ALTER TABLE `revision`
  ADD CONSTRAINT `revision_ibfk_1` FOREIGN KEY (`cve_web`) REFERENCES `producto` (`cve_web`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
