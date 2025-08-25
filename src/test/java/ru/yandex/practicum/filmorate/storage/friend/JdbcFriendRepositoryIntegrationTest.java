package ru.yandex.practicum.filmorate.storage.friend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.BaseIntegrationTest;
import ru.yandex.practicum.filmorate.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import({
        JdbcFriendRepository.class,
        UserRowMapper.class
})
public class JdbcFriendRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private JdbcFriendRepository friendRepository;

    @Test
    public void testGetFriends() {
        List<User> friends = friendRepository.getFriends(1L);

        assertThat(friends).isNotEmpty();
    }

    @Test
    public void testGetCommonFriends() {
        List<User> commonFriends = friendRepository.getCommonFriends(1L, 2L);

        assertThat(commonFriends).isNotEmpty();
        assertThat(commonFriends).hasSize(1);
        assertThat(commonFriends.get(0).getId()).isEqualTo(3L);
    }
}