package com.example.demo.DaoImpl;

import com.example.demo.Dao.UsuarioEntityDao;
import com.example.demo.Entity.UsuarioEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import org.hibernate.sql.ast.tree.expression.Collation;

import java.util.Collections;
import java.util.List;

@Stateless
public class UsuarioEntityDaoImpl implements UsuarioEntityDao {
    public List<UsuarioEntity> findAllUsuario() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        List<UsuarioEntity> listaUsuario= Collections.emptyList();
        try {
            transaction.begin();
            TypedQuery<UsuarioEntity> resultadoUsuario = entityManager.createNamedQuery("Usuario.findAll", UsuarioEntity.class);
            if (!resultadoUsuario.getResultList().isEmpty()){
                listaUsuario=resultadoUsuario.getResultList();
            }

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return listaUsuario;
    }

    public UsuarioEntity findByUsuario(String user) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        UsuarioEntity usuario= new UsuarioEntity();
        try {
            transaction.begin();
            TypedQuery<UsuarioEntity> resultadoUsuario = entityManager.createNamedQuery("Usuario.findByUsuario", UsuarioEntity.class);
            resultadoUsuario.setParameter("usuario",user);
            if (!resultadoUsuario.getResultList().isEmpty()){
                usuario=resultadoUsuario.getSingleResult();
            }

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return usuario;
    }

    public UsuarioEntity create(UsuarioEntity usuario){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return usuario;
    }

    public UsuarioEntity update(UsuarioEntity usuario){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return usuario;
    }

    public UsuarioEntity delete(UsuarioEntity usuario){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            //It turns out that the exception is thrown because the cascade option. This works fine.
            entityManager.remove(entityManager.merge(usuario));
            entityManager.getTransaction().commit();

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return usuario;
    }
}
