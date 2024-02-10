package com.example.demo.Beans;

import com.example.demo.Dao.UsuarioEntityDao;
import com.example.demo.Entity.UsuarioEntity;
import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named("MenuBean")
@SessionScoped
public class MenuBean implements Serializable {

    public String logout(){
        try{
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            getFacesContext()
                    .getExternalContext()
                    .redirect(
                            getRequest().getContextPath() +
                                    "/pages/security/login.xhtml"
                    );
        }catch (Exception e){
            e.printStackTrace();
        }
        return "logout";
    }

    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) getFacesContext()
                .getExternalContext()
                .getRequest();
    }
}
