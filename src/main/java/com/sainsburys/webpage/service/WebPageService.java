package com.sainsburys.webpage.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sainsburys.webpage.logic.BrowserFactory;
import com.sainsburys.webpage.logic.RetrieveProductDetails;
import com.sainsburys.webpage.logic.SerializeJson;
import com.sainsburys.webpage.model.Item;
import com.sainsburys.webpage.model.Results;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.LoadListener;

/**
 * The class contains the logic to retrieve and elaborate the product list page
 * @author Alessio
 *
 */
public class WebPageService {

	final static Logger logger = LoggerFactory.getLogger(WebPageService.class);

	private static final int THREAD_POOL_SIZE = 10;

	/**
	 * The method gets a string url in input, validate the input and calls the
	 * service to process the webpage
	 * 
	 * @param url
	 *            the url to download
	 * @throws IOException
	 */
	public Results executeTask(final String url) {
		
		setLibraryLogLevel();

		if (url != null) {
			return startProcessPage(url);
		} else {
			logger.error("url can't be null");
			return null;
		}
	}
	
	/**
	 * loads the list of products page from the Internet
	 */
	protected Results startProcessPage(final String url) {
		
		final Browser browser = BrowserFactory.getInstance().createBrowser();

		browser.addLoadListener(new LoadAdapter() {
			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event) {
				synchronized(this) {
					onFinishingLoadingFrame(browser, this);
				}
			}
		});
		browser.loadURL(url);
		return null;
	}

	
	protected Results onFinishingLoadingFrame(final Browser browser, LoadListener loadAdapter) {
		if(!browser.isDisposed()) {
			final DOMDocument document = browser.getDocument();
			if(document.findElement(By.id("shelfPage")) != null) {
				browser.removeLoadListener(loadAdapter);
				Results results = extractData(document);
				browser.dispose();
				return results;
			}
		}
		return null;
	}

	/**
	 * The methods contains the logic to elaborate the page
	 * @param document
	 * @return
	 */
	private Results extractData(final DOMDocument document) {
		final Results results = new Results();
		
		// Extract data from the product list page
		extractProductsData(document, results);
		
		// Extract data from the product pages
		loadProductsSize(results);
		
		// Calculates the sum of all unit prices
		calculateTotal(results);
		
		// Serialize the output object
		String json = SerializeJson.getInstance().toJsonString(results);
		
		// log the Json to console
		logger.info(json);
		
		return results;
	}

	/**
	 * Loop over the products in the page 
	 * @param document
	 * @param results
	 */
	private void extractProductsData(final DOMDocument document, Results results) {
		List<DOMElement> productElements = document.findElements(By.className("product "));
		for (DOMElement domElement : productElements) {
			results.getResults().add(extractProductData(domElement));
		}
	}

	/**
	 * Extract data from the product list page
	 * @param domElement
	 * @return
	 */
	private Item extractProductData(final DOMElement domElement) {

		String title = domElement.findElement(By.tagName("a")).getInnerText();
		final String link = domElement.findElement(By.tagName("a")).getAttribute("href");
		String unitPrice = domElement.findElement(By.className("pricePerUnit")).getInnerText();
		unitPrice = unitPrice.replaceAll("Â£", "").replaceAll("£", "").replaceAll("/unit", "").trim();
		
		Item item = new Item();
		item.setUnitPrice(unitPrice);
		try {
			BigDecimal price = new BigDecimal(unitPrice).setScale(2);
			item.setPrice(price);
		} catch(Exception e) {
			item.setPrice(null);
		}
		item.setLink(link);
		item.setSize("0kb");
		item.setTitle(title);
		return item;
	}

	/**
	 * Creates a pool of thread to get all the product pages in parallel and waits for the end of all.
	 * @param results
	 */
	private void loadProductsSize(final Results results) {
        
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<Item>> list = new ArrayList<Future<Item>>();

        for(int i=0; i < results.getResults().size(); i++){
        	Item item = results.getResults().get(i);
        	Callable<Item> callable = getRetrieveProductDetails(item);
            Future<Item> future = executor.submit(callable);
            list.add(future);
        }
        for(Future<Item> future : list){
            try {
                future.get();
            } catch (InterruptedException e) {
            	logger.error(e.getMessage());
            	e.printStackTrace();
            } catch (ExecutionException e) {
            	logger.error(e.getMessage());
            	e.printStackTrace();
			}
        }
        executor.shutdown();
	}

	/**
	 * Instantiate the object to retrieve the product pages from the internet
	 */
	protected Callable<Item> getRetrieveProductDetails(Item item) {
		RetrieveProductDetails ret = new RetrieveProductDetails();
		ret.setItem(item);
		return ret;
	}

	/**
	 * Calculates the sum of all unit prices
	 * @param results
	 */
	private void calculateTotal(Results results) {
		BigDecimal total = BigDecimal.ZERO.setScale(2);
		List<Item> items = results.getResults();
		for (Item item : items) {
			if(item.getPrice() != null) {
				total = total.add(item.getPrice());
			}
		}
		results.setTotal(total.toString());
	}

	/**
	 * Convenient method to set jxBrowser library log level
	 */
	private void setLibraryLogLevel() {
		LoggerProvider.getBrowserLogger().setLevel(Level.OFF);
    	LoggerProvider.getIPCLogger().setLevel(Level.OFF);
    	LoggerProvider.getChromiumProcessLogger().setLevel(Level.OFF);
	}
}
