package org.search.hibernate.script;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.facet.FacetSortOrder;
import org.hibernate.search.query.facet.FacetingRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
public class TestScriptService {

	private static TestScriptRepository testScriptRepository;

	private final EntityManager entityManager;

	public TestScriptService(TestScriptRepository testScriptRepository, EntityManager entityManager) {
		this.testScriptRepository = testScriptRepository;
		this.entityManager = entityManager;
	}

	public List<TestScript> findAll() {
//		try {
//			initiateIndexing();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return testScriptRepository.findAll();
	}

	public void save() {
		TestScript script = new TestScript();
		script.setObjective("Something to save");
		script.setOrganization("general");
		script.setUuid(UUID.randomUUID().toString());
		script.setId(700L);
		testScriptRepository.save(script);
	}

	public List<TestScript> getPostBasedOnWord(String word) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(TestScript.class)
				.get();

		// Search by Keyword
		Query foodQuery = qb.keyword().wildcard().onFields("testScriptName", "requestBody", "objective", "organization",
				"uuid", "tagName", "created_by", "sequence_id").matching(word).createQuery();

		// Search by a part of sentence
		// Query foodQuery =
		// qb.phrase().withSlop(0).onField("objective").sentence(word).createQuery();

		// Fuzzy Query with 2 wrong words
		/*
		 * Query foodQuery = qb.keyword().fuzzy().withEditDistanceUpTo(2)
		 * .onFields("testScriptName", "requestBody", "objective", "organization",
		 * "uuid") .matching("*" + word + "*").createQuery();
		 */

		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(foodQuery, TestScript.class);
		return (List<TestScript>) fullTextQuery.getResultList();
	}

	@Transactional
	public void initiateIndexing() throws InterruptedException {
		System.out.println("Initiating indexing...");
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		fullTextEntityManager.createIndexer().startAndWait();
		System.out.println("All entities indexed");
	}

	public List<TestScript> letsGuess(String userInput) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(TestScript.class)
				.get();

		org.apache.lucene.search.Query exactSearchByName = qb.keyword().onField("objective").matching(userInput)
				.createQuery();
		org.apache.lucene.search.Query fuzzySearchByName = qb.keyword().fuzzy().withEditDistanceUpTo(1)
				.onField("objective").matching(userInput).createQuery();
		org.apache.lucene.search.Query searchByName = qb.bool().should(exactSearchByName).should(fuzzySearchByName)
				.createQuery();
		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(searchByName, TestScript.class);
		return (List<TestScript>) fullTextQuery.getResultList();
	}

	public void indexReader() {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);
		IndexReader reader = fullTextEntityManager.getSearchFactory().getIndexReaderAccessor().open(TestScript.class);
		int numDocs = reader.numDocs();
		System.out.println(numDocs);
	}

	public void faceting() {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(TestScript.class)
				.get();
		FacetingRequest priceFacetingRequest = qb.facet().name("make").onField("organization").discrete()
				.orderedBy(FacetSortOrder.FIELD_VALUE).includeZeroCounts(false).maxFacetCount(10)
				.createFacetingRequest();
		System.out.println(priceFacetingRequest.toString());
	}

	public void testsCreated(String email) {
//		SearchSession searchSession = org.hibernate.search.mapper.orm.Search.session(entityManager);
//
//		SearchResult<TestScript> result = searchSession.search(TestScript.class)
//				.where(f -> f.match().fields( "objective", "created_by" )
//		                .matching( "anushb@quinnox.com" ).fuzzy(2)).fetch(20);
//		
//		List<TestScript> testScripts = result.hits();
//		for (TestScript testScript : testScripts) {
//			System.out.println(testScript.toString());
//		}
	}
}
