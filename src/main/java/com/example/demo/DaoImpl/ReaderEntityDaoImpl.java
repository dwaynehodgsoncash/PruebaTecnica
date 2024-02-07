package com.example.demo.DaoImpl;

import com.example.demo.Dao.ReaderEntityDao;
import com.example.demo.Entity.ReaderEntity;
import com.example.demo.Entity.UsuarioEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

@Stateless
public class ReaderEntityDaoImpl implements ReaderEntityDao {

    public List<ReaderEntity> findAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        List<ReaderEntity> listaReaderEntity= Collections.emptyList();
        try {
            transaction.begin();
            TypedQuery<ReaderEntity> resultadoReaderEntity = entityManager.createNamedQuery("Reader.findAll", ReaderEntity.class);
            if (!resultadoReaderEntity.getResultList().isEmpty()){
                listaReaderEntity=resultadoReaderEntity.getResultList();
            }

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return listaReaderEntity;
    }
    public ReaderEntity findByReaderEntity(String name) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        ReaderEntity reader= new ReaderEntity();
        try {
            transaction.begin();
            TypedQuery<ReaderEntity> resultadoReaderEntity = entityManager.createNamedQuery("Reader.findByReader", ReaderEntity.class);
            resultadoReaderEntity.setParameter("name",name);
            if (!resultadoReaderEntity.getResultList().isEmpty()){
                reader=resultadoReaderEntity.getSingleResult();
            }

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return reader;
    }

    public ReaderEntity create(ReaderEntity reader){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(reader);
            entityManager.getTransaction().commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return reader;
    }

    public ReaderEntity update(ReaderEntity reader){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(reader);
            entityManager.getTransaction().commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return reader;
    }

    public ReaderEntity delete(ReaderEntity reader){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            //It turns out that the exception is thrown because the cascade option. This works fine.
            entityManager.remove(entityManager.merge(reader));
            entityManager.getTransaction().commit();

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return reader;
    }
}
