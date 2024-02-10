package com.example.demo.Beans;

import com.example.demo.Dao.UsuarioEntityDao;
import com.example.demo.Entity.UsuarioEntity;
import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.HashChecker;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.type.ListType;
import org.primefaces.PrimeFaces;

import java.io.IOException;
import java.io.Serializable;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Named("UsuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {
    private String usuario;
    private String usuarioLogeado;
    private String contrasena;
    private UsuarioEntity usuarioEntity;
    private List<UsuarioEntity> listaUsuarios;
    private List<UsuarioEntity> filtroUsuarios;

    @EJB
    private UsuarioEntityDao usuarioEntityDao;

    public void openNew() {
        this.usuarioEntity= new UsuarioEntity();
    }

    public void login() throws IOException {
        this.usuarioEntity=usuarioEntityDao.findByUsuario(usuario);

        if (usuarioEntity.getId()==0){
            String mensaje="El nombre del Usuario o la Contraseña son incorrectos";
            info(mensaje);
        }else{
            BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

            boolean verified = Password.check(contrasena, usuarioEntity.getClave())
                    .addPepper("shared-secret")
                    .with(bcrypt);

            if(!usuarioEntity.getUsuario().equalsIgnoreCase(usuario)){
                String mensaje="El nombre del Usuario o la Contraseña son incorrectos";
                info(mensaje);
            }else{
                if(!verified ){
                    String mensaje="El nombre del Usuario o la Contraseña son incorrectos";
                    info(mensaje);
                }
                else {
                    //setaer la variable session de usuario utilizado en el SecurityFilter
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    HttpSession session = (HttpSession) externalContext.getSession(true);
                    session.setAttribute("usuario", usuarioEntity.getUsuario());

                    getFacesContext()
                            .getExternalContext()
                            .redirect(
                                    getRequest().getContextPath() + "/pages/welcome/welcome.xhtml"
                            );
                    usuarioLogeado = String.valueOf((usuarioEntity.getNombre()+"."+usuarioEntity.getApellido()));
                }
            }
        }
    }

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

    public void loadData() {
        try{
            this.listaUsuarios= Collections.emptyList();
            this.listaUsuarios = usuarioEntityDao.findAllUsuario();
        }catch (Exception e){
           /* PrimeFaces.current().executeScript("PF('manageLoggedDialog').show()");*/
        }

    }
    public void saveUser() {
        if (this.usuarioEntity.getId() == 0) {
            UsuarioEntity usuarioEncontrado= new UsuarioEntity();

            usuarioEncontrado=usuarioEntityDao.findByUsuario(usuarioEntity.getUsuario());
            if(usuarioEncontrado.getUsuario() !=null){
                String mensaje="Usuario Existe";
                info(mensaje);
            }else{
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);
                Hash hash = Password.hash(usuarioEntity.getClave())
                        .addPepper("shared-secret")
                        .with(bcrypt);
                usuarioEntity.setClave(hash.getResult());

                usuarioEntityDao.create(usuarioEntity);
                listaUsuarios=usuarioEntityDao.findAllUsuario();

                String mensaje="Usuario adicionado";
                info(mensaje);
                // obteniendo el mensage desde el fichero de recursos, con la llave message_user_added
                PrimeFaces.current().executeScript("PF('manageUserDialog').hide()"); //Este code permite cerrar el dialog cuyo id es manageUserDialog. Este identificador es el widgetVar
                PrimeFaces.current().ajax().update("form:dt-users"); // Este code es para refrescar el componente con id dt-users que se encuentra dentro del formulario con id form
            }
        } else {
            usuarioEntityDao.update(usuarioEntity);
            listaUsuarios=usuarioEntityDao.findAllUsuario();

            String mensaje="Usuario modificado";
            info(mensaje);
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
            PrimeFaces.current().ajax().update("form:dt-users");
        }
    }

    public void deleteUser() {
        if (this.usuarioEntity.getId() != 0) {
            usuarioEntityDao.delete(usuarioEntity);
            listaUsuarios=usuarioEntityDao.findAllUsuario();

            String mensaje="Usuario Borrado";
            info(mensaje);
        }
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-users");
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

    public void warn() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Message Content"));
    }

    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Message Content."));
    }
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<UsuarioEntity> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<UsuarioEntity> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<UsuarioEntity> getFiltroUsuarios() {
        return filtroUsuarios;
    }

    public void setFiltroUsuarios(List<UsuarioEntity> filtroUsuarios) {
        this.filtroUsuarios = filtroUsuarios;
    }

    public UsuarioEntity getUsuarioEntity() {
        return usuarioEntity;
    }

    public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
        this.usuarioEntity = usuarioEntity;
    }

    public String getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(String usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }
}
