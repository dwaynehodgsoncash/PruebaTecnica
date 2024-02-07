package com.example.demo.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "usuario", schema = "bandesal")
@NamedQueries({
        @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM UsuarioEntity u")
        , @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM UsuarioEntity u WHERE u.id = :id")
        , @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM UsuarioEntity u WHERE u.usuario = :usuario")
        , @NamedQuery(name = "Usuario.findByClave", query = "SELECT u FROM UsuarioEntity u WHERE u.clave = :clave")
        , @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM UsuarioEntity u WHERE u.nombre = :nombre")
        , @NamedQuery(name = "Usuario.findByApellido", query = "SELECT u FROM UsuarioEntity u WHERE u.apellido = :apellido")})
public class UsuarioEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private int id;
    @Basic
    @Column(name = "usuario")
    private String usuario;
    @Basic
    @Column(name = "clave")
    private String clave;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "apellido")
    private String apellido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntity that = (UsuarioEntity) o;
        return id == that.id && Objects.equals(usuario, that.usuario) && Objects.equals(clave, that.clave) && Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, clave, nombre, apellido);
    }
}
