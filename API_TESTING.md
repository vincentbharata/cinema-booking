# Cinema Booking System - API Testing Guide

## Application Status
✅ **Running on:** `http://localhost:8082`  
✅ **Framework:** Spring Boot 3.2.1  
✅ **Database:** Oracle 11g (Schema: `cinema`)  
✅ **Build Status:** SUCCESS  

---

## Base URL
```
http://localhost:8082
```

---

## Film API Endpoints

### 1. Get All Films
```http
GET /api/films
```
**Response:**
```json
{
  "success": true,
  "message": "Films retrieved successfully",
  "data": [
    {
      "filmId": 1,
      "filmName": "The Shawshank Redemption",
      "filmGenre": "Drama",
      "filmDuration": 142
    }
  ]
}
```

### 2. Get Film by ID
```http
GET /api/films/{id}
```
**Example:** `GET /api/films/1`

### 3. Create New Film
```http
POST /api/films
Content-Type: application/json

{
  "filmName": "Oppenheimer",
  "filmGenre": "History",
  "filmDuration": 180
}
```
**Response:**
```json
{
  "success": true,
  "message": "Film created successfully",
  "data": {
    "filmId": 4,
    "filmName": "Oppenheimer",
    "filmGenre": "History",
    "filmDuration": 180
  }
}
```

### 4. Update Film
```http
PUT /api/films/{id}
Content-Type: application/json

{
  "filmName": "Oppenheimer Updated",
  "filmGenre": "Drama",
  "filmDuration": 181
}
```

### 5. Delete Film
```http
DELETE /api/films/{id}
```
**Example:** `DELETE /api/films/1`

### 6. Search Films by Name (QueryDSL)
```http
GET /api/films/search/name?name=Shawshank
```

### 7. Search Films by Genre (QueryDSL)
```http
GET /api/films/search/genre?genre=Drama
```

### 8. Search Films by Minimum Duration (QueryDSL)
```http
GET /api/films/search/duration?duration=120
```

---

## Ticket API Endpoints

### 1. Get All Tickets
```http
GET /api/tickets
```

### 2. Create Ticket
```http
POST /api/tickets
Content-Type: application/json

{
  "filmId": 1,
  "ticketPrice": 150000.00,
  "ticketQuota": 100
}
```

### 3. Update Ticket
```http
PUT /api/tickets/{id}
Content-Type: application/json

{
  "filmId": 1,
  "ticketPrice": 160000.00,
  "ticketQuota": 95
}
```

### 4. Get Available Tickets (QueryDSL)
```http
GET /api/tickets/search/available
```

### 5. Search Tickets by Price Range (QueryDSL)
```http
GET /api/tickets/search/price?minPrice=100000&maxPrice=200000
```

### 6. Search Tickets by Film (QueryDSL)
```http
GET /api/tickets/search/film/{filmId}
```
**Example:** `GET /api/tickets/search/film/1`

---

## Booking API Endpoints

### 1. Get All Bookings
```http
GET /api/bookings
```

### 2. Create Booking
```http
POST /api/bookings
Content-Type: application/json

{
  "ticketId": 1,
  "bookingAmount": 5
}
```
**Response:**
```json
{
  "success": true,
  "message": "Booking created successfully",
  "data": {
    "bookingId": 1,
    "ticketId": 1,
    "bookingAmount": 5,
    "bookingTotalPrice": 750000.00
  }
}
```

### 3. Update Booking
```http
PUT /api/bookings/{id}
Content-Type: application/json

{
  "ticketId": 1,
  "bookingAmount": 3
}
```

### 4. Delete Booking (Release Tickets)
```http
DELETE /api/bookings/{id}
```

### 5. Search Bookings by Minimum Amount (QueryDSL)
```http
GET /api/bookings/search/amount?amount=5
```

### 6. Search Bookings by Price Range (QueryDSL)
```http
GET /api/bookings/search/price?minPrice=500000&maxPrice=1000000
```

### 7. Search Bookings by Ticket (QueryDSL)
```http
GET /api/bookings/search/ticket/{ticketId}
```
**Example:** `GET /api/bookings/search/ticket/1`

---

## Testing with cURL

### Test Get All Films
```bash
curl -X GET http://localhost:8082/api/films
```

### Test Create Film
```bash
curl -X POST http://localhost:8082/api/films \
  -H "Content-Type: application/json" \
  -d '{
    "filmName": "Avatar 3",
    "filmGenre": "Sci-Fi",
    "filmDuration": 165
  }'
```

### Test Search by QueryDSL
```bash
curl -X GET "http://localhost:8082/api/films/search/genre?genre=Drama"
```

### Test Create Booking
```bash
curl -X POST http://localhost:8082/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "ticketId": 1,
    "bookingAmount": 10
  }'
```

---

## Error Responses

### 400 Bad Request
```json
{
  "success": false,
  "message": "Invalid input",
  "error": "Validation failed"
}
```

### 404 Not Found
```json
{
  "success": false,
  "message": "Resource not found",
  "data": null
}
```

### 500 Server Error
```json
{
  "success": false,
  "message": "An error occurred",
  "error": "Internal server error details"
}
```

---

## QueryDSL Features

All search endpoints utilize **QueryDSL** for type-safe query building:

### Film Searches
- `searchByName(String name)` - Case-insensitive name search
- `searchByGenre(String genre)` - Case-insensitive genre search
- `searchByMinDuration(Integer duration)` - Films with duration >= specified minutes

### Ticket Searches
- `searchByPriceRange(Double minPrice, Double maxPrice)` - Price range filter
- `findAvailableTickets()` - Tickets with quota > 0
- `findByFilm(Long filmId)` - Tickets for specific film

### Booking Searches
- `findByMinAmount(Integer amount)` - Bookings with amount >= specified
- `findByTotalPriceRange(Double minPrice, Double maxPrice)` - Price range filter
- `findByTicket(Long ticketId)` - Bookings for specific ticket

---

## Sample Workflow

### Step 1: Create a Film
```bash
POST /api/films
{
  "filmName": "Dune Part Two",
  "filmGenre": "Sci-Fi",
  "filmDuration": 166
}
```
Response: `filmId: 1`

### Step 2: Create Tickets for the Film
```bash
POST /api/tickets
{
  "filmId": 1,
  "ticketPrice": 200000.00,
  "ticketQuota": 200
}
```
Response: `ticketId: 1`

### Step 3: Create a Booking
```bash
POST /api/bookings
{
  "ticketId": 1,
  "bookingAmount": 15
}
```
Response: `Booking created with total price: 3,000,000.00`

### Step 4: Search Bookings by Price Range
```bash
GET /api/bookings/search/price?minPrice=2000000&maxPrice=5000000
```
Response: Returns bookings within price range

---

## Notes

- All timestamps are in ISO 8601 format
- Prices are in IDR (Indonesian Rupiah)
- Quota represents available seats; it decreases on booking and increases on deletion
- QueryDSL ensures type-safe queries and prevents SQL injection
- All entities use Oracle sequences for ID generation
- Schema: All tables are in the `cinema` schema

