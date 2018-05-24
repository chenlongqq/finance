package com.waben.stock.datalayer.institution.entity;

import javax.persistence.*;

/**
 * @author Created by yuyidi on 2018/3/9.
 * @desc 机构代理用户
 */
@Entity
@Table(name = "agency")
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String password;
    @ManyToOne
    @JoinColumn(name = "creater")
    private Agency creater;
    @ManyToOne
    @JoinColumn
    private Institution institution;
    @Column
    private Long role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Agency getCreater() {
        return creater;
    }

    public void setCreater(Agency creater) {
        this.creater = creater;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }
}

