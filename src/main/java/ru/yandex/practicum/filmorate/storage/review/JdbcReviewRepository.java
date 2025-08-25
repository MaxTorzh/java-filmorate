package ru.yandex.practicum.filmorate.storage.review;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.base.BaseNamedParameterRepository;

import java.util.*;

@Repository
@Qualifier("reviewRepository")
public class JdbcReviewRepository extends BaseNamedParameterRepository<Review> implements ReviewRepository {
    private static final String INSERT_REVIEW_QUERY = """
            INSERT INTO reviews (content, is_positive, user_id, film_id, useful)
            VALUES (:content, :isPositive, :userId, :filmId, :useful)
            """;

    private static final String UPDATE_REVIEW_QUERY = """
            UPDATE reviews
            SET content = :content, is_positive = :isPositive, user_id = :userId, film_id = :filmId, useful = :useful
            WHERE review_id = :reviewId;
            """;

    private static final String DELETE_REVIEW_QUERY = "DELETE FROM reviews WHERE review_id = :reviewId";

    private static final String FIND_REVIEW_BY_ID_QUERY = "SELECT * FROM reviews WHERE review_id = :reviewId";

    private static final String FIND_REVIEWS_BY_FILM_ID_QUERY = """
            SELECT * FROM reviews
            WHERE film_id = :filmId
            ORDER BY useful DESC
            LIMIT :count
            """;

    private static final String FIND_ALL_REVIEWS_QUERY = """
            SELECT * FROM reviews
            ORDER BY useful DESC
            LIMIT :count
            """;

    private static final String UPDATE_REVIEW_LIKE_QUERY = """
            UPDATE reviews
            SET useful = useful + 1
            WHERE review_id = :reviewId
            """;

    private static final String UPDATE_REVIEW_DISLIKE_QUERY = """
            UPDATE reviews
            SET useful = useful - 1
            WHERE review_id = :reviewId
            """;

    public JdbcReviewRepository(NamedParameterJdbcOperations jdbc, RowMapper<Review> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Review createReview(Review review) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", review.getContent());
        params.put("isPositive", review.getIsPositive());
        params.put("userId", review.getUserId());
        params.put("filmId", review.getFilmId());
        params.put("useful", 0);

        long id = insert(INSERT_REVIEW_QUERY, params);
        review.setReviewId(id);
        return review;
    }

    @Override
    public Review updateReview(Review review) {
        Map<String, Object> params = new HashMap<>();
        params.put("reviewId", review.getReviewId());
        params.put("content", review.getContent());
        params.put("isPositive", review.getIsPositive());
        params.put("userId", review.getUserId());
        params.put("filmId", review.getFilmId());
        params.put("useful", review.getUseful());

        update(UPDATE_REVIEW_QUERY, params);
        return review;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Map<String, Object> params = new HashMap<>();
        params.put("reviewId", reviewId);
        return update(DELETE_REVIEW_QUERY, params);
    }

    @Override
    public Optional<Review> getReviewById(Long reviewId) {
        Map<String, Object> params = new HashMap<>();
        params.put("reviewId", reviewId);
        return findOne(FIND_REVIEW_BY_ID_QUERY, params);
    }

    @Override
    public List<Review> getReviewsByFilmId(Long filmId, int count) {
        Map<String, Object> params = new HashMap<>();
        params.put("filmId", filmId);
        params.put("count", count);
        return findMany(FIND_REVIEWS_BY_FILM_ID_QUERY, params);
    }

    @Override
    public void addLike(Long reviewId, Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("reviewId", reviewId);
        params.put("userId", userId);
        update(UPDATE_REVIEW_LIKE_QUERY, params);
    }

    @Override
    public void addDislike(Long reviewId, Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("reviewId", reviewId);
        params.put("userId", userId);
        update(UPDATE_REVIEW_DISLIKE_QUERY, params);
    }
}
