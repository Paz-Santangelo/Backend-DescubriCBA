# DescubríCBA

**DescubríCBA** es una aplicación web turística desarrollada para destacar y promover los atractivos de la provincia de Córdoba, Argentina. Ofrece a residentes y visitantes una guía interactiva con información completa sobre lugares para visitar, alojamientos, gastronomía, espectáculos, centros de salud y seguridad.

##  Descripción

La aplicación permite a los usuarios:

- Explorar lugares turísticos categorizados.
- Ver información de restaurantes, hoteles, alojamientos y espectáculos.
- Subir fotos y dejar comentarios en los distintos lugares visitados.
- Acceder rápidamente a información útil como hospitales, centros de salud y comisarías cercanas.

Está pensada como una herramienta inclusiva, intuitiva y útil tanto para turistas como para los habitantes de la provincia.

##  Tecnologías

### Backend:
- Java 17
- Spring Boot
- API REST

### Otros:
- Base de datos relacional (MySQL, PostgreSQL, etc.)
- Git y GitHub para control de versiones

## Instalación y Configuración del Backend

Esta guía detalla los pasos para descargar, configurar y ejecutar el backend del proyecto **DescubríCBA**.

### Requisitos Previos

- Java 17 o superior
- PostgreSQL
- Git
- Un cliente de API como [Postman](https://www.postman.com/downloads/)
- Un IDE para Java como IntelliJ IDEA o VS Code con las extensiones adecuadas.

### 1. Clonar el Repositorio

Abre una terminal o consola y clona el repositorio del proyecto desde GitHub:

```bash
git clone https://github.com/TuUsuario/Backend-DescubriCBA.git
cd Backend-DescubriCBA
```

### 2. Crear la Base de Datos

El proyecto utiliza PostgreSQL. Debes crear una base de datos llamada `descubri_cba`.

1.  Abre una terminal de `psql` o una herramienta de gestión de bases de datos como pgAdmin o DBeaver.
2.  Ejecuta el siguiente comando SQL para crear la base de datos:

```sql
CREATE DATABASE descubri_cba;
```

Spring Boot se encargará de crear las tablas automáticamente la primera vez que se ejecute la aplicación gracias a la configuración `spring.jpa.hibernate.ddl-auto=update` en `application.properties`.

### 3. Configurar Variables de Entorno

La aplicación necesita credenciales para conectarse a tu base de datos. Debes configurar las variables de entorno `PSQL_USER` y `PSQL_PASSWORD` con tu usuario y contraseña de PostgreSQL.

**En Windows (PowerShell):**
```powershell
$env:PSQL_USER="tu_usuario_postgres"
$env:PSQL_PASSWORD="tu_contraseña"
```

**En macOS o Linux:**
```bash
export PSQL_USER="tu_usuario_postgres"
export PSQL_PASSWORD="tu_contraseña"
```
> **Nota:** Estas variables solo se mantendrán en la sesión actual de la terminal. Para una configuración permanente, agrégalas al perfil de tu sistema (`.bashrc`, `.zshrc`, etc.) o configúralas en las opciones de ejecución de tu IDE.

### 4. Ejecutar la Aplicación

Puedes ejecutar el backend desde tu IDE (recomendado) o usando Maven.

- **Desde tu IDE:** Importa el proyecto como un proyecto de Maven y ejecuta la clase principal que contenga el método `main`.
- **Desde la terminal:** Navega a la raíz del proyecto backend y ejecuta:

```bash
./mvnw spring-boot:run
```

Si todo está configurado correctamente, verás en la consola que la aplicación se ha iniciado y está conectada a la base de datos.

### 5. Crear Usuarios con Roles Específicos

Una vez que el backend esté en ejecución, puedes crear usuarios con roles como `ADMIN` o `MANAGEMENT` utilizando Postman.

1.  **Abre Postman** y crea una nueva petición.
2.  **Método:** `POST`
3.  **URL:** `http://localhost:8080/api/auth/register`
4.  **Pestaña "Body":** Selecciona el tipo `raw` y formato `JSON`.
5.  **Contenido del Body:** Pega el siguiente JSON para crear un usuario administrador. Puedes modificar los datos para crear otros usuarios.

```json
{
    "email": "admin@gmail.com",
    "password": "admin2025",
    "name": "Tu Nombre",
    "lastname": "Tu Apellido",
    "role": "ADMIN"
}
```

6.  **Envía la petición.** Recibirás un mensaje de confirmación si el usuario se ha registrado con éxito. Para crear un usuario con rol `MANAGEMENT`, simplemente cambia el valor del campo `"role"`.

## Autores
 
Paz Santangelo

Roxana Mancuello

Alejandro Santangelo

📄 Documentación del Proyecto
Esta sección será completada próximamente con los documentos del PMI, cronograma y matriz de riesgos.



📝 Licencia
Este proyecto, en principio, es de uso educativo y sin fines de lucro. Derechos reservados a sus autores.