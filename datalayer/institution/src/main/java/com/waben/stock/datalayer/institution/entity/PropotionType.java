package com.waben.stock.datalayer.institution.entity;

import com.waben.stock.datalayer.institution.entity.converter.CapitalSourceConvert;
import com.waben.stock.interfaces.enums.CapitalSource;

import javax.persistence.*;

/**
 * @author Created by yuyidi on 2018/3/9.
 * @desc
 */
@Entity
@Table(name = "propotion_type")
public class PropotionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column
//    @Convert(converter = CapitalSourceConvert.class)
//    private CapitalSource source;
    @Column
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
