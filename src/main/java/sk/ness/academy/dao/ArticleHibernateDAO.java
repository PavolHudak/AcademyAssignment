package sk.ness.academy.dao;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;

@Repository
public class ArticleHibernateDAO implements ArticleDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public Article findByID(final Integer articleId) {
    return (Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Article> findAll() {
  return this.sessionFactory.getCurrentSession().createSQLQuery("select * from articles").addEntity(Article.class).list();
  }

  @Override
  public List<Article> findAllList() {
     return this.sessionFactory.getCurrentSession().createSQLQuery("select id , title, text, author, create_timestamp from articles").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
  }

  @Override
  public void persist(final Article article) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(article);
  }

  @Override
  public void deleteByID(Integer articleId) {
    this.sessionFactory.getCurrentSession().delete(findByID(articleId));
  }

  @Override
  public List<Article> searchArticles(String searchText) {
    return findAll().stream()
            .filter(A -> A.getAuthor().contains(searchText) || A.getText().contains(searchText) || A.getTitle().contains(searchText))
            .collect(Collectors.toList());
  }

}
