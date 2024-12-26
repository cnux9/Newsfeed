package com.sparta.newsfeed.newsfeed.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQueryFactory;
import com.sparta.newsfeed.QuerydslUtils;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestQueryDto;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.newsfeed.entity.QNewsfeed;
import com.sparta.newsfeed.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface NewsfeedRepository extends Repository<Newsfeed, Integer>, NewsfeedQueryRepository {
    Newsfeed save(Newsfeed newsfeed);
    Newsfeed findById(Long id);

}

interface NewsfeedQueryRepository {
    Page<Newsfeed> findAll(Pageable pageable, NewsfeedRequestQueryDto query, List<Long> ids);
    boolean delete(Long id);
    void deleteNewsfeedsByUserId(Long id);
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
    public Page<Newsfeed> findAll(
            Pageable pageable,
            NewsfeedRequestQueryDto query,
            List<Long> ids
    ) {
        var builder = new BooleanBuilder();

        if (query != null) {
            if (query.getStart() != null) {
                builder.and(newsfeed.createdAt.after(query.getStart()));
            }
            if (query.getEnd() != null) {
                builder.and(newsfeed.createdAt.before(query.getEnd()));
            }
        }
        if (ids != null && !ids.isEmpty()) {
            builder.and(newsfeed.user.id.in(ids));
        }

        var result = queryFactory
                .selectFrom(newsfeed)
                .where(builder);

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