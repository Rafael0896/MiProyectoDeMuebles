# 🛒 MiProyectoDeMuebles - E-commerce Backend

Sistema backend para una plataforma e-commerce de muebles desarrollado con **Spring Boot**. Este proyecto expone servicios RESTful que permiten gestionar usuarios, autenticación, productos, categorías, carritos de compra y banners publicitarios.

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-4.0.0-blue)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

## 📋 Tabla de Contenidos

- [🏗️ Arquitectura del Proyecto](#-arquitectura-del-proyecto)
- [⚙️ Tecnologías y Dependencias](#-tecnologías-y-dependencias)
- [📁 Estructura del Proyecto](#-estructura-del-proyecto)
- [🚀 Instalación y Configuración](#-instalación-y-configuración)
- [🔐 Sistema de Autenticación](#-sistema-de-autenticación)
- [📦 Funcionalidades Principales](#-funcionalidades-principales)
- [🔍 Documentación de API](#-documentación-de-api)
- [🗄️ Base de Datos](#-base-de-datos)
- [🧪 Testing](#-testing)
- [📊 Monitoreo y Logs](#-monitoreo-y-logs)
- [🤝 Contribuir](#-contribuir)
- [👨‍💻 Autor](#-autor)
- [📄 Licencia](#-licencia)

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una arquitectura de capas basada en Spring Boot:

```
┌─────────────────┐
│   Controllers   │ ← Capa de presentación (REST API)
├─────────────────┤
│    Services     │ ← Lógica de negocio
├─────────────────┤
│  Repositories   │ ← Acceso a datos (JPA)
├─────────────────┤
│     Models      │ ← Entidades de dominio
└─────────────────┘
```

## ⚙️ Tecnologías y Dependencias

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java** | 17 | Lenguaje de programación |
| **Spring Boot** | 3.x | Framework principal |
| **Spring Security** | 6.x | Seguridad y autenticación |
| **Spring Data JPA** | 3.x | Persistencia de datos |
| **JWT** | - | Autenticación stateless |
| **Thymeleaf** | 3.x | Motor de plantillas |
| **MySQL** | 8.0+ | Base de datos (producción) |
| **H2** | 2.x | Base de datos (desarrollo) |
| **Maven** | 3.8+ | Gestión de dependencias |

## 📁 Estructura del Proyecto

```
src/main/java/edu/sena/creamuebles/
├── 🎮 controller/           # Controladores REST
│   ├── AuthController.java       # Autenticación y login
│   ├── BannerController.java     # Gestión de banners
│   ├── ProductController.java    # CRUD de productos
│   ├── CategoryController.java   # CRUD de categorías
│   ├── CartController.java       # Carrito de compras
│   ├── UserController.java       # Gestión de usuarios
│   └── view/                     # Controladores para vistas Thymeleaf
├── 📦 dto/                  # Data Transfer Objects
│   ├── ProductRequestDTO.java    # DTO para productos
│   ├── CartItemDTO.java          # DTO para items del carrito
│   └── ...                       # Otros DTOs
├── 🗃️ model/                # Entidades JPA
│   ├── User.java                 # Entidad usuario
│   ├── Product.java              # Entidad producto
│   ├── Category.java             # Entidad categoría
│   ├── Cart.java                 # Entidad carrito
│   └── ...                       # Otras entidades
├── 🔧 config/               # Configuración
│   ├── SecurityConfig.java       # Configuración de seguridad
│   ├── JwtAuthenticationFilter.java # Filtro JWT
│   ├── PasswordConfig.java       # Configuración de contraseñas
│   └── WebConfig.java            # Configuración web
├── 🗄️ repository/           # Repositorios JPA
│   ├── UserRepository.java       # Repositorio de usuarios
│   ├── ProductRepository.java    # Repositorio de productos
│   └── ...                       # Otros repositorios
├── 🔐 security/             # Componentes de seguridad
│   └── OAuth2AuthenticationSuccessHandler.java
├── ⚙️ service/              # Interfaces de servicios
│   ├── ProductService.java       # Servicio de productos
│   ├── CartService.java          # Servicio de carrito
│   ├── JwtService.java           # Servicio JWT
│   └── impl/                     # Implementaciones
│       ├── ProductServiceImpl.java
│       ├── CartServiceImpl.java
│       └── ...
└── 🚀 CreamueblesApplication.java # Clase principal
```

## 🚀 Instalación y Configuración

### Prerrequisitos

- Java 17 o superior
- Maven 3.8+
- MySQL 8.0+ (para producción)
- Git

### 1. Clonar el repositorio

```bash
git clone https://github.com/Rafael0896/MiProyectoDeMuebles.git
cd MiProyectoDeMuebles
```

### 2. Configurar la base de datos

#### Para desarrollo (H2 - en memoria):
```properties
# application-dev.properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
```

#### Para producción (MySQL):
```properties
# application-prod.properties
spring.datasource.url=jdbc:mysql://localhost:3306/creamuebles
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

### 3. Configurar variables de entorno

```bash
# Variables JWT
export JWT_SECRET_KEY=tu_clave_secreta_muy_larga_y_segura
export JWT_EXPIRATION_TIME=86400000

# Variables de base de datos
export DB_URL=jdbc:mysql://localhost:3306/creamuebles
export DB_USERNAME=tu_usuario
export DB_PASSWORD=tu_contraseña
```

### 4. Ejecutar el proyecto

```bash
# Desarrollo
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Producción
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### 5. Verificar la instalación

La aplicación estará disponible en: `http://localhost:8080`

## 🔐 Sistema de Autenticación

El proyecto implementa autenticación mediante **JWT (JSON Web Tokens)** con las siguientes características:

### Endpoints de autenticación:
- `POST /api/auth/register` - Registro de usuarios
- `POST /api/auth/login` - Inicio de sesión
- `POST /api/auth/refresh` - Renovar token
- `POST /api/auth/logout` - Cerrar sesión

### Uso del token:
```bash
# Incluir en el header de las peticiones
Authorization: Bearer <tu_jwt_token>
```

### Configuración de seguridad:
- Tokens expiran en 24 horas por defecto
- Soporte para OAuth2 (Google, Facebook)
- Encriptación de contraseñas con BCrypt
- Filtros personalizados para validación JWT

## 📦 Funcionalidades Principales

### 🔑 Gestión de Usuarios
- ✅ Registro e inicio de sesión
- ✅ Perfiles de usuario
- ✅ Roles y permisos
- ✅ Recuperación de contraseña

### 🪑 Gestión de Productos
- ✅ CRUD completo de productos
- ✅ Categorización de productos
- ✅ Búsqueda y filtrado
- ✅ Imágenes de productos
- ✅ Gestión de inventario

### 🛒 Carrito de Compras
- ✅ Agregar/eliminar productos
- ✅ Modificar cantidades
- ✅ Cálculo de totales
- ✅ Persistencia del carrito

### 🎯 Sistema de Banners
- ✅ Banners promocionales
- ✅ Gestión de campañas
- ✅ Programación de banners

### 📊 Dashboard Administrativo
- ✅ Estadísticas de ventas
- ✅ Gestión de inventario
- ✅ Reportes de usuarios

## 🔍 Documentación de API

### Productos

#### Obtener todos los productos
```http
GET /api/products
```

#### Obtener producto por ID
```http
GET /api/products/{id}
```

#### Crear nuevo producto
```http
POST /api/products
Content-Type: application/json

{
  "name": "Sofá Moderno",
  "description": "Sofá de tres plazas",
  "price": 1299.99,
  "categoryId": 1,
  "stock": 10
}
```

### Carrito

#### Agregar producto al carrito
```http
POST /api/cart/add
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

#### Ver carrito actual
```http
GET /api/cart
```

## 🗄️ Base de Datos

### Diagrama de Entidades

```mermaid
erDiagram
    USER ||--o{ CART : tiene
    USER {
        Long id PK
        String username
        String email
        String password
        String role
        DateTime createdAt
    }
    
    PRODUCT ||--o{ CART_ITEM : contiene
    PRODUCT }o--|| CATEGORY : pertenece
    PRODUCT {
        Long id PK
        String name
        String description
        BigDecimal price
        Integer stock
        String imageUrl
        Long categoryId FK
    }
    
    CATEGORY {
        Long id PK
        String name
        String description
    }
    
    CART ||--o{ CART_ITEM : contiene
    CART {
        Long id PK
        Long userId FK
        DateTime createdAt
        DateTime updatedAt
    }
    
    CART_ITEM {
        Long id PK
        Long cartId FK
        Long productId FK
        Integer quantity
        BigDecimal unitPrice
    }
```

### Scripts de inicialización

El archivo `src/main/resources/data.sql` contiene datos de prueba para desarrollo.

## 🧪 Testing

### Ejecutar todas las pruebas
```bash
./mvnw test
```

### Ejecutar pruebas específicas
```bash
./mvnw test -Dtest=ProductControllerTest
```

### Coverage de pruebas
```bash
./mvnw jacoco:report
```

## 📊 Monitoreo y Logs

### Endpoints de Actuator
- `/actuator/health` - Estado de la aplicación
- `/actuator/info` - Información del proyecto
- `/actuator/metrics` - Métricas de la aplicación

### Configuración de logs
```properties
# application.properties
logging.level.edu.sena.creamuebles=DEBUG
logging.pattern.console=%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
logging.file.name=logs/creamuebles.log
```

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

### Estándares de código:
- Seguir las convenciones de Java
- Documentar métodos públicos
- Escribir pruebas unitarias
- Usar nombres descriptivos para variables y métodos

## 👨‍💻 Autor

**Rafael Álvarez** - [@Rafael0896](https://github.com/Rafael0896)

- 📧 Email: rafael.alvarez@ejemplo.com
- 💼 LinkedIn: [Rafael Álvarez](https://linkedin.com/in/rafael-alvarez)
- 🌐 Portfolio: [rafaelalvarez.dev](https://rafaelalvarez.dev)

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

---

### 🔗 Enlaces útiles

- [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- [Guía de Spring Security](https://spring.io/guides/gs/securing-web/)
- [JWT.io](https://jwt.io/) - Información sobre JSON Web Tokens
- [MySQL Documentation](https://dev.mysql.com/doc/)

### 📈 Roadmap

- [ ] Implementar sistema de pagos
- [ ] Agregar notificaciones en tiempo real
- [ ] Implementar sistema de reviews
- [ ] Agregar soporte para múltiples idiomas
- [ ] Implementar caché con Redis
- [ ] Agregar documentación con Swagger/OpenAPI

---

⭐ ¡No olvides darle una estrella al proyecto si te resultó útil!
