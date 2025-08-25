package ru.yandex.practicum.filmorate.storage.film;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.BaseIntegrationTest;
import ru.yandex.practicum.filmorate.mappers.FilmRowMapper;

import static org.assertj.core.api.Assertions.assertThat;

@Import({
        JdbcFilmRepository.class,
        FilmRowMapper.class
})
public class JdbcFilmRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private JdbcFilmRepository filmRepository;

//    @Test
//    public void testCreateFilm() {
//        Film film = Film.builder()
//                .name("Test Film")
//                .description("Test Description")
//                .releaseDate(LocalDate.of(2020, 1, 1))
//                .duration(120)
//                .mpa(MpaRating.builder().id(1L).name("G").build())
//                .genres(new HashSet<>(Arrays.asList(
//                        Genre.builder().id(1L).name("Комедия").build(),
//                        Genre.builder().id(2L).name("Драма").build()
//                )))
//                .directors(new HashSet<>())
//                .build();
//
//        Film createdFilm = filmRepository.createFilm(film);
//
//        assertThat(createdFilm).isNotNull();
//        assertThat(createdFilm.getId()).isNotNull().isPositive();
//        assertThat(createdFilm.getName()).isEqualTo("Test Film");
//        assertThat(createdFilm.getDescription()).isEqualTo("Test Description");
//        assertThat(createdFilm.getReleaseDate()).isEqualTo(LocalDate.of(2020, 1, 1));
//        assertThat(createdFilm.getDuration()).isEqualTo(120);
//        assertThat(createdFilm.getMpa().getId()).isEqualTo(1L);
//        assertThat(createdFilm.getGenres()).hasSize(2);
//    }
//
//    @Test
//    public void testFindAllFilms() {
//        List<Film> films = filmRepository.findAllFilms();
//
//        assertThat(films).hasSize(2);
//        assertThat(films).extracting(Film::getName)
//                .containsExactlyInAnyOrder("Test Film 1", "Test Film 2");
//    }

//    @Test
//    public void testUpdateFilm() {
//        Film film = Film.builder()
//                .name("Original Film")
//                .description("Original Description")
//                .releaseDate(LocalDate.of(2020, 1, 1))
//                .duration(120)
//                .mpa(MpaRating.builder().id(1L).name("G").build())
//                .genres(new HashSet<>())
//                .directors(new HashSet<>())
//                .build();
//        Film createdFilm = filmRepository.createFilm(film);
//
//        Film updatedFilm = Film.builder()
//                .id(createdFilm.getId())
//                .name("Updated Film")
//                .description("Updated Description")
//                .releaseDate(LocalDate.of(2021, 1, 1))
//                .duration(150)
//                .mpa(MpaRating.builder().id(2L).name("PG").build())
//                .genres(new HashSet<>(Collections.singletonList(
//                        Genre.builder().id(1L).name("Комедия").build()
//                )))
//                .directors(new HashSet<>())
//                .build();
//
//        Film result = filmRepository.updateFilm(updatedFilm);
//
//        assertThat(result.getId()).isEqualTo(createdFilm.getId());
//        assertThat(result.getName()).isEqualTo("Updated Film");
//        assertThat(result.getDescription()).isEqualTo("Updated Description");
//        assertThat(result.getReleaseDate()).isEqualTo(LocalDate.of(2021, 1, 1));
//        assertThat(result.getDuration()).isEqualTo(150);
//        assertThat(result.getMpa().getId()).isEqualTo(2L);
//        assertThat(result.getGenres()).hasSize(1);
//    }

//    @Test
//    public void testDeleteFilm() {
//        Film film = Film.builder()
//                .name("Test Film")
//                .description("Test Description")
//                .releaseDate(LocalDate.of(2020, 1, 1))
//                .duration(120)
//                .mpa(MpaRating.builder().id(1L).name("G").build())
//                .genres(new HashSet<>())
//                .directors(new HashSet<>())
//                .build();
//        Film createdFilm = filmRepository.createFilm(film);
//
//        boolean deleted = filmRepository.deleteFilm(createdFilm.getId());
//
//        assertThat(deleted).isTrue();
//
//        Optional<Film> foundFilm = filmRepository.getFilmById(createdFilm.getId());
//        assertThat(foundFilm).isEmpty();
//    }

    @Test
    public void testDeleteNonExistentFilm() {
        boolean deleted = filmRepository.deleteFilm(999L);
        assertThat(deleted).isFalse();
    }

//    @Test
//    public void testGetPopularFilms() {
//        Film film1 = Film.builder()
//                .name("Popular Film")
//                .description("Popular Description")
//                .releaseDate(LocalDate.of(2020, 1, 1))
//                .duration(120)
//                .mpa(MpaRating.builder().id(1L).name("G").build())
//                .genres(new HashSet<>())
//                .directors(new HashSet<>())
//                .build();
//
//        Film film2 = Film.builder()
//                .name("Less Popular Film")
//                .description("Less Popular Description")
//                .releaseDate(LocalDate.of(2021, 1, 1))
//                .duration(150)
//                .mpa(MpaRating.builder().id(2L).name("PG").build())
//                .genres(new HashSet<>())
//                .directors(new HashSet<>())
//                .build();
//
//        filmRepository.createFilm(film1);
//        filmRepository.createFilm(film2);
//
//        Collection<Film> popularFilms = filmRepository.getPopularFilms(2);
//
//        assertThat(popularFilms).hasSize(2);
//        for (Film film : popularFilms) {
//            assertThat(film.getDirectors()).isNotNull();
//        }
//    }
}
