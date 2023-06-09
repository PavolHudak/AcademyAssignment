package sk.ness.academy.dao;

import java.util.List;

import sk.ness.academy.domain.Article;

public interface ArticleDAO {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	  /** Returns all available {@link Article}s */
	  List<Article> findAll();

	  /** Returns all available {@link Article}s */
	  List<Article> findAllList();

	  /** Persists {@link Article} into the DB */
	  void persist(Article article);

	  /** Delete {@link Article} by Id*/
	  void deleteByID(Integer articleId);

	  /** Find all articles contains searchText **/
	  List<Article> searchArticles(String searchText);

	}

