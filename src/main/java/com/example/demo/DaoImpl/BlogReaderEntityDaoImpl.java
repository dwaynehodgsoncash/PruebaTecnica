package com.example.demo.DaoImpl;

import com.example.demo.Dao.BlogReaderEntityDao;
import com.example.demo.Entity.BlogReaderEntity;
import com.example.demo.Entity.ReaderEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

@Stateless
public class BlogReaderEntityDaoImpl implements BlogReaderEntityDao {
    public List<BlogReaderEntity> findAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        List<BlogReaderEntity> listaBlogReaderEntity= Collections.emptyList();
        try {
            transaction.begin();
            TypedQuery<BlogReaderEntity> resultadoBlogReaderEntity = entityManager.createNamedQuery("BlogReader.findAll", BlogReaderEntity.class);
            if (!resultadoBlogReaderEntity.getResultList().isEmpty()){
                listaBlogReaderEntity=resultadoBlogReaderEntity.getResultList();
            }

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return listaBlogReaderEntity;
    }
    public BlogReaderEntity findByBlogReaderEntity(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        BlogReaderEntity BlogReader= new BlogReaderEntity();
        try {
            transaction.begin();
            TypedQuery<BlogReaderEntity> resultadoBlogReaderEntity = entityManager.createNamedQuery("BlogReader.findById", BlogReaderEntity.class);
            resultadoBlogReaderEntity.setParameter("id",id);
            if (!resultadoBlogReaderEntity.getResultList().isEmpty()){
                BlogReader=resultadoBlogReaderEntity.getSingleResult();
            }

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return BlogReader;
    }

    public BlogReaderEntity create(BlogReaderEntity blogReader){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(blogReader);
            entityManager.getTransaction().commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return blogReader;
    }

    public BlogReaderEntity update(BlogReaderEntity blogReader){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(blogReader);
            entityManager.getTransaction().commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return blogReader;
    }

    public BlogReaderEntity delete(BlogReaderEntity blogReader){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            BlogReaderEntity blogReaderEntity = entityManager.find(BlogReaderEntity.class   ,blogReader.getId());
            //It turns out that the exception is thrown because the cascade option. This works fine.

            entityManager.remove(blogReaderEntity);
            entityManager.getTransaction().commit();

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return blogReader;
    }
}
