package com.example.demo.Dao;

import com.example.demo.Entity.BlogReaderEntity;
import com.example.demo.Entity.ReaderEntity;

import java.util.List;

public interface BlogReaderEntityDao {
    public List<BlogReaderEntity> findAll();
    public BlogReaderEntity findByBlogReaderEntity(int id);
    public BlogReaderEntity create(BlogReaderEntity blogReader);
    public BlogReaderEntity update(BlogReaderEntity blogReader);
    public BlogReaderEntity delete(BlogReaderEntity blogReader);
}
