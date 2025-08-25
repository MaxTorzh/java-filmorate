package ru.yandex.practicum.filmorate.storage.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.BaseIntegrationTest;
import ru.yandex.practicum.filmorate.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
@Import({
        JdbcGenreRepository.class,
        GenreRowMapper.class
})
public class JdbcGenreRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private JdbcGenreRepository genreRepository;

    @Test
    public void testFindAllGenres() {
        List<Genre> genres = genreRepository.findAllGenres();

        assertThat(genres).isNotEmpty();
    }

    @Test
    public void testFindGenreById() {
        Optional<Genre> genreOptional = genreRepository.findGenreById(1L);

        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre ->
                        assertThat(genre).hasFieldOrPropertyWithValue("id", 1L)
                );
    }
}
