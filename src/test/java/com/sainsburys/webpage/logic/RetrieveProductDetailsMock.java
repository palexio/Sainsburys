package com.sainsburys.webpage.logic;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sainsburys.webpage.model.Results;
import com.teamdev.jxbrowser.chromium.Browser;

/**
 * Mock class to retrieve product pages from the file system
 * @author Alessio
 *
 */
public class RetrieveProductDetailsMock extends RetrieveProductDetails {
	
	final static Logger logger = LoggerFactory.getLogger(RetrieveProductDetailsMock.class);

	/**
	 * loads the list of products page from the file system
	 */
	protected void invokeFrame(final String link, final Browser browser) {
		try {
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(link);	
			String html = IOUtils.toString(is);
			browser.loadHTML(html);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
