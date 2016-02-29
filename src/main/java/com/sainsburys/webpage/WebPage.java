package com.sainsburys.webpage;


import com.sainsburys.webpage.service.WebPageService;

/**
 * 
 * Main class, entry point for the application
 *
 */
public class WebPage 
{
    public static void main( String[] args ) {

    	if(args.length == 1) {
			new WebPageService().executeTask(args[0]);
		} else {
    		System.out.println("Usage: WebPage <url>");
		}

    }
}
