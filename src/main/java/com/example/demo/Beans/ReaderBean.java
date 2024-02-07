package com.example.demo.Beans;

import com.example.demo.Dao.ReaderEntityDao;
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

@Named("ReaderBean")
@ViewScoped
public class ReaderBean implements Serializable {
    private ReaderEntity readerEntity;
    private List<ReaderEntity> listaReaderEntity;
    private List<ReaderEntity> filtroReaderEntity;

    @EJB
    ReaderEntityDao readerEntityDao;
    public void loadData() {
        try{
            this.listaReaderEntity= Collections.emptyList();
            this.listaReaderEntity = readerEntityDao.findAll();
        }catch (Exception e){
            /* PrimeFaces.current().executeScript("PF('manageLoggedDialog').show()");*/
        }

    }

    public void openNew() {
        this.readerEntity= new ReaderEntity();
    }

    public void saveReader() {
        if (this.readerEntity.getId() == 0) {
            ReaderEntity readerEncontrado= new ReaderEntity();

            readerEncontrado=readerEntityDao.findByReaderEntity(readerEntity.getName());
            if(readerEncontrado.getName() !=null){
                String mensaje="Registro Ya Existe";
                info(mensaje);
            }else{
                readerEntityDao.create(readerEntity);
                listaReaderEntity=readerEntityDao.findAll();

                String mensaje="Usuario adicionado";
                info(mensaje);
                // obteniendo el mensage desde el fichero de recursos, con la llave message_user_added
                PrimeFaces.current().executeScript("PF('manageReaderDialog').hide()"); //Este code permite cerrar el dialog cuyo id es manageUserDialog. Este identificador es el widgetVar
                PrimeFaces.current().ajax().update("form:dt-readers"); // Este code es para refrescar el componente con id dt-users que se encuentra dentro del formulario con id form
            }
        } else {
            readerEntityDao.update(readerEntity);
            listaReaderEntity=readerEntityDao.findAll();

            String mensaje="Registro modificado";
            info(mensaje);
            PrimeFaces.current().executeScript("PF('manageReaderDialog').hide()");
            PrimeFaces.current().ajax().update("form:dt-readers");
        }
    }

    public void deleteReader() {
        if (this.readerEntity.getId() != 0) {
            readerEntityDao.delete(readerEntity);
            listaReaderEntity=readerEntityDao.findAll();

            String mensaje="Registro Borrado";
            info(mensaje);
        }
        PrimeFaces.current().executeScript("PF('manageReaderDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-readers");
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

    public ReaderEntity getReaderEntity() {
        return readerEntity;
    }

    public void setReaderEntity(ReaderEntity readerEntity) {
        this.readerEntity = readerEntity;
    }

    public List<ReaderEntity> getListaReaderEntity() {
        return listaReaderEntity;
    }

    public void setListaReaderEntity(List<ReaderEntity> listaReaderEntity) {
        this.listaReaderEntity = listaReaderEntity;
    }

    public List<ReaderEntity> getFiltroReaderEntity() {
        return filtroReaderEntity;
    }

    public void setFiltroReaderEntity(List<ReaderEntity> filtroReaderEntity) {
        this.filtroReaderEntity = filtroReaderEntity;
    }
}
