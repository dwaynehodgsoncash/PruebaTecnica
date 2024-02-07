package com.example.demo.Dao;

import com.example.demo.Entity.BlogEntity;
import com.example.demo.Entity.ReaderEntity;

import java.util.List;

public interface BlogEntityDao {
    public List<BlogEntity> findAll();
    public BlogEntity findByBlogEntity(String title);
    public BlogEntity create(BlogEntity blog);
    public BlogEntity update(BlogEntity blog);
    public BlogEntity delete(BlogEntity blog);
}
