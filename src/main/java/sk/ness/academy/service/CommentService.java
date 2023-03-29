package sk.ness.academy.service;

import sk.ness.academy.domain.Comment;

public interface CommentService {

    /** Returns {@link Comment} with provided ID */
    Comment findCommentById(Integer commentId);

    /** Deletes {@link Comment} */
    void deleteCommentById(Integer commentId);

    /** Creates new {@link Comment} */
    void createComment(Comment comment);

}
