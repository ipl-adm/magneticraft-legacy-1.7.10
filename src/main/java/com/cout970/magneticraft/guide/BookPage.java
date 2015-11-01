package com.cout970.magneticraft.guide;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class BookPage {

    @Expose
    public List<CompHolder> gadgets;

    public BookPage() {
        gadgets = new ArrayList<>();
    }

    public void addComponent(IPageComp comp) {
        gadgets.add(new CompHolder(comp));
    }
}
