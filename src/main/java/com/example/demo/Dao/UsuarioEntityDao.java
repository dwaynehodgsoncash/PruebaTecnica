package com.example.demo.Dao;

import com.example.demo.Entity.UsuarioEntity;

import java.util.List;

public interface UsuarioEntityDao {
    public List<UsuarioEntity> findAllUsuario();
    public UsuarioEntity findByUsuario(String user);
    public UsuarioEntity create(UsuarioEntity usuario);
    public UsuarioEntity update(UsuarioEntity usuario);
    public UsuarioEntity delete(UsuarioEntity usuario);
}
