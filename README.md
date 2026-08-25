# Contact Management Application

A full-stack contact management system built with **Angular 22** frontend, **Spring Boot 3** backend, and **MySQL** database. 
Features complete CRUD operations, JWT authentication, and modern Angular best practices.

---

## Quick Start

### Prerequisites
- **Node.js**: v18+ and npm v11+
- **Java**: JDK 17+
- **Maven**: 3.9+
- **MySQL**: 8.0+

### 1️⃣ Setup Database
```bash
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE contact_management;
exit;

# Initialize schema
cd config
mysql -u root -p contact_management < init-db.sql
```

### 2️⃣ Start Backend (Terminal 1)
```bash
cd backend
mvn clean install
```
✅ Backend runs on: `http://localhost:8080/api`

### 3️⃣ Start Frontend (Terminal 2)
```bash
cd frontend
npm install
npm start
```
✅ Frontend runs on: `http://localhost:4200`

### 4️⃣ Login
```
Username: testuser
Password: password123
```

---

## Detailed Setup Instructions

### Step 1: Create Database

```sql
-- User Table
CREATE TABLE user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Contact Table
CREATE TABLE contact (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  address VARCHAR(255),
  phone_number VARCHAR(20),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- Insert test user
INSERT INTO user (username, password, email) VALUES 
('testuser', '$2a$10$slYQmyNdGzin7olVN3p5be4DlH.PKZbv5H8KnzzVgXXbVxzy3QFRY', 'testuser@example.com');
```

Expected output:
```
Tables_in_contact_management
contact
user
```

---

## Backend Setup

### Step 1: Navigate to Backend Directory

```bash
cd backend
```

### Step 2: Update Database Configuration

Edit: `src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/contact_management
    username: root
    password: your_mysql_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: validate
    database-platform: org.hibernate.dialect.MySQL8Dialect
    show-sql: false
  application:
    name: contact-management-app

jwt:
  secret: "your-secret-key-minimum-72-characters-long-for-hs512-algorithm-use-openssl-rand-base64-64"
  expiration: 86400000

server:
  port: 8080
  servlet:
    context-path: /api
```
Build Project using 

`mvn clean install`

Expected output:
```
[INFO] Building jar: target/contact-app-api-1.0.0.jar
[INFO] BUILD SUCCESS
```

### Step 4: Run Backend

** Using Compiled JAR**
```bash
java -jar target/contact-app-api-1.0.0.jar
```

**Startup Success Indicator:**
```
Started ContactManagementAppApplication in 5.234 seconds
```

### Verify Backend

```bash
# Test login endpoint
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# Expected response
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user": {
    "id": 1,
    "username": "testuser",
    "email": "testuser@example.com"
  }
}
```

---

## Frontend Setup

### Step 1: Navigate to Frontend Directory

```bash
cd frontend
```

### Step 2: Install Dependencies

```bash
npm install
```

**Expected Output:**
```
added 1234 packages in 45s
```

### Step 3: Update Environment Configuration (Optional)

Edit: `src/environments/environment.ts`

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

### Step 4: Run Development Server

```bash
npm start
```

**Expected Output:**
```
✔ Compiled successfully.

Local:        http://localhost:4200/
external:     http://192.168.x.x:4200/
```

### Step 5: Access Application

Open browser and navigate to: `http://localhost:4200`

---

## Usage Guide

### Login
1. Enter Username: `testuser`
2. Enter Password: `password123`
3. Click "Login"
4. You'll be redirected to Dashboard

### View Contacts
1. Click "Contacts" in navigation
2. All your contacts will be displayed in a table

### Add Contact
1. Click "Contacts"
2. Fill in the form:
   - Name (min 2 characters)
   - Address (min 5 characters)
   - Phone Number (valid format)
3. Click "Add Contact"

### Edit Contact
1. In Contacts table, click "Edit" button
2. Form will populate with current data
3. Make changes
4. Click "Update Contact"
5. Click "Cancel" to discard changes

### Delete Contact
1. In Contacts table, click "Delete" button
2. Confirm deletion
3. Contact is removed

### Logout
1. Click user menu (top-right)
2. Click "Logout"
3. You'll be redirected to login page

---

## API Endpoints

### Base URL
```
http://localhost:8080/api
```

### Authentication Endpoints

#### Register User
```bash
POST /auth/register
Content-Type: application/json

{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "password123"
}
```

#### Login
```bash
POST /auth/login
Content-Type: application/json

{
  "username": "testuser",
  "password": "password123"
}

# Response
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user": {
    "id": 1,
    "username": "testuser",
    "email": "testuser@example.com"
  }
}
```

### Contact Endpoints

**All require Authorization header:**
```bash
Authorization: Bearer {token}
```

#### Get All Contacts
```bash
GET /contacts

# Response
[
  {
    "id": 1,
    "name": "John Doe",
    "address": "123 Main St, New York, NY",
    "phoneNumber": "+1-212-555-0100"
  }
]
```

#### Get Contact by ID
```bash
GET /contacts/1

# Response
{
  "id": 1,
  "name": "John Doe",
  "address": "123 Main St, New York, NY",
  "phoneNumber": "+1-212-555-0100"
}
```

#### Create Contact
```bash
POST /contacts
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "John Doe",
  "address": "123 Main St, New York, NY",
  "phoneNumber": "+1-212-555-0100"
}

# Response (201 Created)
{
  "id": 1,
  "name": "John Doe",
  "address": "123 Main St, New York, NY",
  "phoneNumber": "+1-212-555-0100"
}
```

#### Update Contact
```bash
PUT /contacts/1
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Jane Doe",
  "address": "456 Oak Ave, Los Angeles, CA",
  "phoneNumber": "+1-310-555-0200"
}

# Response (200 OK)
{
  "id": 1,
  "name": "Jane Doe",
  "address": "456 Oak Ave, Los Angeles, CA",
  "phoneNumber": "+1-310-555-0200"
}
```

#### Delete Contact
```bash
DELETE /contacts/1
Authorization: Bearer {token}

# Response (204 No Content)
```

---

## Project Structure

```
contact-management-app/
│
├── frontend/                          # Angular 22 Application
│   ├── src/
│   │   ├── app/
│   │   │   ├── pages/
│   │   │   │   ├── login/             # Login page
│   │   │   │   │   ├── login.component.ts
│   │   │   │   │   ├── login.component.html
│   │   │   │   │   ├── login.component.css
│   │   │   │   │   └── login.component.spec.ts
│   │   │   │   ├── dashboard/         # Dashboard page
│   │   │   │   │   ├── dashboard.component.ts
│   │   │   │   │   ├── dashboard.component.html
│   │   │   │   │   └── dashboard.component.css
│   │   │   │   └── contacts/          # Contacts management
│   │   │   │       ├── contacts.component.ts
│   │   │   │       ├── contacts.component.html
│   │   │   │       ├── contacts.component.css
│   │   │   │       └── contacts.component.spec.ts
│   │   │   ├── services/
│   │   │   │   ├── auth.service.ts    # Authentication service
│   │   │   │   └── contact.service.ts # Contact CRUD operations
│   │   │   ├── guards/
│   │   │   │   └── auth.guard.ts      # Route protection
│   │   │   ├── app.component.ts       # Root component
│   │   │   ├── app.routes.ts          # Route definitions
│   │   │   └── app.config.ts          # App configuration
│   │   ├── environments/
│   │   │   ├── environment.ts         # Dev environment
│   │   │   └── environment.prod.ts    # Prod environment
│   │   ├── main.ts                    # Entry point
│   │   └── styles.css                 # Global styles
│   ├── jest.config.js                 # Jest configuration
│   ├── tsconfig.json                  # TypeScript config
│   ├── package.json                   # npm dependencies
│   └── angular.json                   # Angular config
│
├── backend/                           # Spring Boot 3 Application
│   ├── src/main/java/com/contactapp/
│   │   ├── entity/
│   │   │   ├── User.java              # User entity
│   │   │   └── Contact.java           # Contact entity
│   │   ├── controller/
│   │   │   ├── AuthController.java    # Auth endpoints
│   │   │   └── ContactController.java # Contact endpoints
│   │   ├── service/
│   │   │   ├── AuthService.java       # Auth service
│   │   │   └── ContactService.java    # Contact service
│   │   ├── repository/
│   │   │   ├── UserRepository.java    # User repository
│   │   │   └── ContactRepository.java # Contact repository
│   │   ├── config/
│   │   │   └── SecurityConfig.java    # Spring Security config
│   │   ├── security/
│   │   │   └── JwtTokenProvider.java  # JWT handling
│   │   └── ContactManagementAppApplication.java
│   ├── src/main/resources/
│   │   └── application.yml            # Application config
│   └── pom.xml                        # Maven dependencies
│
├── config/
│   └── init-db.sql                    # Database init script
│
├── JEST_SETUP.md                      # Jest testing guide
├── README.md                          # This file
└── .gitignore                         # Git ignore rules
```

---

## Tech Stack & Versions

### Frontend Stack
| Technology | Version | Purpose |
|-----------|---------|---------|
| Angular | 22.1.0 | Frontend framework |
| TypeScript | 6.0.2 | Language |
| RxJS | 7.8.0 | Reactive programming |
| Jest | 29.7.0 | Testing |

### Backend Stack
| Technology | Version | Purpose |
|-----------|---------|---------|
| Spring Boot | 3.2.0 | Backend framework |
| Java | 17 | Language |
| Spring Security | 3.2.0 | Authentication |
| JWT | jjwt 0.11.5 | Token handling |
| Hibernate | 6.2.x | ORM |
| MySQL Driver | 8.0.x | Database |

### Database
| Technology | Version | Purpose |
|-----------|---------|---------|
| MySQL | 8.0+ | Database |
