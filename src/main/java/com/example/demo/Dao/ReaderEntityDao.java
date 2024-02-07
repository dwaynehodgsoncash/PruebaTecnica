package com.example.demo.Dao;

import com.example.demo.Entity.BlogEntity;
import com.example.demo.Entity.ReaderEntity;
import com.example.demo.Entity.UsuarioEntity;

import java.util.List;

public interface ReaderEntityDao {
    public List<ReaderEntity> findAll();
    public ReaderEntity findByReaderEntity(String name);
    public ReaderEntity create(ReaderEntity reader);
    public ReaderEntity update(ReaderEntity reader);
    public ReaderEntity delete(ReaderEntity reader);
}
