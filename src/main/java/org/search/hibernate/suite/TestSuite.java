package org.search.hibernate.suite;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.search.hibernate.Project;
import org.search.hibernate.script.TestScript;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Indexed(index = "idx_testsuite")
@Table(name = "test_suite")
@org.hibernate.search.annotations.AnalyzerDef(name = "suiteanalyzer", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
		@TokenFilterDef(factory = LowerCaseFilterFactory.class),
		@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
				@Parameter(name = "language", value = "English") }) })
public class TestSuite {

	@Id
	private Long id;

	@Field(termVector = TermVector.YES)
	@Column(name = "uuid")
	private String uuid;

	@Field(termVector = TermVector.YES)
	@Column(name = "test_suite_name")
	private String testSuiteName;

	@Field(termVector = TermVector.YES)
	@Column(name = "module_name")
	private String moduleName;

	@ManyToMany(mappedBy = "testSuite", fetch = FetchType.EAGER)
	@IndexedEmbedded
	private Set<TestScript> testScripts = new HashSet<TestScript>();

	@ManyToOne(targetEntity = Project.class)
	@JsonIgnore
	@IndexedEmbedded(indexNullAs = "No Project", depth = 2)
	private Project project;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTestSuiteName() {
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Set<TestScript> getTestScripts() {
		return testScripts;
	}

	public void setTestScripts(Set<TestScript> testScripts) {
		this.testScripts = testScripts;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "TestSuite [id=" + id + ", uuid=" + uuid + ", testSuiteName=" + testSuiteName + ", moduleName="
				+ moduleName + ", testScripts=" + testScripts + ", project=" + project + "]";
	}

}
