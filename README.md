# TuTicket

**TuTicket** es una aplicación para la gestión de tickets. Este repositorio contiene el código fuente y los recursos necesarios para ejecutar la aplicación.

## Tecnologías Utilizadas

- **Lenguaje**: Java 17
- **Framework**: Spring Boot
- **Documentación de API**: Swagger
- **Base de Datos**: H2
- **Gestión de Dependencias**: Maven

## Requisitos

- **Java 17**
- **Maven**

## Instalación

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/bnarvaez2/tuticket.git

2. **Construye el proyecto con Maven**:
   
    ```bash
    mvn clean install
    ```
3. **Despliegue en local**:
   
    ```bash
    mvn spring-boot:run

## Documentación

### Endpoints

### 1. **Authentication API**

#### Crear un token de autenticación (Usuario)

- **Método**: `POST`
- **Ruta**: `/api/v1/auth`
- **Descripción**: Genera un token de autenticación para un usuario regular.
- **Cuerpo de la solicitud**:

  ```json
  {
    "username": "usuario",
    "password": "password123"
  }

- **Respuesta**:  
  - **Código 200**: Token de autenticación generado correctamente.
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiYjQ2YjVhMzktM2ZjMy00ZTBiLTkzYzgtMmFhZTc3Y2NhYjQ4IiwiaWF0IjoxNjg2NTMwNzMwLCJleHBpcmVkX2RhdGUiOiIyMDI1LTAxLTIyVDEzOjUyOjMwIn0.2F4Jkh6ihON7xDr8vLfByML0fLoFJGBK_JLBcXwrxkA"
    }
    ```
  - **Código 400**: Bad request.
    ```json
    {
      "message": "Message specifying the error in the request."
    }
    ```

  
### Crear un token de autenticación (Administrador)

- **Método**: `POST`
- **Ruta**: `/api/v1/auth/admin`
- **Descripción**: Genera un token de autenticación para un administrador.
- **Cuerpo de la solicitud**:
  
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }

- **Respuesta**:  
  - **Código 200**: Token de autenticación generado correctamente.
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiYjQ2YjVhMzktM2ZjMy00ZTBiLTkzYzgtMmFhZTc3Y2NhYjQ4IiwiaWF0IjoxNjg2NTMwNzMwLCJleHBpcmVkX2RhdGUiOiIyMDI1LTAxLTIyVDEzOjUyOjMwIn0.2F4Jkh6ihON7xDr8vLfByML0fLoFJGBK_JLBcXwrxkA"
    }
    ```
  - **Código 400**: Bad request.
    ```json
    {
      "message": "Message specifying the error in the request."
    }
    ```

### 2. **User API**

#### Crear un nuevo usuario

- **Método:** POST
- **Ruta:** /api/v1/users
- **Descripción:** Crea un nuevo usuario.
- **Cuerpo de la solicitud:**
  ```json
  {
    "name": "Brian",
    "lastname": "Narvaez"
  }

- **Respuesta**: 
  - **Código 201**: Usuario creado exitosamente.
    ```json
    {
      "message": "User created successfully. User ID: 123e4567-e89b-12d3-a456-426614174000"
    }
    ```
  - **Código 400**: Bad request.
    ```json
    {
      "message": "Message specifying the error in the request."
    }
    ```
    
#### Obtener todos los usuarios

- **Método:** GET
- **Ruta:** /api/v1/users
- **Descripción:** Obtiene una lista de todos los usuarios.
- **Respuesta**: 
  - **Código 200**: Usuarios recuperados exitosamente.
    ```json
      [
        {
          "id": "123e4567-e89b-12d3-a456-426614174000",
          "name": "Brian",
          "lastname": "Narvaez",
          "creationDate": "2025-01-23T14:30:00",
          "updateDate": "2025-01-23T15:00:00"
        }
      ]
    ```

#### Obtener un usuario por ID

- **Método:** GET
- **Ruta:** /api/v1/users/{id}
- **Descripción:** Obtiene un usuario por su ID.
- **Respuesta**: 
  - **Código 200**: Usuario recuperado exitosamente.
    ```json
    {
      "id": "123e4567-e89b-12d3-a456-426614174000",
      "name": "Brian",
      "lastname": "Narvaez",
      "creationDate": "2025-01-23 14:30:00",
      "updateDate": "2025-01-23 15:00:00"
    }
    ```

### 3. **Ticket API**

#### Crear un nuevo ticket

- **Método:** POST
- **Ruta:** /api/v1/tickets
- **Descripción:** Crea un nuevo ticket.
- **Cuerpo de la solicitud:**
  ```json
  {
    "userId": "123e4567-e89b-12d3-a456-426614174000",
    "estatus": "ABIERTO"
  }

- **Respuesta**: 
  - **Código 201**: Ticket creado exitosamente.
    ```json
    {
      "message": "Ticket created successfully. Ticket ID: 123e4567-e89b-12d3-a456-426614174001"
    }
    ```
  - **Código 400**: Bad request.
    ```json
    {
      "message": "Message specifying the error in the request."
    }
    ```

#### Actualiza un ticket existente

- **Método:** PUT
- **Ruta:** /api/v1/tickets/{id}
- **Descripción:** Actualiza un ticket existente por su ID.
- **Cuerpo de la solicitud:**
  ```json
  {
    "userId": "123e4567-e89b-12d3-a456-426614174000",
    "estatus": "CERRADO"
  }

- **Respuesta**: 
  - **Código 201**: Ticket actualizado exitosamente.
    ```json
    {
      "message": "Ticket updated successfully."
    }
    ```
  - **Código 404**: Ticket no encontrado.
    ```json
    {
      "message": "Ticket not found with id: 123e4567-e89b-12d3-a456-426614174000."
    }
    ```
  - **Código 400**: Bad request.
    ```json
    {
      "message": "Message specifying the error in the request."
    }
    ```
    
#### Elimina un ticket

- **Método:** DELETE
- **Ruta:** /api/v1/tickets/{id}
- **Descripción:** Elimina un ticket por su ID.
- **Respuesta**: 
  - **Código 200**: Ticket borrado exitosamente.
    ```json
    {
      "message": "Ticket deleted successfully."
    }
    ```
  - **Código 404**: Ticket no encontrado.
    ```json
    {
      "message": "Ticket not found with id: 123e4567-e89b-12d3-a456-426614174000."
    }
    ```

#### Obten un ticket por su ID

- **Método:** GET
- **Ruta:** /api/v1/tickets/{id}
- **Descripción:** Obtiene un ticket específico por su ID.
- **Respuesta**: 
  - **Código 200**: Detalles del ticket.
    ```json
    {
      "id": "123e4567-e89b-12d3-a456-426614174000",
      "user": {
        "id": "123e4567-e89b-12d3-a456-426614174000",
        "name": "Brian",
        "lastname": "Narvaez"
      },
      "creationDate": "2025-01-23T14:30:00",
      "updateDate": "2025-01-23T15:00:00",
      "estatus": "ABIERTO"
    }
    ```
  - **Código 404**: Ticket no encontrado.
    ```json
    {
      "message": "Ticket not found with id: 123e4567-e89b-12d3-a456-426614174000."
    }
    ```
    
#### Obtener todos los tickets

- **Método:** GET
- **Ruta:** /api/v1/tickets
- **Descripción:** Obtiene todos los tickets con paginación.
- **Parametros de consulta:**
  - page: Número de página.
  - size: Tamaño de página.
- **Respuesta**: 
  - **Código 200**: Lista de tickets con paginación.
    ```json
    {
      "content": [
        {
          "id": "123e4567-e89b-12d3-a456-426614174000",
          "user": {
            "id": "123e4567-e89b-12d3-a456-426614174000",
            "name": "Brian",
            "lastname": "Narvaez"
          },
          "creationDate": "2025-01-23T14:30:00",
          "updateDate": "2025-01-23T15:00:00",
          "estatus": "ABIERTO"
        }
      ],
      "pageNumber": 1,
      "pageSize": 10,
      "totalElements": 100,
      "totalPages": 10
    }
    ```
  - **Código 400**: Bad request.
    ```json
    {
      "message": "Message specifying the error in the request."
    }
    ```

#### Obtener tickets por estatus

- **Método:** GET
- **Ruta:** /api/v1/tickets/estatus/{estatus}
- **Descripción:** Obtiene todos los tickets por estatus.
- **Respuesta**: 
  - **Código 200**: Detalles de los tickets.
    ```json
    [
      {
        "id": "123e4567-e89b-12d3-a456-426614174000",
        "user": {
          "id": "123e4567-e89b-12d3-a456-426614174000",
          "name": "Brian",
          "lastname": "Narvaez"
        },
        "creationDate": "2025-01-23T14:30:00",
        "updateDate": "2025-01-23T15:00:00",
        "estatus": "CERRADO"
      }
    ]
    ```
    
#### Obtener tickets por usuario

- **Método:** GET
- **Ruta:** /api/v1/tickets/user/{userid}
- **Descripción:** Obtiene todos los tickets por usuario.
- **Respuesta**: 
  - **Código 200**: Detalles de los tickets.
    ```json
    [
      {
        "id": "123e4567-e89b-12d3-a456-426614174000",
        "user": {
          "id": "123e4567-e89b-12d3-a456-426614174000",
          "name": "Brian",
          "lastname": "Narvaez"
        },
        "creationDate": "2025-01-23T14:30:00",
        "updateDate": "2025-01-23T15:00:00",
        "estatus": "ABIERTO"
      }
    ]

    ```
    
#### Obtener tickets por estatus y usuario

- **Método:** GET
- **Ruta:** /api/v1/tickets/estatus/{estatus}/user/{userid}
- **Descripción:** Obtiene todos los tickets por usuario.
- **Respuesta**: 
  - **Código 200**: Detalles de los tickets.
    ```json
    [
      {
        "id": "123e4567-e89b-12d3-a456-426614174000",
        "user": {
          "id": "123e4567-e89b-12d3-a456-426614174000",
          "name": "Brian",
          "lastname": "Narvaez"
        },
        "creationDate": "2025-01-23T14:30:00",
        "updateDate": "2025-01-23T15:00:00",
        "estatus": "ABIERTO"
      }
    ]
    ```
  - **Código 404**: Usuartio no encontrado.
    ```json
    {
      "message": "User not found with id: 123e4567-e89b-12d3-a456-426614174000."
    }
    ```

La documentación de las APIa está disponible en Swagger. Para acceder a ella, inicia la aplicación y navega a http://localhost:8081/swagger-ui/index.html
