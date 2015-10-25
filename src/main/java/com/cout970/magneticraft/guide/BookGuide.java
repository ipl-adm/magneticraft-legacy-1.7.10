package com.cout970.magneticraft.guide;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

public class BookGuide {

    @Expose
    public HashMap<String, BookPage> pages;

    public BookGuide() {
        pages = new HashMap<>();
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
