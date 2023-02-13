package org.search.hibernate.script;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@Entity
@Indexed(index = "idx_testscript")
@Table(name = "test_script")
@org.hibernate.search.annotations.AnalyzerDef(name = "textanalyzer", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
		@TokenFilterDef(factory = LowerCaseFilterFactory.class),
		@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
				@Parameter(name = "language", value = "English") }) })
public class TestScript {

	@Id
	private Long Id;

	@Field(termVector = TermVector.YES)
	@Column(name = "test_script_name")
	private String testScriptName;

	@Field(termVector = TermVector.YES)
	@Column(name = "requestBody")
	private String requestBody;

	@Field(termVector = TermVector.YES, analyzer = @Analyzer(definition = "textanalyzer"))
	private String objective;

	@Field(termVector = TermVector.YES)
	private String organization;

	@Field(termVector = TermVector.YES)
	private String uuid;

	@Column(name = "tagName")
	@Field(termVector = TermVector.YES)
	@IndexedEmbedded
	private String[] tagName;
	
	@Field(termVector = TermVector.YES)
	private String created_by;
	
	@Field(termVector = TermVector.YES)
	private String sequence_id;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTestScriptName() {
		return testScriptName;
	}

	public void setTestScriptName(String testScriptName) {
		this.testScriptName = testScriptName;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String[] getTagName() {
		return tagName;
	}

	public void setTagName(String[] tagName) {
		this.tagName = tagName;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getSequence_id() {
		return sequence_id;
	}

	public void setSequence_id(String sequence_id) {
		this.sequence_id = sequence_id;
	}

	@Override
	public String toString() {
		return "TestScript [Id=" + Id + ", testScriptName=" + testScriptName + ", requestBody=" + requestBody
				+ ", objective=" + objective + ", organization=" + organization + ", uuid=" + uuid + ", tagName="
				+ Arrays.toString(tagName) + ", created_by=" + created_by + ", sequence_id=" + sequence_id + "]";
	}

	
}
