package org.search.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
public class ProjectService {

	private static ProjectRepository projectRepository;

	private final EntityManager entityManager;
	
	public ProjectService(ProjectRepository projectRepository, EntityManager entityManager) {
		this.projectRepository = projectRepository;
		this.entityManager = entityManager;
	}

	public List<Project> findAll() {
//		try {
//			initiateIndexing();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return projectRepository.findAll();
	}

	public List<Project> getPostBasedOnWord(String word) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Project.class).get();

		Query foodQuery = qb.keyword().wildcard().onFields("projectName", "organization", "team_id", "project_description").matching(word+ "*").createQuery();

		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(foodQuery, Project.class);
		return (List<Project>) fullTextQuery.getResultList();
	}

	@Transactional
	public void initiateIndexing() throws InterruptedException {
		System.out.println("Initiating indexing...");
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		fullTextEntityManager.createIndexer().startAndWait();
		System.out.println("All entities indexed");
	}
}
