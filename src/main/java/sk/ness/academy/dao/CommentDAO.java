package sk.ness.academy.dao;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentDAO {

    /** Returns {@link Comment} with provided ID */
    Comment findCommentById(Integer commentId);

    /** Persists {@link Comment} into the DB */
    void persist(Comment comment);

    /** Deletes {@link Comment} of provided Id */
    void deleteCommentById(Integer commentId);
}

