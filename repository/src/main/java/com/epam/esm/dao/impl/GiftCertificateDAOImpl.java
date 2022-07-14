package com.epam.esm.dao.impl;

import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.constants.FieldName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    private final DateTimeFormatter dateTimeFormatter;
    private final LocalDateTime localDateTime;
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;

    public GiftCertificateDAOImpl(JdbcTemplate jdbcTemplate,
                                  GiftCertificateMapper giftCertificateMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
        dateTimeFormatter = DateTimeFormatter.ofPattern(FieldName.DATE_TIME_FORMAT);
        localDateTime = LocalDateTime.now();
    }

    @Override
    public List<Integer> findCertificatesIdByParams(String query) {
        return jdbcTemplate.queryForList(query, Integer.class);
    }

    @Override
    public GiftCertificate find(int id) {
        final String READ_CERTIFICATE_BY_ID = "SELECT * " +
                "FROM gift_certificate WHERE id = ?";
        return jdbcTemplate.query(READ_CERTIFICATE_BY_ID,
                giftCertificateMapper, id)
                .stream().findAny().orElse(null);
    }

    @Override
    public GiftCertificate find(String certificateName) {
        final String READ_CERTIFICATE_BY_NAME = "SELECT * " +
                "FROM gift_certificate WHERE name = ?";
        return jdbcTemplate.query(READ_CERTIFICATE_BY_NAME,
                giftCertificateMapper, certificateName)
                .stream().findAny().orElse(null);
    }

    @Override
    public boolean delete(int id) {
        final String DELETE_CERTIFICATE_BY_ID = "DELETE" +
                " FROM gift_certificate WHERE id = ?";
        return jdbcTemplate.update(DELETE_CERTIFICATE_BY_ID,
                id) >= 1;
    }

    @Override
    public boolean create(GiftCertificate giftCertificate) {
        final String CREATE_CERTIFICATE = "INSERT INTO gift_certificate " +
                "(id, name, description, price, duretion, create_date, last_update_date) VALUES (?,?,?,?,?,?,?)";
        String timeOfCreation = dateTimeFormatter.format(localDateTime);
        return jdbcTemplate.update(CREATE_CERTIFICATE, giftCertificate.getId(),
                giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(),
                timeOfCreation, timeOfCreation) >= 1;
    }

    @Override
    public boolean update(int id, GiftCertificate giftCertificate) {
        final String UPDATE_CERTIFICATE_BY_ID = "UPDATE" +
                " gift_certificate SET name=?, description=?, price=?, duretion=?" +
                " ,last_update_date=? WHERE id=?";
        return jdbcTemplate.update(UPDATE_CERTIFICATE_BY_ID,
                giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(),
                dateTimeFormatter.format(localDateTime), id) >= 1;
    }

    @Override
    public int findId(String certificateName) {
        final String READ_CERTIFICATE_ID_BY_NAME = "SELECT id FROM gift_certificate WHERE name = ?";
        return jdbcTemplate.queryForObject(READ_CERTIFICATE_ID_BY_NAME,
                Integer.class, certificateName);
    }
}