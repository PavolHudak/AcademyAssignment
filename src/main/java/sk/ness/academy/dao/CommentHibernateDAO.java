package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.ResourceNotFoundException;

import javax.annotation.Resource;


@Repository
public class CommentHibernateDAO implements CommentDAO{

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Resource
    private ArticleDAO articleDAO;

    @Override
    public Comment findCommentById(Integer commentId) {
        return (Comment) this.sessionFactory.getCurrentSession().get(Comment.class, commentId);
    }

    @Override
    public void createComment(final Comment comment) {
        if(articleDAO.findByID(comment.getArticleId()) == null) {
            throw new ResourceNotFoundException("Article with provided id do not exists.");
        } else
            this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }

    @Override
    public void deleteCommentById(Integer commentId) {
        this.sessionFactory.getCurrentSession().delete(findCommentById(commentId));
    }
}
