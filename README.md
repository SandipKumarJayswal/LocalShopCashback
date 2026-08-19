# LocalShop Cashback System

A full-stack cashback rewards platform built with Spring Boot, Spring Data JPA, and MySQL. Users register, make purchases at registered shops, and automatically earn tiered cashback rewards tracked in a digital wallet — with a fully functional frontend and OTP-based password recovery.

## Features
- User registration & login (Customer / Shop Owner roles)
- Shop registration and listing
- Tiered cashback calculation engine (5% / 7% / 10% based on purchase amount)
- Digital wallet with real-time balance tracking
- Transaction history with filtering and sorting (Java 8 Streams)
- OTP-based forgot password flow (backend-generated OTP for secure password reset)
- Responsive frontend UI (HTML5, CSS3, JavaScript) with a modern 3D-styled design, fully connected to the backend via REST APIs

## Tech Stack
- **Language:** Java, Java 8
- **Backend:** Spring Boot, Spring Data JPA, Spring MVC, RESTful APIs
- **Frontend:** HTML5, CSS3, JavaScript (Fetch API for backend integration)
- **Database:** MySQL
- **Tools:** Maven, Postman, Git/GitHub

## Architecture


## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users/register` | Register a new user |
| POST | `/api/users/login` | User login |
| POST | `/api/users/send-otp` | Send OTP for password reset |
| POST | `/api/users/reset-password` | Verify OTP and reset password |
| POST | `/api/shops` | Register a new shop |
| GET | `/api/shops` | Get all shops |
| POST | `/api/transactions` | Add a purchase transaction (triggers cashback) |
| GET | `/api/transactions/{userId}` | Get user's transaction history |
| GET | `/api/wallet/{userId}` | Get wallet balance |

## How to Run
1. Clone the repository
2. Set your MySQL password in `src/main/resources/application.properties`
3. Run `CashbackApplication.java`
4. Open `frontend/register.html` in a browser to use the app, or test endpoints using Postman

## Author
Sandip Kumar Jayswal
