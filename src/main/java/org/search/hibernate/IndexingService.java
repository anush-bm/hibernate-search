package org.search.hibernate;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexingService {
	private final EntityManager em = null;

	@Transactional
	public void initiateIndexing() throws InterruptedException {
		System.out.println("Initiating indexing...");
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
		fullTextEntityManager.createIndexer().startAndWait();
		System.out.println("All entities indexed");
	}
}
