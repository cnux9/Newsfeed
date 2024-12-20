package com.sparta.newsfeed.newsfeed.repository;

import com.querydsl.jpa.JPQLQueryFactory;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.newsfeed.entity.QNewsfeed;
import com.sparta.newsfeed.user.entity.QUser;
import org.springframework.data.repository.Repository;
import java.util.List;

public interface NewsfeedRepository extends Repository<Newsfeed, Integer>, NewsfeedQueryRepository {
    Newsfeed save(Newsfeed newsfeed);
    Newsfeed findById(Long id);
}

interface NewsfeedQueryRepository {
    List<Newsfeed> findAll(List<Long> ids);
    boolean delete(Long id);
}

@org.springframework.stereotype.Repository
class NewsfeedRepositoryImpl implements NewsfeedQueryRepository {
    private final JPQLQueryFactory queryFactory;
    QNewsfeed newsfeed = QNewsfeed.newsfeed;
    QUser user = QUser.user;

    public NewsfeedRepositoryImpl(JPQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Newsfeed> findAll(List<Long> ids) {
        return queryFactory
                .selectFrom(newsfeed)
                .where(newsfeed.user.id.in(ids))
                .orderBy(newsfeed.createdAt.asc())
                .fetchAll()
                .stream()
                .toList();
    }

    @Override
    public boolean delete(Long id) {
        long deletedCount = queryFactory
                .delete(newsfeed)
                .where(newsfeed.id.eq(id))
                .execute();

        return deletedCount > 0;
    }
}