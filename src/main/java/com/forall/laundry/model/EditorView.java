package com.forall.laundry.model;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class EditorView {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}