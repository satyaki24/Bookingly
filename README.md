# 🏨 Bookingly - Hotel Booking and Management System

**Bookingly** is a modern Spring Boot-based RESTful Hotel Booking and Management System built to streamline hotel management and enhance user booking experiences. With features like dynamic pricing, secure JWT authentication, role-based access, and Stripe payment integration, it offers a complete backend solution for a hotel booking and management platform.

---

## 📚 Table of Contents

- [Overview](#-overview)
- [Tech Stack](#-tech-stack)
- [Modules](#-modules)
- [User Roles](#-user-roles)
- [Authentication & Authorization](#-authentication--authorization)
- [Hotel Browsing & Booking (GUEST)](#-hotel-browsing--booking-guest)
- [Admin Functionality (HOTEL_MANAGER)](#admin-functionality-hotelmanager)
- [Room & Inventory Management](#-room--inventory-management)
- [Payment Flow (Stripe)](#-payment-flow-stripe)
- [Guest Management](#-guest-management)
- [Dynamic Pricing](#-dynamic-pricing)
- [Scheduled Tasks](#scheduled-tasks)
- [API Endpoints](#api-endpoints)

---

## 📖 Overview

BookingEase enables users to:

- 🔍 Search and book hotels
- 🧾 Manage bookings and guest details
- 💳 Make secure payments via **Stripe**
- 📈 Experience dynamic pricing based on real-time strategies

---

## 🧠 Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Security (JWT-based)**
- **Stripe API** (for payments)
- **PostgreSQL** (database)
- **Lombok**
- **Scheduled Tasks** (for background operations)

---

## 📦 Modules

- Authentication Module  
- Hotel Management Module  
- Room & Inventory Module  
- Booking Module  
- Guest Management Module  
- Pricing Module  
- Stripe Webhook Listener  
- Scheduled Task Executor  

---

## 👤 User Roles

### GUEST
- Sign up / Login
- Search and book hotels
- Manage bookings and guests

### HOTEL_MANAGER
- Create and manage hotels & rooms
- View bookings and generate reports
- Activate/deactivate hotel listings

---

## 🔐 Authentication & Authorization

- JWT-based access token with refresh token
- Secure cookie storage for refresh tokens

**Endpoints:**
- ```POST /auth/signup```        - Register new guest  
- ```POST /auth/login```         - Login and receive tokens  
- ```POST /auth/refresh```       - Refresh access token
## 🧳 Hotel Browsing & Booking (GUEST)

- ```GET  /hotels/searchAll```                      - Paginated hotel list  
- ```GET  /hotels/search```                         - Filtered hotel search  
- ```GET  /hotels/info/{hotelId}```                 - Get hotel details  
- ```POST /bookings/init```                         - Initialize booking  
- ```POST /bookings/{bookingId}/addGuests```        - Add guests to booking  
- ```POST /bookings/{bookingId}/payments```         - Start payment session  
- ```POST /bookings/{bookingId}/cancel```           - Cancel a booking  
- ```GET  /bookings/{bookingId}/status```           - Check booking status

## 🛠️ Admin Functionality (HOTEL_MANAGER)

- ```POST   /admin/hotels```                       - Create new hotel  
- ```PUT    /admin/hotels/{id}```                  - Update hotel details  
- ```PATCH  /admin/hotels/activate/{id}```         - Activate or deactivate hotel  
- ```GET    /admin/hotels/{id}/bookings```         - View all bookings  
- ```GET    /admin/hotels/{id}/reports```          - Generate booking reports  

## 🏨 Room & Inventory Management

- ```POST   /admin/hotels/{hotelId}/rooms```               - Add room to hotel  
- ```PUT    /admin/hotels/{hotelId}/rooms/{roomId}```      - Update room details  
- ```PATCH  /admin/inventory/rooms/{roomId}```             - Update room inventory  
- ```GET    /admin/inventory/rooms/{roomId}```             - View room inventory  

## 💳 Payment Flow (Stripe)
1. Initiate payment - ```POST /bookings/{id}/payments ```
2. Stripe Checkout session created
3. Stripe sends event → ```POST /webhook/payment```
4. Booking status updated to ```CONFIRMED```
5. Refunds handled via Stripe on cancellations

## 👥 Guest Management
- ```GET /users/guests``` – List guests
- ```POST /users/guests``` – Add guest
- ```PUT /users/guests/{id}``` – Update guest
- ```DELETE /users/guests/{id}``` – Delete guest

## 📈 Dynamic Pricing
Implemented using the Decorator Pattern with multiple strategies:

- ```BasePricingStrategy``` – Default base price
- ```SurgePricingStrategy``` – Increases during high demand
- ```OccupancyPricingStrategy``` – Adjusts based on availability
- ```UrgencyPricingStrategy``` – Increases near check-in date
- ```HolidayPricingStrategy``` – Premium pricing on holidays

```java
PricingStrategy strategy = new BasePricingStrategy();
strategy = new SurgePricingStrategy(strategy);
strategy = new OccupancyPricingStrategy(strategy);
strategy = new UrgencyPricingStrategy(strategy);
strategy = new HolidayPricingStrategy(strategy);
BigDecimal finalPrice = strategy.calculatePrice(inventory);
```
## ⏲️ Scheduled Tasks
### 🔸 Expire unpaid bookings every 15 minutes
```java
@Scheduled(cron = "0 */15 * * * *") // every 15 mins
public void expireBooking() {
    // logic to expire unpaid bookings
}
```
### 🔸 Update room pricing hourly
```java
@Scheduled(cron = "0 0 * * * *") // every hour
public void updatePrices() {
    // logic to update dynamic pricing
}
```
