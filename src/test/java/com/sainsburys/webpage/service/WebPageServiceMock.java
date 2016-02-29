package com.sainsburys.webpage.service;

import java.io.InputStream;
import java.util.concurrent.Callable;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sainsburys.webpage.logic.BrowserFactory;
import com.sainsburys.webpage.logic.RetrieveProductDetails;
import com.sainsburys.webpage.logic.RetrieveProductDetailsMock;
import com.sainsburys.webpage.model.Item;
import com.sainsburys.webpage.model.Results;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;

/**
 * Mock class to retrieve the main product list page from the file system
 * @author Alessio
 *
 */
public class WebPageServiceMock extends WebPageService {
	
	final static Logger logger = LoggerFactory.getLogger(WebPageServiceMock.class);
	
	String page;
	public WebPageServiceMock(String page) {
		this.page = page;
	}
	
	/**
	 * loads the list of products page from the file system
	 */
	protected Results startProcessPage(final String url) {
		
		final Browser browser = BrowserFactory.getInstance().createBrowser();

		String html = null;
		
		try {
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(page);	
			html = IOUtils.toString(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		browser.loadHTML(html);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return onFinishingLoadingFrame(browser, new LoadAdapter(){});
	}
	
	/**
	 * Instantiate the object to retrieve the product pages from the filesystem
	 */
	protected Callable<Item> getRetrieveProductDetails(Item item) {
		RetrieveProductDetails ret = new RetrieveProductDetailsMock();
		ret.setItem(item);
		return ret;
	}
}
