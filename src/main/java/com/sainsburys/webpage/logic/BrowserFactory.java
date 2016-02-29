package com.sainsburys.webpage.logic;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;

/**
 * Factory class to provide a convenient instance of the Browser object
 * @author Alessio
 *
 */
public class BrowserFactory {
	
	private static BrowserFactory browserFactory = new BrowserFactory();
	
	public static BrowserFactory getInstance() {
		return browserFactory;
	}
	
	public Browser createBrowser() {
		final Browser browser = new Browser();
		browser.setPreferences(getPreferences(browser));
		return browser;
	}
	
	private BrowserPreferences getPreferences(final Browser browser) {
		BrowserPreferences preferences = browser.getPreferences();
		preferences.setImagesEnabled(false);
		preferences.setAllowDisplayingInsecureContent(false);
		preferences.setAllowRunningInsecureContent(false);
		preferences.setJavaEnabled(false);
		preferences.setLoadsImagesAutomatically(false);
		preferences.setPluginsEnabled(false);
		return preferences;
	}
}
