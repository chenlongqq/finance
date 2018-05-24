package com.waben.stock.datalayer.institution.entity.converter;

import com.waben.stock.interfaces.enums.CapitalSource;

import javax.persistence.AttributeConverter;

/**
 * @author Created by yuyidi on 2018/3/9.
 * @desc
 */
public class CapitalSourceConvert implements AttributeConverter<CapitalSource,String> {

    @Override
    public String convertToDatabaseColumn(CapitalSource attribute) {
        return attribute.getIndex();
    }

    @Override
    public CapitalSource convertToEntityAttribute(String dbData) {
        return CapitalSource.getByType(dbData);
    }
}
