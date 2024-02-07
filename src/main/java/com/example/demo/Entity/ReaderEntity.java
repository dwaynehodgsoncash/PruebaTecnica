package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlTransient;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "reader", schema = "bandesal")
@NamedQueries({
        @NamedQuery(name = "Reader.findAll", query = "SELECT u FROM ReaderEntity u")
        , @NamedQuery(name = "Reader.findByReader", query = "SELECT u FROM ReaderEntity u WHERE u.name = :name")})
public class ReaderEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private int id;
    @Basic
    @Column(name = "Name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idReader")
    private List<BlogReaderEntity> blogReaderList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        ReaderEntity that = (ReaderEntity) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
