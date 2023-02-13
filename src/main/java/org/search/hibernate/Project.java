package org.search.hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

@Entity
@Indexed(index = "idx_project")
@Table(name = "project")
public class Project {

	@Id
	private Long Id;

	@Field(termVector = TermVector.YES)
	private String projectName;

	@Field(termVector = TermVector.YES)
	private String project_description;

	@Field(termVector = TermVector.YES)
	private String team_id;

	@Field(termVector = TermVector.YES)
	private String organization;

	@Field(termVector = TermVector.YES)
	private String uuid;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProject_description() {
		return project_description;
	}

	public void setProject_description(String project_description) {
		this.project_description = project_description;
	}

	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
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

}
