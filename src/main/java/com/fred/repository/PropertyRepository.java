package com.fred.repository;

import com.fred.database.ConnectionManager;
import com.fred.domain.PropertyDomain;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropertyRepository {

    public PropertyDomain get(int id) throws SQLException {
        try (Connection con = ConnectionManager.getConnection()) {
            String sql = "select * from property where id = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    PropertyDomain property = new PropertyDomain();
                    this.setFindFields(property, rs);
                    return property;
                }
                return null;
            }
        }
    }

    public List<PropertyDomain> findAll() throws SQLException {
        try (Connection con = ConnectionManager.getConnection()) {
            String sql = "select * from property";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                List<PropertyDomain> result = new ArrayList<>();
                while (rs.next()) {
                    PropertyDomain property = new PropertyDomain();
                    this.setFindFields(property, rs);
                    result.add(property);
                }
                return result;
            }
        }
    }

    public List<PropertyDomain> findByDescription(String description, String order) throws SQLException {
        try (Connection con = ConnectionManager.getConnection()) {

            String sql = "select * from property";
            if (description != null && !description.isEmpty()) {
                sql += " where description like ?";
            }
            sql += " order by `value`" + (order != null && order.equals("highest-value") ? "desc" : "asc");

            try (PreparedStatement pst = con.prepareStatement(sql)) {
                if (description != null && !description.isEmpty()) {
                    pst.setString(1, "%" + description + "%");
                }
                ResultSet rs = pst.executeQuery();
                List<PropertyDomain> result = new ArrayList<>();
                while (rs.next()) {
                    PropertyDomain property = new PropertyDomain();
                    this.setFindFields(property, rs);
                    result.add(property);
                }
                return result;
            }
        }
    }

    private void setFindFields(PropertyDomain property, ResultSet rs) throws SQLException {
        property.setId(rs.getInt("id"));
        property.setCode(rs.getString("code"));
        property.setTitle(rs.getString("title"));
        property.setDescription(rs.getString("description"));
        property.setImage(rs.getBytes("image"));
        property.setValue(rs.getFloat("value"));
    }

    public void save(PropertyDomain property) throws SQLException, FileNotFoundException {
        if (property.getId() != null) {
            this.update(property);
            return;
        }
        this.insert(property);
    }

    private void insert(PropertyDomain property) throws SQLException, FileNotFoundException {
        try(Connection con = ConnectionManager.getConnection()) {
            String sql = "insert into property (code, title, description, image, `value`) values (?, ?, ?, ?, ?)";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                this.setSaveFields(pst, property);
                pst.executeUpdate();
            }
        }
    }

    private void update(PropertyDomain property) throws SQLException, FileNotFoundException {
        try(Connection con = ConnectionManager.getConnection()) {
            String sql = "update property set code = ?, title = ?, description = ?, image = ?, `value` = ? where id = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                this.setSaveFields(pst, property);
                pst.setInt(6, property.getId());
                pst.executeUpdate();
            }
        }
    }

    private void setSaveFields(PreparedStatement pst, PropertyDomain property) throws FileNotFoundException, SQLException {
        pst.setString(1, property.getCode());
        pst.setString(2, property.getTitle());
        pst.setString(3, property.getDescription());
        pst.setBytes(4, property.getImage());
        pst.setFloat(5, property.getValue());
    }

    public void delete(PropertyDomain property) throws SQLException {
        try(Connection con = ConnectionManager.getConnection()) {
            String sql = "delete from property where id = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, property.getId());
                pst.executeUpdate();
            }
        }
    }
}
