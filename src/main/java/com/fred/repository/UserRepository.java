package com.fred.repository;

import com.fred.database.ConnectionManager;
import com.fred.domain.UserDomain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public UserDomain findByEmailAndPassword(String email, String password) throws SQLException {
        try (Connection con = ConnectionManager.getConnection()) {
            String sql = "select * from user where email = ? and password = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, email);
                pst.setString(2, password);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    UserDomain user = new UserDomain();
                    this.setDomainFields(user, rs);
                    return user;
                }
                return null;
            }
        }
    }

    private void setDomainFields(UserDomain user, ResultSet rs) throws SQLException {
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
    }
}
