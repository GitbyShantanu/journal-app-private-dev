# 📝 JournalApp — (Development)

> A **Spring Boot** + **MongoDB Atlas** powered journaling backend built from scratch with clean architecture, real transactions, authentication, and automation.

---

## 📖 Overview

**JournalApp** is a backend system that lets users securely create and manage journal entries —  
designed with **layered architecture**, **RESTful APIs**, and **production-ready modules** including security, testing, and scheduling.

💡 What started as a simple CRUD project evolved into a **complete backend learning journey**.

---

## ⚙️ Architecture  

- **Controller → Service → Repository → Entity**
- Modular design for **User** and **Journal** services
- DTO mapping with `ResponseEntity` for consistent responses
- Configurable through `application.yml` using **dev/prod profiles**
- Connected to **MongoDB Atlas** for live cloud storage

---

## 🧩 Core Features

| Category | Description |
|-----------|-------------|
| 🗄️ **Database** | Live **MongoDB Atlas** connection using `@DBRef` relationships |
| 🔁 **Transactions** | Used `@Transactional` for atomic multi-document operations (Atlas-backed) |
| 🔐 **Authentication** | Implemented **Spring Security** (Basic Auth + Role-Based Access) |
| 🌦️ **API Integration** | Added external **Weather API** using `RestTemplate` |
| 🧪 **Testing** | Created unit tests with **JUnit 5** & **Mockito** |
| 🧹 **Code Quality** | Integrated **SonarQube + SonarCloud** for static code analysis |
| ⏰ **Automation** | Built **Spring Scheduler** for periodic email triggers |
| 📧 **Mail Service** | Configured **Spring Mail (SMTP)** for email notifications |
| 💬 **Sentiment Analysis** | Implemented via `MongoTemplate` + dynamic Criteria queries |
| 🪵 **Logging & Profiles** | Separate **dev/prod** profiles with **SLF4J** logging |

---

## 🧠 Learning Highlights

- Explored **transactional consistency** and its unique behavior in MongoDB
- Understood **authentication flow**, filters, and role hierarchies in Spring Security
- Faced and solved real backend issues — Atlas IP whitelisting, build configs, DTO mapping
- Learned to build beyond CRUD — focusing on **scalability, maintainability, and clarity**
- Realized that backend isn’t just about code — it’s **system design thinking**

---

## 🔮 Upcoming Additions

🚧 The journey continues with upcoming modules:

- 🧰 **Redis** caching
- ⚡ **Kafka** event streaming
- 🔑 **JWT Authentication**
- 📘 **Swagger** documentation
- ☁️ **Heroku Deployment**

---

## 🧰 Tech Stack

| Category | Technologies                       |
|-----------|------------------------------------|
| ☕ **Language** | Java 22                            |
| 🧱 **Framework** | Spring Boot                        |
| 🗄️ **Database** | MongoDB Atlas                      |
| 🔐 **Security** | Spring Security (Basic Auth, RBA)  |
| 📧 **Mail & Automation** | Spring Mail, Spring Scheduler      |
| 🧪 **Testing** | JUnit 5, Mockito                   |
| 🧹 **Code Quality** | SonarQube, SonarCloud              |
| 🧰 **Tools** | IntelliJ IDEA, Postman, Maven, Git |

<p align="center">
  <img src="https://img.shields.io/badge/Java-22-007396?logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring_Boot-2.7.x-6DB33F?logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/MongoDB-Atlas-47A248?logo=mongodb&logoColor=white"/>
  <img src="https://img.shields.io/badge/SonarQube-Integrated-4E9BCD?logo=sonarqube&logoColor=white"/>
  <img src="https://img.shields.io/badge/Postman-Tested-orange?logo=postman&logoColor=white"/>
</p>

---

## 🧑‍💻 Author

**Shantanu Deshmukh**  
🎯 *Java Backend Developer | B.Tech (IT) 2025*  
🔗 [GitHub – GitbyShantanu](https://github.com/GitbyShantanu)

---

⭐ **If this project inspired you — give it a star!**  
It’s not just a backend… it’s a journey from “Hello World” to “System Design Thinking.” 🌟
