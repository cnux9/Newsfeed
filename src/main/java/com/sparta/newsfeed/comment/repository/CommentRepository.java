package com.sparta.newsfeed.comment.repository;

import com.querydsl.jpa.JPQLQueryFactory;
import com.sparta.newsfeed.QuerydslUtils;
import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.entity.QComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface CommentRepository extends Repository<Comment, Long>, CommentQueryRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(Long Id);
    void deleteById(Long id);
}

interface CommentQueryRepository {
    Page<Comment> findAllByNewsfeedId(Pageable pageable, Long id);
}

@org.springframework.stereotype.Repository
class CommentRepositoryImpl implements CommentQueryRepository {
    private final JPQLQueryFactory queryFactory;
    QComment comment = QComment.comment;

    CommentRepositoryImpl(JPQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Comment> findAllByNewsfeedId(Pageable pageable, Long id) {
        var result = queryFactory
                .selectFrom(comment)
                .where(comment.newsfeed.id.eq(id));
        return QuerydslUtils.fetchPage(result, comment, pageable);
    }
}

