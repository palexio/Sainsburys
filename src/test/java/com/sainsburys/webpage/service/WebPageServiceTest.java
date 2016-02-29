package com.sainsburys.webpage.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sainsburys.webpage.model.Results;

import junit.framework.Assert;

public class WebPageServiceTest {

	final static Logger logger = LoggerFactory.getLogger(WebPageServiceTest.class);

	@Test
	public void executeTask0Items() {
		
		// execute test
		Results results = new WebPageServiceMock("webpage0element.html").executeTask("");
		
		// assert
		Assert.assertNotNull(results);
		Assert.assertEquals("0.00", results.getTotal());
		Assert.assertNotNull(results.getResults());
		Assert.assertEquals(0, results.getResults().size());
	}

	@Test
	public void executeTask1Item() {
		
		// execute test
		Results results = new WebPageServiceMock("webpage1element.html").executeTask("");
		
		// assert
		Assert.assertNotNull(results);
		Assert.assertEquals("1.50", results.getTotal());
		Assert.assertNotNull(results.getResults());
		Assert.assertEquals(1, results.getResults().size());
		Assert.assertEquals("Sainsbury's Avocado Ripe & Ready XL Loose 300g ", results.getResults().get(0).getTitle());
		Assert.assertEquals("34kb", results.getResults().get(0).getSize());
		Assert.assertEquals("1.50", results.getResults().get(0).getUnitPrice());
		Assert.assertEquals("Avocados", results.getResults().get(0).getDescription());
	}

	@Test
	public void executeTaskMoreItems() {
		
		// execute test
		Results results = new WebPageServiceMock("webpage.html").executeTask("");

		// assert
		Assert.assertNotNull(results);
		Assert.assertEquals("6.50", results.getTotal());
		Assert.assertNotNull(results.getResults());
		Assert.assertEquals(3, results.getResults().size());
		
		Assert.assertEquals("Sainsbury's Avocado Ripe & Ready XL Loose 300g ", results.getResults().get(0).getTitle());
		Assert.assertEquals("34kb", results.getResults().get(0).getSize());
		Assert.assertEquals("1.50", results.getResults().get(0).getUnitPrice());
		Assert.assertEquals("Avocados", results.getResults().get(0).getDescription());

		Assert.assertEquals("Sainsbury's Avocado, Ripe & Ready x2 ", results.getResults().get(1).getTitle());
		Assert.assertEquals("100kb", results.getResults().get(1).getSize());
		Assert.assertEquals("1.80", results.getResults().get(1).getUnitPrice());
		Assert.assertEquals("Avocados", results.getResults().get(1).getDescription());

		Assert.assertEquals("Sainsbury's Avocados, Ripe & Ready x4 ", results.getResults().get(2).getTitle());
		Assert.assertEquals("99kb", results.getResults().get(2).getSize());
		Assert.assertEquals("3.20", results.getResults().get(2).getUnitPrice());
		Assert.assertEquals("Avocados", results.getResults().get(2).getDescription());
	}
}
