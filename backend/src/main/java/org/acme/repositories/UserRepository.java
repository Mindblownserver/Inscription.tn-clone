package org.acme.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.acme.entities.Role;
import org.acme.entities.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserRepository {
    @Inject
    DataSource dataSource;
    
    public User getUserByUsername(String username)throws SQLException{
        User usr = new User();
        String sql = "select USERNAME, PASSWORD_HASH, ROLE_ID from USERS where USERNAME =?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1, username);
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        usr.setUsername(rs.getString("username"));
                        usr.setPasswordHash(rs.getString("password_hash"));
                        Role role = getRoleById(rs.getInt("role_Id"));
                        usr.setRole(role);
                    }
                    return usr;
                }
        }
    }

    private Role getRoleById(int roleId)throws SQLException {
        Role role = new Role();
        String sql = "select id, role_name from roles where id =?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1, roleId);
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        role.setId(roleId);
                        role.setRoleName(rs.getString("role_name"));
                    }
                    return role;
                }
        }
    }

    public void save(User user) {

        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            // Set parameters
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
    
            // Execute the insert operation
            int rowsInserted = statement.executeUpdate();
    
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
