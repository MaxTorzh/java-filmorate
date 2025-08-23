![Диаграмма Filmorate](images/FilmorateScheme.png)

# Filmorate 🎬

## О проекте

**Filmorate** — это REST API для оценки фильмов и поиска друзей по интересам.  
Аналог кинопоиска с возможностью:

- Добавления фильмов и пользователей
- Оставления лайков
- Поиска друзей и общих интересов

## 🛠 Технологии и зависимости

| Компонент         | Версия                 |
|-------------------|------------------------|
| Java              | 21                     |
| Spring Boot       | 3.2.4                  |
| Lombok            | (из родительского POM) |
| Logbook           | 3.7.2                  |
| Spring Validation | 3.2.4                  |

```xml

<dependency>
    <groupId>org.zalando</groupId>
    <artifactId>logbook-spring-boot-starter</artifactId>
    <version>3.7.2</version>
</dependency>
```

---

# 🚀 Запуск проекта

Требования:

- JDK 21+
- Maven 3.9+

**Сборка и запуск**

```java
mvn clean package
java -
jar target/filmorate-0.0.1-SNAPSHOT.jar
```

---

# 📚 API Endpoints

## Фильмы

| Метод  | 	Путь                      | 	Описание                | 	Валидация                                                         | 	Пример тела запроса                                                                                    |
|--------|----------------------------|--------------------------|--------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| GET    | /films                     | Получить все фильмы      | 	-                                                                 | 	-                                                                                                      |
| GET    | /films/{id}                | Получить фильм по ID     | Проверка существования ID                                          | 	-                                                                                                      |
| POST   | /films                     | Создать фильм            | @Valid, проверка названия, даты (≥ 1895-12-28), длительности (> 0) | json { "name": "Inception", "description": "A thief...", "releaseDate": "2010-07-16", "duration": 148 } |
| PUT    | 	/films                    | 	Обновить фильм          | 	Проверка ID, названия, даты                                       | 	Аналогично POST                                                                                        |
| PUT    | 	/films/{id}/like/{userId} | 	Добавить лайк           | 	Проверка ID фильма и пользователя                                 | 	-                                                                                                      |
| DELETE | 	/films/{id}/like/{userId} | 	Удалить лайк            | 	Проверка ID фильма и пользователя                                 | 	-                                                                                                      |
| GET    | 	/films/popular?count={n}  | 	Топ-N фильмов по лайкам | 	count > 0 (по умолчанию 10)                                       | 	-                                                                                                      |

## Пользователи

| Метод  | 	Путь                                 | 	Описание                    | 	Валидация                                   | 	Пример тела запроса                                                                                           |
|--------|---------------------------------------|------------------------------|----------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| GET    | 	/users                               | 	Получить всех пользователей | 	-                                           | 	-                                                                                                             |
| GET    | 	/users/{id}                          | 	Получить пользователя по ID | 	Проверка ID                                 | 	-                                                                                                             |
| POST   | 	/users                               | 	Создать пользователя        | 	@Valid, email/логин уникальны, имя ≠ пустое | 	json { "email": "user@mail.com", "login": "user123", "name": "Thomas A. Anderson", "birthday": "1971-09-13" } |
| PUT    | 	/users                               | 	Обновить пользователя       | 	Проверка ID, email/логина                   | 	Аналогично POST                                                                                               |
| PUT    | 	/users/{id}/friends/{friendId}       | 	Добавить друга              | 	Проверка ID, запрет самодружбы              | 	-                                                                                                             |
| DELETE | 	/users/{id}/friends/{friendId}       | 	Удалить друга               | 	Проверка ID                                 | 	-                                                                                                             |
| GET    | 	/users/{id}/friends                  | 	Список друзей               | 	Проверка ID                                 | 	-                                                                                                             |
| GET    | 	/users/{id}/friends/common/{otherId} | 	Общие друзья                | 	Проверка обоих ID                           | 	-                                                                                                             |

---

## 🏛️ Архитектура

```mermaid
classDiagram
    direction BT
    
    class FilmController {
        +addLike(id, userId) void
    }
    
    class FilmService {
        -filmStorage: FilmStorage
        +addLike(id, userId) void
    }
    
    class InMemoryFilmStorage {
        -films: Map~Long, Film~
        +save(Film) Film
    }
    
    FilmController --> FilmService
    FilmService --> InMemoryFilmStorage
```

---

# 🔑 Ключевые компоненты

## Слои:

### Controller:

1. Принимает HTTP-запросы

2. Валидирует входные данные (@Valid)

3. Возвращает HTTP-ответы

### Service:

1. Содержит бизнес-логику (дружба, лайки)

2. Проверяет права доступа

3. Работает через интерфейсы Storage

### Storage:

1. In-memory реализация (HashMap)

2. Выполняет CRUD-операции

### Валидация:

1. Аннотации (@NotBlank, @Size, @PastOrPresent)

### Кастомные проверки:

1. Дата релиза фильма ≥ 28.12.1895 (@MinReleaseDate)

2. Запрет самодружбы в UserService.addFriend()

### Обработка ошибок:

1. @ExceptionHandler в контроллерах

### Кастомные исключения:

1. NotFoundException (404)

2. ValidationException (400)

3. DuplicatedDataException (409)

### Логирование:

1. Logbook для HTTP-запросов/ответов

2. SLF4J для бизнес-событий (добавление друзей, лайков)

---

# 💡 Примеры

## 🧍 Добавление друга (UserService)

```java
public void addFriend(Long userId, Long friendId) {
    if (userId.equals(friendId)) {
        throw new ValidationException("Нельзя добавить себя в друзья");
    }
    User user = userStorage.getById(userId);
    User friend = userStorage.getById(friendId);
    user.getFriends().add(friendId);
    friend.getFriends().add(userId); // Двусторонняя дружба
    userStorage.save(user);
    userStorage.save(friend);
}
```

## Последовательность действий

```mermaid
sequenceDiagram
    participant Frontend
    participant UserController
    participant UserService
    participant Storage
    
    Frontend->>UserController: PUT /users/1/friends/2
    UserController->>UserService: addFriend(1, 2)
    UserService->>Storage: getById(1)
    Storage-->>UserService: User
    UserService->>Storage: getById(2)
    Storage-->>UserService: User
    UserService->>Storage: save(User)
    UserService->>Storage: save(Friend)
    UserService-->>UserController: OK
    UserController-->>Frontend: 200 OK
```

---

## ❤️ Добавление лайка

```java
public void addLike(Long filmId, Long userId) {
    Film film = filmStorage.getFilmById(filmId);
    if (filmStorage.getFilmById(filmId) == null) {
        throw new NotFoundException("Фильм не найден.");
    }
    User user = userStorage.getUserById(userId);
    if (user == null) {
        throw new NotFoundException("Пользователь не найден.");
    }
    film.getLikes().add(userId);
}
```

## Последовательность действий

```mermaid
sequenceDiagram
    participant User
    participant Controller
    participant Service
    participant Storage
    
    User->>Controller: PUT /films/1/like/101
    Controller->>Service: addLike(1, 101)
    Service->>Storage: getById(1)
    Storage-->>Service: Film
    Service->>Storage: save(Film)
    Storage-->>Service: Film
    Service-->>Controller: OK
    Controller-->>User: 200 OK
```
