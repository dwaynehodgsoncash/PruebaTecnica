package com.example.demo.DaoImpl;

import com.example.demo.Dao.BlogEntityDao;
import com.example.demo.Entity.BlogEntity;
import com.example.demo.Entity.ReaderEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

@Stateless
public class BlogEntityDaoImpl implements BlogEntityDao {
    public List<BlogEntity> findAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        List<BlogEntity> listaBlogEntity= Collections.emptyList();
        try {
            transaction.begin();
            TypedQuery<BlogEntity> resultadoBlogEntity = entityManager.createNamedQuery("Blog.findAll", BlogEntity.class);
            if (!resultadoBlogEntity.getResultList().isEmpty()){
                listaBlogEntity=resultadoBlogEntity.getResultList();
            }

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return listaBlogEntity;
    }

     public BlogEntity findByBlogEntity(String title) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        BlogEntity blog= new BlogEntity();
        try {
            transaction.begin();
            TypedQuery<BlogEntity> resultadoBlogEntity = entityManager.createNamedQuery("Blog.findByBlog", BlogEntity.class);
            resultadoBlogEntity.setParameter("title",title);
            if (!resultadoBlogEntity.getResultList().isEmpty()){
                blog=resultadoBlogEntity.getSingleResult();
            }

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return blog;
    }

    public BlogEntity create(BlogEntity blog){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(blog);
            entityManager.getTransaction().commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return blog;
    }

    public BlogEntity update(BlogEntity blog){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(blog);
            entityManager.getTransaction().commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return blog;
    }

    public BlogEntity delete(BlogEntity blog){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            //It turns out that the exception is thrown because the cascade option. This works fine.
            entityManager.remove(entityManager.merge(blog));
            entityManager.getTransaction().commit();

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return blog;
    }
}
