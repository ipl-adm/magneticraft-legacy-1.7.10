package com.cout970.magneticraft.guide;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class BookPage {

	@Expose
	public List<CompHolder> gadgets;

	public BookPage() {
		gadgets = new ArrayList<CompHolder>();
	}

	public void addComponent(IPageComp comp) {
		gadgets.add(new CompHolder(comp));
	}
}
