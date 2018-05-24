package com.waben.stock.datalayer.institution.entity;

import com.waben.stock.datalayer.institution.entity.converter.InstitutionTypeConvert;
import com.waben.stock.interfaces.enums.InstitutionType;

import javax.persistence.*;

/**
 * @author Created by yuyidi on 2018/3/9.
 * @desc 机构实体
 */
@Entity
@Table
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String code;
    @Column(name = "type")
    @Convert(converter = InstitutionTypeConvert.class)
    private InstitutionType institutionType;
    @ManyToOne
    @JoinColumn(name = "superior")
    private Institution superior;

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

    public InstitutionType getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(InstitutionType institutionType) {
        this.institutionType = institutionType;
    }

    public Institution getSuperior() {
        return superior;
    }

    public void setSuperior(Institution superior) {
        this.superior = superior;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
