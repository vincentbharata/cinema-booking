# QueryDSL Implementation Guide

## Ringkasan
Project Cinema Learn telah dikonfigurasi dengan **QueryDSL** untuk type-safe query building dengan fitur autocomplete dan compile-time validation.

## Apa itu QueryDSL?
QueryDSL adalah framework yang memungkinkan Anda membangun queries secara type-safe menggunakan fluent API, bukan String-based queries. Ini memberikan:
- ✅ Type safety (error detection saat compile time)
- ✅ IDE autocomplete
- ✅ Refactoring support
- ✅ Query reusability

## Q-Classes
QueryDSL telah menggenerate Q-classes untuk setiap entity:
- **QFilm** - untuk Film entity
- **QTicket** - untuk Ticket entity  
- **QBooking** - untuk Booking entity

Ini dapat ditemukan di: `target/generated-sources/java/com/example/cinema_learn/entity/Q*.class`

## Penggunaan

### 1. QueryDSLUtil - Helper Utility
File: `com.example.cinema_learn.util.QueryDSLUtil`

Berisi predicate builders untuk common queries:

#### Film Queries
```java
// Search film by name (case-insensitive)
Predicate predicate = QueryDSLUtil.filmByName("Interstellar");

// Search film by genre
Predicate predicate = QueryDSLUtil.filmByGenre("Sci-Fi");

// Search film dengan duration > nilai tertentu
Predicate predicate = QueryDSLUtil.filmByDurationGreaterThan(120);
```

#### Ticket Queries
```java
// Search ticket dengan price range
Predicate predicate = QueryDSLUtil.ticketByPriceRange(10000.0, 100000.0);

// Find tickets yang masih tersedia (quota > 0)
Predicate predicate = QueryDSLUtil.ticketByQuotaAvailable();

// Find tickets untuk film tertentu
Predicate predicate = QueryDSLUtil.ticketByFilmId(1L);
```

#### Booking Queries
```java
// Find bookings dengan jumlah booking > nilai tertentu
Predicate predicate = QueryDSLUtil.bookingByAmountGreaterThan(5);

// Search booking dengan total price range
Predicate predicate = QueryDSLUtil.bookingByTotalPriceRange(50000.0, 500000.0);

// Find bookings untuk ticket tertentu
Predicate predicate = QueryDSLUtil.bookingByTicketId(1L);
```

### 2. Service Methods yang Sudah Menggunakan QueryDSL

#### FilmService
```java
// Search film by name
List<Film> films = filmService.searchByName("Inception");

// Search film by genre
List<Film> films = filmService.searchByGenre("Sci-Fi");

// Search film dengan minimum duration
List<Film> films = filmService.searchByMinDuration(150);
```

#### TicketService
```java
// Search ticket dengan price range
List<Ticket> tickets = ticketService.searchByPriceRange(20000.0, 60000.0);

// Find tickets yang tersedia
List<Ticket> tickets = ticketService.findAvailableTickets();

// Find tickets untuk film tertentu
List<Ticket> tickets = ticketService.findByFilm(1L);
```

#### BookingService
```java
// Find bookings dengan minimum amount
List<Booking> bookings = bookingService.findByMinAmount(10);

// Search booking dengan price range
List<Booking> bookings = bookingService.findByTotalPriceRange(100000.0, 1000000.0);

// Find bookings untuk ticket tertentu
List<Booking> bookings = bookingService.findByTicket(2L);
```

### 3. Repositories dengan QuerydslPredicateExecutor
Semua repositories sudah extend `QuerydslPredicateExecutor`:

```java
@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, QuerydslPredicateExecutor<Film> {
}
```

Ini memungkinkan method `findAll(Predicate predicate)`.

## Contoh Implementasi Custom Query

Jika Anda ingin membuat custom predicate:

```java
import com.example.cinema_learn.entity.QFilm;
import com.querydsl.core.types.Predicate;

// Contoh: Film dengan duration antara 100 dan 200 menit
Predicate predicate = QFilm.film.filmDuration.between(100, 200)
    .and(QFilm.film.filmGenre.eq("Sci-Fi"));

List<Film> results = filmRepository.findAll(predicate);
```

## Build Configuration

### Maven Dependencies
- `querydsl-jpa` - Core QueryDSL JPA
- `querydsl-apt` - Annotation processor untuk generate Q-classes
- `jakarta.persistence-api` - Jakarta Persistence

### Annotation Processors
QueryDSL annotation processor diatur di Maven compiler plugin dan akan otomatis:
1. Scan entity classes
2. Generate Q-classes di `target/generated-sources/java`
3. Compile classes dengan Q-classes

## Best Practices

1. **Gunakan QueryDSLUtil untuk common queries** - Hindari duplicate predicate logic
2. **Combine predicates dengan AND/OR** - Untuk complex queries
3. **Return empty list jika predicate null** - Handle null cases gracefully
4. **Cache complex predicates** - Jika digunakan berkali-kali

## Tips & Tricks

### Combining Multiple Predicates
```java
Predicate predicate1 = QueryDSLUtil.filmByGenre("Sci-Fi");
Predicate predicate2 = QueryDSLUtil.filmByDurationGreaterThan(100);

// Combine dengan AND
if (predicate1 != null && predicate2 != null) {
    Predicate combined = predicate1.and(predicate2);
    List<Film> results = filmRepository.findAll(combined);
}
```

### Null Safe Handling
```java
Predicate predicate = QueryDSLUtil.filmByName(nameParam);
// Jika predicate null, akan return semua records
return predicate != null ? filmRepository.findAll(predicate) : filmRepository.findAll();
```

## Referensi
- [QueryDSL Documentation](http://www.querydsl.com/)
- [Spring Data QueryDSL](https://docs.spring.io/spring-data/jpa/reference/repositories/query-by-example.html#repositories.query-by-example)
- [QueryDSL Predicates](https://querydsl.com/static/querydsl/latest/reference/html/ch02.html)
