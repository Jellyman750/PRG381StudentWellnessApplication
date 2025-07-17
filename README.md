# BC Student Wellness Management System
## 👥 Team Members

- [Erick Knoetze]
- [Glacious Mukwevho]
- [Kyle Colin Haynes-Smart]
- [Tarina Snyman]

---

## 📌 Project Overview

This project is part of the PRG3781 2025 assessment and demonstrates the application of:
- Core Java and Object-Oriented Programming (OOP)
- GUI development using Java Swing
- Web development using JSP and Servlets
- Database integration using PostgreSQL and JavaDB

## 🧩 Project Structure

The system is divided into two main components:

---

### 🌐 Milestone 1: Web Application – Login & Registration System

#### Technologies Used:
- JSP, Servlets
- PostgreSQL
- HTML/CSS

#### Features:
- `index.jsp` - Homepage with navigation
- `register.jsp` - Student registration form
- `login.jsp` - Login form
- `dashboard.jsp` - Welcome screen post-login

#### Functional Highlights:
- Secure user registration with input validation and hashed passwords
- Login with session management
- PostgreSQL-backed user storage

#### Database Schema:
- `Users` table:
  - `student_number` (PK)
  - `name`
  - `surname`
  - `email` (UNIQUE)
  - `phone`
  - `password`

---

### 🖥️ Milestone 2: Desktop Application – Wellness Management System

#### Technologies Used:
- Java Swing
- JavaDB (Apache Derby)

#### Features:
- Appointment Management
- Counselor Management
- Feedback Management
- Tab-based or menu navigation

#### Functional Highlights:
- Book, update, and cancel appointments
- Manage counselor availability and details
- Submit, view, edit, and delete feedback
- Input validation, exception handling, confirmation dialogs

#### Database Tables:
- `Appointments` 
- `Counselors` 
- `Feedback` 
