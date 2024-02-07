package com.example.demo.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "blog_reader", schema = "bandesal")
@NamedQueries({
        @NamedQuery(name = "BlogReader.findAll", query = "SELECT b FROM BlogReaderEntity b")
        , @NamedQuery(name = "BlogReader.findById", query = "SELECT b FROM BlogReaderEntity b WHERE b.id = :id")})
public class BlogReaderEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private int id;
    @JoinColumn(name = "IdBlog", referencedColumnName = "Id", nullable = false)
    @ManyToOne(optional = false)
    private BlogEntity idBlog;
    @JoinColumn(name = "IdReader", referencedColumnName = "Id", nullable = false)
    @ManyToOne(optional = false)
    private ReaderEntity idReader;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BlogEntity getIdBlog() {
        return idBlog;
    }

    public void setIdBlog(BlogEntity idBlog) {
        this.idBlog = idBlog;
    }

    public ReaderEntity getIdReader() {
        return idReader;
    }

    public void setIdReader(ReaderEntity idReader) {
        this.idReader = idReader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogReaderEntity that = (BlogReaderEntity) o;
        return id == that.id && idReader == that.idReader && idBlog == that.idBlog;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idReader, idBlog);
    }
}
