package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher();
	final static ArrayList<String> pagesVisited = new ArrayList<String>();
	

	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
         * 2. Ignoring external links, links to the current page, or red links
         * 3. Stopping when reaching "Philosophy", a page with no links or a page
         *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		String philoURL = "https://en.wikipedia.org/wiki/Philosophy";
		//String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		String url = "https://en.wikipedia.org/wiki/Property_(philosophy)";

		//ArrayList<String> pagesVisited = new ArrayList<String>();

		if (goToPhilo(url, philoURL)) {
			System.out.println("Pages Visited: " + pagesVisited.size());
			for (int i = 0; i < pagesVisited.size(); i++) {
				System.out.println(pagesVisited.get(i));
			}
		}
	}

	private static boolean goToPhilo(String url, String philoURL) throws IOException {
		if(pagesVisited.contains(url)) {	//in a loop
			return false;
		}
		else {
			pagesVisited.add(url);
			if (url.equals(philoURL)) {
				return true;
			}
			else {
				Elements paragraphs = wf.fetchWikipedia(url);
                		Element firstPara = paragraphs.get(0);

	                	Iterable<Node> iter = new WikiNodeIterable(firstPara);
        	        	for (Node node: iter) {
                	        	if (node instanceof Element) {
                        	        	Element elt = (Element) node;
						if (elt.tagName().equals("a")) {
							url = elt.attr("abs.href");
							System.out.println(url);
							return true;
							//return goToPhilo(url, philoURL);
						}
                        		}
                		}
				// no links on page
				return false;
			}
		}

	}
}
