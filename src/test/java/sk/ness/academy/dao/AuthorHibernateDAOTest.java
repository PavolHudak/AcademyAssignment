package sk.ness.academy.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sk.ness.academy.config.TestDatabaseConfig;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, AuthorHibernateDAO.class, ArticleHibernateDAO.class })
@Transactional
public class AuthorHibernateDAOTest {

    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    private ArticleDAO articleDAO;

    @Test
    public void testFindAll() {
        List<Author> authors = authorDAO.findAll();
        assertThat(authors, hasSize(3));
        assertThat(authors.get(0).getName(), equalTo("Emil Forslund"));
        assertThat(authors.get(1).getName(), equalTo("Matt Lacey"));
        assertThat(authors.get(2).getName(), equalTo("Ruth Avramovich"));

    }

    @Test
    public void testGetAuthorStatistics() {
        List<AuthorStats> authorStats = authorDAO.getAuthorStatistics();
        assertThat(authorStats, hasSize(3));
        assertThat(authorStats.get(0).getAuthorName(), equalTo("Matt Lacey"));
        assertThat(authorStats.get(0).getArticleCount(), equalTo(1));
        assertThat(authorStats.get(1).getAuthorName(), equalTo("Ruth Avramovich"));
        assertThat(authorStats.get(1).getArticleCount(), equalTo(1));
        assertThat(authorStats.get(2).getAuthorName(), equalTo("Emil Forslund"));
        assertThat(authorStats.get(2).getArticleCount(), equalTo(1));
    }

}

