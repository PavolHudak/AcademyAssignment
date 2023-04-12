package sk.ness.academy.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.ResourceNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CommentHibernateDAOTest {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private ArticleDAO articleDAO;

    @PersistenceContext
    private EntityManager entityManager;

    @Rollback(true)
    @Test
    public void testCreateComment() {
        // create new article
        Article article = new Article();
        article.setTitle("New article");
        article.setText("This is a new article text.");
        article.setAuthor("John Doe");
        articleDAO.persist(article);

        // create new comment
        Comment comment = new Comment();
        comment.setText("This is a new comment.");
        comment.setAuthor("Jane Doe");
        comment.setArticleId(article.getId());
        commentDAO.createComment(comment);

        // check that comment was persisted correctly
        Comment persistedComment = entityManager.find(Comment.class, comment.getId());
        Assertions.assertEquals(comment.getText(), persistedComment.getText());
        Assertions.assertEquals(comment.getAuthor(), persistedComment.getAuthor());
        Assertions.assertEquals(comment.getArticleId(), persistedComment.getArticleId());
    }

    @Rollback(true)
    @Test
    public void testDeleteCommentById() {
        // create new article
        Article article = new Article();
        article.setTitle("Test article");
        article.setText("This is a test article text.");
        article.setAuthor("John Tester");
        articleDAO.persist(article);

        // create new comment
        Comment comment = new Comment();
        comment.setText("This is a Test comment.");
        comment.setAuthor("Palo Tester");
        comment.setArticleId(article.getId());
        commentDAO.createComment(comment);

        commentDAO.deleteCommentById(comment.getId());

        // check that comment was deleted
        Comment deletedComment = entityManager.find(Comment.class, comment.getId());
        Assertions.assertNull(deletedComment);
    }

    @Rollback(true)
    @Test
    public void testCreateCommentWithNonExistingArticle() {
        // create new comment with non-existing article id
        Comment comment = new Comment();
        comment.setText("This is a new comment.");
        comment.setAuthor("Palo Tester");
        comment.setArticleId(999);

        // check that ResourceNotFoundException is thrown when trying to create comment
        Assertions.assertThrows(ResourceNotFoundException.class, () -> commentDAO.createComment(comment));

        // check that comment was not persisted
        List<Comment> comments = entityManager.createQuery("SELECT c FROM Comment c WHERE c.text = :text")
                .setParameter("text", "This is a new comment.").getResultList();
        Assertions.assertEquals(0, comments.size());
    }
}
