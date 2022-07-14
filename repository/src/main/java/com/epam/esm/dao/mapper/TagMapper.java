package com.epam.esm.dao.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.dao.constants.FieldName;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper implements RowMapper<Tag> {

    public TagMapper() {
    }

    @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(resultSet.getInt(FieldName.ID));
        tag.setName(resultSet.getString(FieldName.NAME));
        return tag;
    }
}