# Spring Security Application

This project is a Spring Boot application that provides robust security features, including user authentication, registration, and account management through a set of RESTful APIs. The application uses Docker Compose to run a MySQL database, enabling easy setup and deployment.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Folder Structure](#folder-structure)
- [API Endpoints](#api-endpoints)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- User Registration
- User Login
- User Account Management
- Secure JWT Authentication
- Global Exception Handling

## Technologies Used

- **Spring Boot**: Framework for building Java applications
- **Spring Security**: For securing APIs
- **Docker**: Containerization of the application
- **MySQL**: Database for storing user information
- **Lombok**: To reduce boilerplate code
- **Maven**: Dependency management

## Folder Structure

```
.
├── main
│   ├── java
│   │   └── org
│   │       └── marouanedbibih
│   │           └── springsecurity
│   │               ├── account
│   │               ├── auth
│   │               ├── exception
│   │               ├── handler
│   │               ├── interfaces
│   │               ├── jwt
│   │               ├── security
│   │               ├── user
│   │               └── utils
│   └── resources
└── test
```

- **account**: Contains account-related controllers and services.
- **auth**: Handles authentication requests and responses.
- **exception**: Custom exception classes.
- **handler**: Global exception handling.
- **interfaces**: Interface definitions.
- **jwt**: JWT utility classes for token management.
- **security**: Security configuration and custom user details service.
- **user**: User-related functionality including controllers, DTOs, and repositories.
- **utils**: Common utility classes.

## API Endpoints

### Authentication

- **POST** `/auth/register` - Register a new user
- **POST** `/auth/login` - Authenticate user and return JWT

### User Management

- **GET** `/user/{id}` - Get user details by ID
- **PUT** `/user/{id}` - Update user details
- **DELETE** `/user/{id}` - Delete a user

### Account Management

- **PUT** `/account/update-password` - Update user password
- **GET** `/account/details` - Get account details

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/spring-security.git
   cd spring-security/backend
   ```

2. Ensure you have [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/) installed.

3. Start the MySQL database:
   ```bash
   docker-compose up -d
   ```

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Usage

- Use Postman or any other API testing tool to interact with the endpoints.
- Make sure to set the appropriate headers, including `Authorization` with the JWT token for protected routes.

## Contributing

Contributions are welcome! Please create a pull request or open an issue for any enhancements or bugs.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.