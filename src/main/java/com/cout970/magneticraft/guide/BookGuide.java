package com.cout970.magneticraft.guide;

import java.util.HashMap;

import com.google.gson.annotations.Expose;

public class BookGuide {

	@Expose
	public HashMap<String, BookPage> pages;

	public BookGuide() {
		pages = new HashMap<String, BookPage>();
	}

	public void addPage(String name, BookPage page) {
		pages.put(name, page);
	}

	public BookPage getMainPage() {
		return pages.get("main");
	}

	public BookPage getPage(String page) {
		return pages.get(page);
	}
}