package com.cl932.rsmw.web;


import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;


public abstract class BasePage extends WebPage {

    private MarkupContainer defaultModal;

    public BasePage(PageParameters params){
        super(params);
        initPage();
    }

    public BasePage(){
        initPage();
    }

    private void initPage(){
        defaultModal = new EmptyPanel("defaultModal");
        defaultModal.setOutputMarkupId(true);
        add(defaultModal);
    }




}