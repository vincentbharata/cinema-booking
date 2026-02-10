# Schema Cinema Configuration Guide

## Ringkasan
Aplikasi Cinema Learn telah dikonfigurasi untuk menyimpan semua data di schema Oracle bernama **"cinema"**.

## Struktur Schema

### Schema "cinema" Contains:
```
cinema.films           - Tabel film
cinema.tickets         - Tabel ticket  
cinema.bookings        - Tabel booking
cinema.film_seq        - Sequence untuk film_id
cinema.ticket_seq      - Sequence untuk ticket_id
cinema.booking_seq     - Sequence untuk booking_id
```

## Konfigurasi

### 1. Database Connection (application.properties)
```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=system
spring.datasource.password=cinema123

# Default schema untuk semua entities
spring.jpa.properties.hibernate.default_schema=cinema
```

### 2. Entity Configuration
Semua entities sudah dikonfigurasi dengan schema "cinema":

```java
@Entity
@Table(name = "films", schema = "cinema")
public class Film { ... }

@Entity
@Table(name = "tickets", schema = "cinema")
public class Ticket { ... }

@Entity
@Table(name = "bookings", schema = "cinema")
public class Booking { ... }
```

### 3. Sequence Generators
Sequence generators juga menggunakan prefix "cinema":

```java
@SequenceGenerator(name = "film_seq", sequenceName = "cinema.film_seq", allocationSize = 1)
@SequenceGenerator(name = "ticket_seq", sequenceName = "cinema.ticket_seq", allocationSize = 1)
@SequenceGenerator(name = "booking_seq", sequenceName = "cinema.booking_seq", allocationSize = 1)
```

## SQL Setup

### Membuat Schema Baru (Jika belum ada)
```sql
-- Run dari sqlplus atau SQL Developer
CREATE SCHEMA cinema;
```

### Creating Tables & Sequences
File: `src/main/resources/schema.sql` - Sudah terupdate

Scripts yang dijalankan:
```sql
CREATE SCHEMA cinema;
CREATE SEQUENCE cinema.film_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE cinema.ticket_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE cinema.booking_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE cinema.films (
    film_id NUMBER PRIMARY KEY,
    film_name VARCHAR2(255) NOT NULL,
    film_genre VARCHAR2(100),
    film_duration NUMBER NOT NULL
);

CREATE TABLE cinema.tickets (
    ticket_id NUMBER PRIMARY KEY,
    film_id NUMBER NOT NULL,
    ticket_price NUMBER(10,2) NOT NULL,
    ticket_quota NUMBER NOT NULL,
    CONSTRAINT fk_ticket_film FOREIGN KEY (film_id) REFERENCES cinema.films(film_id)
);

CREATE TABLE cinema.bookings (
    booking_id NUMBER PRIMARY KEY,
    ticket_id NUMBER NOT NULL,
    booking_amount NUMBER NOT NULL,
    booking_total_price NUMBER(10,2) NOT NULL,
    CONSTRAINT fk_booking_ticket FOREIGN KEY (ticket_id) REFERENCES cinema.tickets(ticket_id)
);
```

### Data Insertion
Sample data automatically inserted ke schema cinema:
```sql
INSERT INTO cinema.films VALUES (cinema.film_seq.NEXTVAL, 'Avengers Endgame', 'Action', 181);
INSERT INTO cinema.films VALUES (cinema.film_seq.NEXTVAL, 'The Lion King', 'Animation', 118);
INSERT INTO cinema.films VALUES (cinema.film_seq.NEXTVAL, 'Inception', 'Sci-Fi', 148);
-- ... etc
```

## Mengubah Schema (Optional)

Jika ingin menggunakan schema berbeda:

### 1. Update application.properties
```properties
spring.jpa.properties.hibernate.default_schema=YOUR_SCHEMA_NAME
```

### 2. Update Entity Annotations
```java
@Table(name = "films", schema = "YOUR_SCHEMA_NAME")
```

### 3. Update Sequences di schema.sql
```sql
CREATE SEQUENCE YOUR_SCHEMA_NAME.film_seq ...
CREATE TABLE YOUR_SCHEMA_NAME.films ...
```

## Keuntungan Menggunakan Schema

1. **Data Organization** - Semua data cinema terintegrasi dalam satu schema
2. **Namespace Separation** - Tidak ada conflict dengan table lain di database
3. **Security** - Bisa set permission per-schema
4. **Maintainability** - Lebih mudah manage, backup, restore
5. **Multi-tenancy Ready** - Bisa tambah schema lain untuk client berbeda

## Verifikasi di Database

### Cek schema dan tables (sqlplus):
```sql
-- List semua schemas
SELECT * FROM dba_users WHERE username = 'CINEMA';

-- List tables di cinema schema
SELECT table_name FROM dba_tables WHERE owner = 'CINEMA';

-- List sequences di cinema schema
SELECT sequence_name FROM dba_sequences WHERE sequence_owner = 'CINEMA';
```

## Troubleshooting

### Error: "Schema tidak ada"
- Pastikan schema "cinema" sudah dibuat
- Run schema.sql dari application startup atau manual

### Error: "Permission denied"
- Pastikan user (system) punya permission CREATE SCHEMA, CREATE TABLE, dll
- Atau buat schema dengan user yang punya privilege

### Error: "Sequence not found"
- Verify sequence exists: `SELECT * FROM user_sequences;`
- Re-run schema.sql untuk create sequences

## Testing Query di Schema

### Menggunakan SQL Developer atau sqlplus:
```sql
-- Connect sebagai system user
CONNECT system/cinema123@xe

-- Query film dari cinema schema
SELECT * FROM cinema.films;

-- Query dengan join
SELECT f.film_name, t.ticket_price, b.booking_amount
FROM cinema.films f
JOIN cinema.tickets t ON f.film_id = t.film_id
JOIN cinema.bookings b ON t.ticket_id = b.ticket_id;
```

## Database Architecture

```
Oracle XE (localhost:1521:xe)
└── System User
    └── cinema Schema
        ├── films table (+ constraints, indexes)
        ├── tickets table (+ constraints, indexes)
        ├── bookings table (+ constraints, indexes)
        ├── film_seq sequence
        ├── ticket_seq sequence
        └── booking_seq sequence
```

## Next Steps

1. ✅ Schema cinema sudah dikonfigurasi
2. ✅ Entities sudah menggunakan schema cinema
3. ✅ Application.properties sudah update
4. ⏭️ Jalankan `mvn spring-boot:run` untuk start aplikasi
5. ⏭️ Schema dan tables akan auto-create dari schema.sql

---

Semua data sekarang disimpan secara terorganisir di schema "cinema"! 🎬
