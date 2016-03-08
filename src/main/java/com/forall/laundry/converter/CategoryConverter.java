package com.forall.laundry.converter;

import com.forall.laundry.service.CategoryService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 * Created by jd on 9/2/15.
 */
@Named
@RequestScoped
@FacesConverter("categoryConverter")
public class CategoryConverter implements Converter{

    @EJB
    private CategoryService categoryService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        System.out.println(categoryService);
        return categoryService.findBy(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o.toString();
    }
}