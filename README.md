# 📋 Task Management System

A full-stack task management application with **Spring Boot** (Backend) and **Angular** (Frontend).

## ✨ Features

- ✅ User Login & Registration
- ✅ Create, Read, Update, Delete Tasks
- ✅ Task Status: PENDING, IN_PROGRESS, COMPLETED, EXPIRED
- ✅ Due Date with Alerts (24h, 12h, 6h, 1h before)
- ✅ Automatic Task Expiration
- ✅ Browser Notifications for Alerts
- ✅ Responsive UI

## 🛠️ Tech Stack

| Backend | Frontend |
|---------|----------|
| Java 17 | Angular 17 |
| Spring Boot 3.1.5 | TypeScript |
| Spring Data JPA | HTML5 + CSS3 |
| Spring Security | |
| MySQL | |
| Maven | |

## 🚀 Quick Start

1. Clone the Repository
```bash
git clone https://github.com/Vidhya12-v/Task_Manager.git
cd Task_Manager

2. Run Backend
```bash
cd task-backend
# Update application.properties with your MySQL password
mvn clean install
mvn spring-boot:run
Backend runs on: http://localhost:8081

3. Run Frontend
```bash
cd task-frontend
npm install
ng serve
Frontend runs on: http://localhost:4200
```

## 📁 Project Structure

Task_Manager/
├── task-backend/          # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/      # Java source code
│   │   │   └── resources/ # Config files
│   │   └── test/
│   └── pom.xml
│
├── task-frontend/         # Angular Frontend
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/ # Login & Task List
│   │   │   ├── models/    # TypeScript models
│   │   │   └── services/  # API services
│   │   ├── index.html
│   │   └── styles.css
│   ├── package.json
│   └── angular.json
│
└── README.md


### 📝 License
This project is for learning purposes.



