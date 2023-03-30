package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;


@Repository
public class CommentHibernateDAO implements CommentDAO{

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Comment findCommentById(Integer commentId) {
        return (Comment) this.sessionFactory.getCurrentSession().get(Comment.class, commentId);
    }

    @Override
    public void createComment(final Comment comment) {
       // comment.setArticleId(sessionFactory.getCurrentSession().get(Article.class);
        this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }

    @Override
    public void deleteCommentById(Integer commentId) {
        this.sessionFactory.getCurrentSession().delete(findCommentById(commentId));
    }
}
