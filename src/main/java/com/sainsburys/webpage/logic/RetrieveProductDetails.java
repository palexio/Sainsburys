package com.sainsburys.webpage.logic;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sainsburys.webpage.model.Item;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;

/**
 * The class contains the logic to retrieve and elaborate the product page
 * @author Alessio
 *
 */
public class RetrieveProductDetails implements Callable<Item> {

	final static Logger logger = LoggerFactory.getLogger(RetrieveProductDetails.class);

	private Item item;

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}


	/**
	 * Retrieves the size and the description of a product
	 */
	public Item call() throws Exception {
		final Browser browser = BrowserFactory.getInstance().createBrowser();
		invokeFrame(item.getLink(), browser);


		int size = browser.getHTML().length();
		item.setSize(String.valueOf(size / 1024) + "kb");
		DOMElement content = browser.getDocument().findElement(By.id("information"));
		if(content != null) {
			List<DOMElement> paragraphs = content.findElements(By.tagName("p"));
			if (paragraphs != null && paragraphs.size() > 0) {
				item.setDescription(paragraphs.get(0).getInnerText());
			} else {
				logger.error("paragraph not found");
			}
		} else {
			logger.error("information content not found");
		}
		browser.dispose();
		return item;
	}


	protected void invokeFrame(final String link, final Browser browser) {
		Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
			public void invoke(Browser browser) {
				browser.loadURL(link);
		}
	});
	}
}
