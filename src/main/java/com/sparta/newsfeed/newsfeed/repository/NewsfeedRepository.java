package com.sparta.newsfeed.newsfeed.repository;

import com.querydsl.jpa.JPQLQueryFactory;
import com.sparta.newsfeed.QuerydslUtils;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.newsfeed.entity.QNewsfeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface NewsfeedRepository extends Repository<Newsfeed, Integer>, NewsfeedQueryRepository {
    Newsfeed save(Newsfeed newsfeed);

    Newsfeed findById(Long id);

}

interface NewsfeedQueryRepository {
    Page<Newsfeed> findAll(Pageable pageable, List<Long> ids);

    boolean delete(Long id);

    void deleteNewsfeedsByUserId(Long id);
}

@org.springframework.stereotype.Repository
class NewsfeedRepositoryImpl implements NewsfeedQueryRepository {
    private final JPQLQueryFactory queryFactory;
    QNewsfeed newsfeed = QNewsfeed.newsfeed;

    public NewsfeedRepositoryImpl(JPQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Newsfeed> findAll(Pageable pageable, List<Long> ids) {
        var result = queryFactory
                .selectFrom(newsfeed)
                .where(newsfeed.user.id.in(ids));

        return QuerydslUtils.fetchPage(result, newsfeed, pageable);
    }

    @Override
    public boolean delete(Long id) {
        long deletedCount = queryFactory
                .delete(newsfeed)
                .where(newsfeed.id.eq(id))
                .execute();

        return deletedCount > 0;
    }

    @Override
    public void deleteNewsfeedsByUserId(Long userId) {
        queryFactory
                .delete(newsfeed)
                .where(newsfeed.user.id.eq(userId))
                .execute();
    }
}