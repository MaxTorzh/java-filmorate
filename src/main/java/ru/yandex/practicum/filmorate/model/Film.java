package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.MinReleaseDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private Long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;
    @NotNull(message = "Дата релиза не может быть пустой")
    @PastOrPresent(message = "Дата релиза не может быть в будущем")
    @MinReleaseDate
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительным числом, в минутах")
    private int duration;
    private Set<Long> likes = new HashSet<>();
    @NotEmpty(message = "Фильм должен содержать хотя бы один жанр")
    private Set<Genre> genres = new HashSet<>();
    @NotNull(message = "Рейтинг MPA не может быть пустой")
    private MpaRating mpa;
}
