package sk.ness.academy.dao;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sk.ness.academy.config.TestDatabaseConfig;
import sk.ness.academy.domain.Article;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, ArticleHibernateDAO.class })
@Transactional
class ArticleHibernateDAOTest {

    @Autowired
    private ArticleDAO articleDAO;

    @ParameterizedTest
    @CsvSource({"2,5 Tips On Working With Technical Debt,Matt Lacey", "4,Extending the Stream API to Maps,Emil Forslund"})
    public void testFindById(Integer id, String title, String author) {
        Article article = articleDAO.findByID(id);
        assertNotNull(article);
        assertEquals(title, article.getTitle());
        assertEquals(author, article.getAuthor());
    }

    @ParameterizedTest
    @CsvSource({"java,1", "and ,3"})
    public void testSearchArticles(String searchText, int expectedSize) {
        List<Article> articles = articleDAO.searchArticles(searchText);
        assertNotNull(articles);
        assertEquals(expectedSize, articles.size());
    }

    @ParameterizedTest
    @CsvSource({"New Article,Author", "Another Article,John"})
    public void testPersist(String title, String author) {
        Article article = new Article();
        article.setTitle(title);
        article.setText("This is a new article.");
        article.setAuthor(author);
        article.setCreateTimestamp(new Date());

        articleDAO.persist(article);

        Article persistedArticle = articleDAO.findByID(article.getId());
        assertNotNull(persistedArticle);
        assertEquals(title, persistedArticle.getTitle());
    }

    @ParameterizedTest
    @CsvSource({ "2", "3", "4"})
    public void testDeleteById(Integer id) {
        articleDAO.deleteByID(id);
        Article deletedArticle = articleDAO.findByID(id);
        assertNull(deletedArticle);
    }
}