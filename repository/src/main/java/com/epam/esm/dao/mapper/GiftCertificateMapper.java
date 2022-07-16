package com.epam.esm.dao.mapper;

import com.epam.esm.dao.constants.FieldName;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    public GiftCertificateMapper() {
    }

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(resultSet.getInt(FieldName.ID));
        giftCertificate.setName(resultSet.getString(FieldName.NAME));
        giftCertificate.setDescription(resultSet.getString(FieldName.DESCRIPTION));
        giftCertificate.setPrice(resultSet.getDouble(FieldName.PRICE));
        giftCertificate.setDuretion(resultSet.getInt(FieldName.DURATION));
        giftCertificate.setCreateDate(resultSet.getTimestamp(FieldName.CREATE_DATE)
                .toLocalDateTime());
        giftCertificate.setLastUpdateDate(resultSet.getTimestamp(FieldName.LAST_UPDATE_DATE)
                .toLocalDateTime());
        return giftCertificate;
    }
}