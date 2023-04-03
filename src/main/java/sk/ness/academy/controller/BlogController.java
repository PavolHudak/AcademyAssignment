package sk.ness.academy.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.exception.BadRequestException;
import sk.ness.academy.exception.CustomExceptionHandler;
import sk.ness.academy.exception.ResourceNotFoundException;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;

@RestController
public class BlogController {

  @Resource
  private ArticleService articleService;

  @Resource
  private AuthorService authorService;

  @Resource
  private CommentService commentService;

  // ~~ Article
  @RequestMapping(value = "articles", method = RequestMethod.GET)
  public List<Article> getAllArticles() {
	  return this.articleService.findAllList();
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public Article getArticle(@PathVariable final Integer articleId) {
    if (this.articleService.findByID(articleId) == null) {
      throw new ResourceNotFoundException("Article with id " + articleId + " do not exists.");
    } else return this.articleService.findByID(articleId);
  }

  @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
  public List<Article> searchArticle(@PathVariable final String searchText) {
    if(this.articleService.searchArticles(searchText).isEmpty()) {
      throw new ResourceNotFoundException("Article with searching text '" + searchText + "' do not exists.");
    } else return this.articleService.searchArticles(searchText);
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public ResponseEntity<?> addArticle(@RequestBody final Article article) {
    if (article.getTitle() == null || article.getAuthor() == null || article.getText() == null) {
      throw new BadRequestException("JSON body is not completed! Author, text and title are mandatory fields.");
    } else {
      this.articleService.createArticle(article);
      return ResponseEntity.ok("Article created successfully");
    }
  }

  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public List<Author> getAllAuthors() {
	  return this.authorService.findAll();
  }

  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public List<AuthorStats> authorStats() {
    return this.authorService.getAuthorStatistics();
  }

  @RequestMapping(value = "articles/{articleId}" , method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteArticle(@PathVariable final Integer articleId) {
    try {
      this.articleService.deleteByID(articleId);
      return ResponseEntity.ok("Article deleted successfully.");
    } catch (Exception e) {
      throw new ResourceNotFoundException("Article with ID " + articleId + " not found.");
    }
  }

  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.PUT)
  public ResponseEntity<?> createComment(@PathVariable final Integer articleId, @RequestBody final Comment comment) {
    comment.setArticleId(articleId);
    this.commentService.createComment(comment);
    return ResponseEntity.ok("Article commented successfully.");
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.GET)
  public Comment getComment(@PathVariable final Integer commentId) {
    if (this.commentService.findCommentById(commentId) == null) {
      throw new ResourceNotFoundException("Comment with id " + commentId + " do not exists.");
    } else return this.commentService.findCommentById(commentId);
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteComment(@PathVariable final Integer commentId) {
    try {
      this.commentService.deleteCommentById(commentId);
      return ResponseEntity.ok("Comment deleted successfully.");
    } catch (Exception e) {
      throw new ResourceNotFoundException("Comment with ID " + commentId + " not found.");
    }
  }


}
