package com.waben.stock.datalayer.institution.entity.converter;

import com.waben.stock.datalayer.institution.entity.Institution;
import com.waben.stock.interfaces.enums.InstitutionType;

import javax.persistence.AttributeConverter;

/**
 * @author Created by yuyidi on 2018/3/9.
 * @desc
 */
public class InstitutionTypeConvert implements AttributeConverter<InstitutionType, String> {

    @Override
    public String convertToDatabaseColumn(InstitutionType attribute) {
        return attribute.getIndex();
    }

    @Override
    public InstitutionType convertToEntityAttribute(String dbData) {
        return InstitutionType.getByType(dbData);
    }
}
