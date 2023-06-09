package sk.ness.academy.service;

import java.util.List;

import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

public interface AuthorService {

	  /** Returns all available {@link Author}s */
	  List<Author> findAll();

	/** Returns all authors with  {@link AuthorStats} */
	List<AuthorStats> getAuthorStatistics();

	}
