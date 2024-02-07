package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlTransient;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "blog", schema = "bandesal")
@NamedQueries({
        @NamedQuery(name = "Blog.findAll", query = "SELECT u FROM BlogEntity u")
        , @NamedQuery(name = "Blog.findByBlog", query = "SELECT u FROM BlogEntity u WHERE u.title = :title")})
public class BlogEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private int id;
    @Basic
    @Column(name = "Title")
    private String title;
    @Basic
    @Column(name = "Description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBlog")
    private List<BlogReaderEntity> blogReaderList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<BlogReaderEntity> getBlogReaderList() {
        return blogReaderList;
    }

    public void setBlogReaderList(List<BlogReaderEntity> blogReaderList) {
        this.blogReaderList = blogReaderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogEntity that = (BlogEntity) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }
}
