
# 🛒 MiProyectoDeMuebles (Backend)

Sistema backend para una plataforma e-commerce de muebles. Desarrollado con **Spring Boot**, este proyecto expone servicios RESTful que permiten gestionar usuarios, autenticación, productos, categorías, carritos de compra y banners publicitarios.

---

## 📁 Estructura del Proyecto

```
src/main/java/edu/sena/creamuebles/
├── config/               # Configuración de seguridad y filtros
├── controller/           # Controladores REST para cada entidad
│   └── view/             # Controladores para renderizado con Thymeleaf
├── dto/                  # Objetos de transferencia de datos
├── model/                # Entidades JPA (mapeo a base de datos)
├── repository/           # Interfaces JPA para acceso a datos
├── service/              # Lógica de negocio y servicios
└── CreamueblesApplication.java  # Clase principal Spring Boot
```

---

## ⚙️ Tecnologías y Dependencias

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- JWT (JSON Web Tokens)
- Thymeleaf
- MySQL (producción) / H2 (desarrollo)
- Maven

---

## 🔐 Autenticación

Se implementa un sistema de autenticación mediante **JWT**. Los endpoints protegidos requieren token en el header `Authorization`.

Configuraciones clave:
- `JwtAuthenticationFilter.java`
- `SecurityConfig.java`

---

## 🚀 Cómo ejecutar el proyecto

### 1. Clona el repositorio
```bash
git clone https://github.com/Rafael0896/MiProyectoDeMuebles.git
cd MiProyectoDeMuebles
```

### 2. Configura tu base de datos
Edita el archivo `application.properties` o `application.yml`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/creamuebles
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

### 3. Ejecuta el proyecto
```bash
./mvnw spring-boot:run
```

---

## 📦 Funcionalidades principales

- 🔑 Registro e inicio de sesión de usuarios con JWT
- 🪑 Gestión de productos y categorías de muebles
- 🛒 Operaciones de carrito de compras
- 🎯 Administración de banners promocionales
- 👤 CRUD de usuarios
- 📄 Vistas Thymeleaf integradas (opcional)

---

## 🔍 Paquetes importantes

| Capa         | Descripción                                     |
|--------------|-------------------------------------------------|
| `controller` | Define los endpoints del sistema                |
| `dto`        | Define los objetos para enviar/recibir datos    |
| `model`      | Contiene las entidades persistentes             |
| `repository` | Interfaces JPA para CRUD y queries              |
| `service`    | Lógica de negocio separada del controlador      |
| `config`     | Seguridad (JWT, filtros, CORS, etc.)            |

---

## 👨‍💻 Autor

Desarrollado por [Rafael Álvarez](https://github.com/Rafael0896)

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Puedes usarlo, modificarlo y distribuirlo libremente.
