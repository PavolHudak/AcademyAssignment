package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;


@Repository
public class CommentHibernateDAO implements CommentDAO{

    @Resource
    private ArticleDAO articleDAO;

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;


    @Override
    public Comment findCommentById(Integer commentId) {
        return (Comment) this.sessionFactory.getCurrentSession().get(Comment.class, commentId);
    }

    @Override
    public void persist(final Comment comment) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }

    @Override
    public void deleteCommentById(Integer commentId) {
        this.sessionFactory.getCurrentSession().delete(findCommentById(commentId));
    }
}
