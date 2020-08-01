package net.suyudi.retail_uma.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Entity
@Where(clause = "is_delete = 0")
@Table(name = "tbl_role", schema = "uma")
public class Role implements Serializable {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "level")
    private Integer level;

    @Column(name = "menus", columnDefinition = "TEXT")
    private String menus;

    @Column(name = "created_at", updatable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+7")
    // @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+7")
    // @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "deleted_at")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+7")
    private Date deletedAt;

    @Column(name = "is_delete")
    private Integer isDelete;

}
