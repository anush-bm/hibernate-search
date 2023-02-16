package org.search.hibernate.suite;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Service;

@Service
public class TestSuiteService {

	private static TestSuiteRepository testSuiteRepository;

	private final EntityManager entityManager;

	public TestSuiteService(TestSuiteRepository testSuiteRepository, EntityManager entityManager) {
		this.testSuiteRepository = testSuiteRepository;
		this.entityManager = entityManager;
	}

	@Transactional
	public void initiateIndexing() {
		try {
			System.out.println("Initiating indexing...");
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
			System.out.println("All entities indexed");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<TestSuite> findAll() {
		initiateIndexing();
		return testSuiteRepository.findAll();
	}

}
