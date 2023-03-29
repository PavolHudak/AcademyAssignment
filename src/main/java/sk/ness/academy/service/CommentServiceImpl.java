package sk.ness.academy.service;

import org.springframework.stereotype.Service;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDAO commentDAO;


    @Override
    public Comment findCommentById(Integer commentId) {
        return this.commentDAO.findCommentById(commentId);
    }

    @Override
    public void deleteCommentById(Integer commentId) {
        this.commentDAO.deleteCommentById(commentId);
    }

    @Override
    public void createComment(final Comment comment) {
        this.commentDAO.persist(comment);
    }
}
