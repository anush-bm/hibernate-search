package org.search.hibernate;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectResource {

	private final ProjectService projectService;

	public ProjectResource(ProjectService projectService) {
		this.projectService = projectService;
	}

	@RequestMapping(value = "/projects", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<Project> findAll() {
		return projectService.findAll();
	}

	@RequestMapping(value = "/projects-es", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<Project> findES(@RequestParam String word) {
		return projectService.getPostBasedOnWord(word);
	}

}
