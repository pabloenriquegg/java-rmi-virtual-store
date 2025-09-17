# Java RMI Virtual Store

Trabajo colaborativo de la asignatura **Programación Concurrente y Distribuida** (UD6).  
Implementación en **Java** de una tienda virtual distribuida mediante la tecnología **Remote Method Invocation (RMI)**.

## Contenido
- Carpeta `src/`: código fuente de los proyectos **Servidor** y **Cliente de pruebas**.
- Carpeta `data/`: ficheros JSON que actúan como persistencia ligera de datos (usuarios y productos).
- Carpeta `docs/`: documentación explicativa del trabajo colaborativo (memoria de proyecto).

## Funcionalidades principales
- **Gestión de usuarios**:
  - Inicio de sesión de usuarios registrados.
  - Registro de nuevos usuarios con validación de credenciales.
- **Gestión de productos**:
  - Inserción de nuevos productos.
  - Eliminación de productos existentes.
  - Visualización del catálogo.
- **Cliente de pruebas**:
  - Menú en consola para interactuar con el servidor.

## Requisitos
- **Java 8 o superior**
- Librería externa **Jackson** para la gestión de archivos JSON.
Objetivos

Este trabajo colaborativo permitió:

Comprender el funcionamiento de la comunicación remota en Java con RMI.

Implementar una arquitectura cliente-servidor con persistencia básica en JSON.

Desarrollar habilidades de trabajo en equipo en un proyecto académico real.

## Autores

Trabajo realizado en colaboración por:

Pablo Enrique Guntín Garrido

María A.

Pablo M.

Pilar P.

## Ejecución
1. Compilar los proyectos (Servidor y Cliente).
2. Ejecutar el servidor indicando los parámetros requeridos:
   ```bash
   Servidor: 

java -jar ServidorTienda.jar <ip> <puerto> <archivo usuarios> <archivo productos>
ejemplo:
	java -jar ServidorTienda.jar localhost 10666 usuarios.txt productos.txt


Cliente
java -jar Cliente.jar <ip> <puerto>