package org.search.hibernate.script;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestScriptRepository extends JpaRepository<TestScript, Long> {

}
