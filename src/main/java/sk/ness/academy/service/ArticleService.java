package sk.ness.academy.service;

import java.io.IOException;
import java.util.List;
import sk.ness.academy.domain.Article;

public interface ArticleService {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	  /** Returns all available {@link Article}s */
	  List<Article> findAll();

	  /** Returns all available {@link Article}s */
	  List<Article> findAllList();

	  /** Creates new {@link Article} */
	  void createArticle(Article article);

	  /** Creates new {@link Article}s by ingesting all articles from json */
	  void ingestArticles(String jsonArticles) throws IOException;

	  /** Delete {@link Article} by Id*/
	  void deleteByID(Integer articleId);

	  /** Find all articles contains searchText **/
	  List<Article> searchArticles(String searchText);

	}
