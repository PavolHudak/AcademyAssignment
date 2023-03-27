package sk.ness.academy.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

@Repository
public class AuthorHibernateDAO implements AuthorDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Resource
  private ArticleDAO articleDAO;

  @SuppressWarnings("unchecked")
  @Override
  public List<Author> findAll() {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select distinct a.author as name from articles a ")
        .addScalar("name", StringType.INSTANCE)
        .setResultTransformer(new AliasToBeanResultTransformer(Author.class)).list();
  }

  @Override
  public List<AuthorStats> getAuthorStatistics() {
    List<Article> articles = articleDAO.findAll();

    Map<String, Long> authorCountMap = articles.stream()
            .collect(Collectors.groupingBy(Article::getAuthor, Collectors.counting()));

    return authorCountMap.entrySet().stream()
            .map(entry -> new AuthorStats(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
  }


}

