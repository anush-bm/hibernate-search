package org.search.hibernate.script;

import java.util.List;

import org.search.hibernate.suite.TestSuite;
import org.search.hibernate.suite.TestSuiteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestScriptResource {

	private final TestScriptService testScriptService;
	private final  TestSuiteService testSuiteService;
	

	public TestScriptResource(TestScriptService testScriptService, TestSuiteService testSuiteService) {
		this.testScriptService = testScriptService;
		this.testSuiteService = testSuiteService;
	}

	@RequestMapping(value = "/test-scripts", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<TestScript> findAll() {
		return testScriptService.findAll();
	}

	@RequestMapping(value = "/test-scripts-es", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<TestScript> findES(@RequestParam String word) {
		return testScriptService.getPostBasedOnWord(word);
	}

	@RequestMapping(value = "/test-scripts-guess", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<TestScript> letsGuess(@RequestParam String word) {
		return testScriptService.letsGuess(word);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public @ResponseBody void save() {
		testScriptService.faceting();
	}
	
	@RequestMapping(value = "/testsCreated", method = RequestMethod.GET)
	public @ResponseBody void testsCreated(@RequestParam String email) {
		testScriptService.testsCreated(email);
	}
	
	@RequestMapping(value = "/test-suites", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody List<TestSuite> findAllSuites() {
		return testSuiteService.findAll();
	}
}

