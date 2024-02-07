package com.example.demo.Beans;

import com.example.demo.Dao.BlogEntityDao;
import com.example.demo.Dao.ReaderEntityDao;
import com.example.demo.Entity.BlogEntity;
import com.example.demo.Entity.ReaderEntity;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named("BlogBean")
@ViewScoped
public class BlogBean implements Serializable {
    private BlogEntity blogEntity;
    private List<BlogEntity> listaBlogEntity;
    private List<BlogEntity> filtroBlogEntity;

    @EJB
    BlogEntityDao blogEntityDao;

    public void loadData() {
        try{
            this.listaBlogEntity= Collections.emptyList();
            this.listaBlogEntity = blogEntityDao.findAll();
        }catch (Exception e){
            /* PrimeFaces.current().executeScript("PF('manageLoggedDialog').show()");*/
        }

    }

    public void openNew() {
        this.blogEntity= new BlogEntity();
    }

    public void saveBlog() {
        if (this.blogEntity.getId() == 0) {
            BlogEntity blogEntityEncontrado= new BlogEntity();

            blogEntityEncontrado=blogEntityDao.findByBlogEntity(blogEntity.getTitle());
            if(blogEntityEncontrado.getTitle() !=null){
                String mensaje="Registro Ya Existe";
                info(mensaje);
            }else{
                blogEntityDao.create(blogEntity);
                listaBlogEntity=blogEntityDao.findAll();

                String mensaje="Usuario adicionado";
                info(mensaje);
                // obteniendo el mensage desde el fichero de recursos, con la llave message_user_added
                PrimeFaces.current().executeScript("PF('manageBlogDialog').hide()"); //Este code permite cerrar el dialog cuyo id es manageUserDialog. Este identificador es el widgetVar
                PrimeFaces.current().ajax().update("form:dt-blogs"); // Este code es para refrescar el componente con id dt-users que se encuentra dentro del formulario con id form
            }
        } else {
            blogEntityDao.update(blogEntity);
            listaBlogEntity=blogEntityDao.findAll();

            String mensaje="Registro modificado";
            info(mensaje);
            PrimeFaces.current().executeScript("PF('manageBlogDialog').hide()");
            PrimeFaces.current().ajax().update("form:dt-blogs");
        }
    }

    public void deleteBlog() {
        if (this.blogEntity.getId() != 0) {
            blogEntityDao.delete(blogEntity);
            listaBlogEntity=blogEntityDao.findAll();

            String mensaje="Registro Borrado";
            info(mensaje);
        }
        PrimeFaces.current().executeScript("PF('manageBlogDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-blogs");
    }

    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) getFacesContext()
                .getExternalContext()
                .getRequest();
    }

    public void info(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mensaje));
    }


    public BlogEntity getBlogEntity() {
        return blogEntity;
    }

    public void setBlogEntity(BlogEntity blogEntity) {
        this.blogEntity = blogEntity;
    }

    public List<BlogEntity> getListaBlogEntity() {
        return listaBlogEntity;
    }

    public void setListaBlogEntity(List<BlogEntity> listaBlogEntity) {
        this.listaBlogEntity = listaBlogEntity;
    }

    public List<BlogEntity> getFiltroBlogEntity() {
        return filtroBlogEntity;
    }

    public void setFiltroBlogEntity(List<BlogEntity> filtroBlogEntity) {
        this.filtroBlogEntity = filtroBlogEntity;
    }
}
