package org.search.hibernate.suite;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestSuiteRepository extends JpaRepository<TestSuite, Long> {

}
