package com.example.demo.Beans;

import com.example.demo.Dao.BlogEntityDao;
import com.example.demo.Dao.BlogReaderEntityDao;
import com.example.demo.Dao.ReaderEntityDao;
import com.example.demo.Entity.BlogEntity;
import com.example.demo.Entity.BlogReaderEntity;
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

@Named("BlogReaderBean")
@ViewScoped
public class BlogReaderBean implements Serializable {
    private BlogReaderEntity blogReaderEntity;
    private List<BlogReaderEntity> listaBlogReaderEntity;
    private List<BlogReaderEntity> filtroBlogReaderEntity;
    private List<BlogEntity> listaBlog;
    private List<ReaderEntity> listaReader;
    private Integer idReader;
    private Integer idBlog;

    @EJB
    BlogReaderEntityDao blogReaderEntityDao;
    @EJB
    BlogEntityDao blogEntityDao;
    @EJB
    ReaderEntityDao readerEntityDao;

    public void loadData() {
        try{
            this.listaBlogReaderEntity= Collections.emptyList();
            this.listaBlogReaderEntity = blogReaderEntityDao.findAll();
            this.listaReader=readerEntityDao.findAll();
            this.listaBlog=blogEntityDao.findAll();
        }catch (Exception e){
            /* PrimeFaces.current().executeScript("PF('manageLoggedDialog').show()");*/
        }
    }

    public void openNew() {
        this.blogReaderEntity= new BlogReaderEntity();
        this.blogReaderEntity.setIdBlog(new BlogEntity());
        this.blogReaderEntity.setIdReader(new ReaderEntity());
    }

    public void saveBlogReader() {
        if (this.blogReaderEntity.getId() == 0) {
            BlogReaderEntity blogReaderEncontrado= new BlogReaderEntity();

            blogReaderEncontrado=blogReaderEntityDao.findByBlogReaderEntity(blogReaderEntity.getId());
            if(blogReaderEncontrado.getIdReader() !=null){
                String mensaje="Registro Ya Existe";
                info(mensaje);
            }else{
                blogReaderEntityDao.create(blogReaderEntity);
                listaBlogReaderEntity=blogReaderEntityDao.findAll();

                String mensaje="Usuario adicionado";
                info(mensaje);
                // obteniendo el mensage desde el fichero de recursos, con la llave message_user_added
                PrimeFaces.current().executeScript("PF('manageBlogReaderDialog').hide()"); //Este code permite cerrar el dialog cuyo id es manageUserDialog. Este identificador es el widgetVar
                PrimeFaces.current().ajax().update("form:dt-blogreaders"); // Este code es para refrescar el componente con id dt-users que se encuentra dentro del formulario con id form
            }
        } else {
            blogReaderEntityDao.update(blogReaderEntity);
            listaBlogReaderEntity=blogReaderEntityDao.findAll();

            String mensaje="Registro modificado";
            info(mensaje);
            PrimeFaces.current().executeScript("PF('manageBlogReaderDialog').hide()");
            PrimeFaces.current().ajax().update("form:dt-blogreaders");
        }
    }

    public void deleteBlogReader() {
        if (this.blogReaderEntity.getId() != 0) {
            blogReaderEntityDao.delete(blogReaderEntity);
            listaBlogReaderEntity=blogReaderEntityDao.findAll();

            String mensaje="Registro Borrado";
            info(mensaje);
        }
        PrimeFaces.current().executeScript("PF('manageBlogReaderDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-blogreaders");
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

    public List<BlogEntity> getListaBlog() {
        return listaBlog;
    }

    public void setListaBlog(List<BlogEntity> listaBlog) {
        this.listaBlog = listaBlog;
    }

    public List<ReaderEntity> getListaReader() {
        return listaReader;
    }

    public void setListaReader(List<ReaderEntity> listaReader) {
        this.listaReader = listaReader;
    }

    public BlogReaderEntity getBlogReaderEntity() {
        return blogReaderEntity;
    }

    public void setBlogReaderEntity(BlogReaderEntity blogReaderEntity) {
        this.blogReaderEntity = blogReaderEntity;
    }

    public List<BlogReaderEntity> getListaBlogReaderEntity() {
        return listaBlogReaderEntity;
    }

    public void setListaBlogReaderEntity(List<BlogReaderEntity> listaBlogReaderEntity) {
        this.listaBlogReaderEntity = listaBlogReaderEntity;
    }

    public List<BlogReaderEntity> getFiltroBlogReaderEntity() {
        return filtroBlogReaderEntity;
    }

    public void setFiltroBlogReaderEntity(List<BlogReaderEntity> filtroBlogReaderEntity) {
        this.filtroBlogReaderEntity = filtroBlogReaderEntity;
    }

    public Integer getIdReader() {
        return idReader;
    }

    public void setIdReader(Integer idReader) {
        this.idReader = idReader;
    }

    public Integer getIdBlog() {
        return idBlog;
    }

    public void setIdBlog(Integer idBlog) {
        this.idBlog = idBlog;
    }
}
