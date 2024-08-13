package org.example.repository;


import org.example.entity.Audit;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AuditRepository implements CRUDRepository<Audit, Integer> {

    @Override
    public Audit save(Audit audit) {
        String insertSQL = "INSERT INTO car_shop.audit (action, timestamp, user_id) VALUES (?, ?, ?) RETURNING id";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {

            statement.setString(1, audit.getAction());
            statement.setTimestamp(2, Timestamp.valueOf(audit.getTimestamp()));
            statement.setInt(3, audit.getUserID());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                audit.setId(resultSet.getInt("id"));
                return audit;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save audit log");
        }
        return null;
    }

    @Override
    public List<Audit> findAll() {
        List<Audit> audits = new ArrayList<>();
        String findAllSQL = "SELECT * FROM car_shop.audit";

        try (Connection connection = DataBaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(findAllSQL);
            audits = mapResultSetToAudits(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audits;
    }

    @Override
    public Optional<Audit> findById(Integer id) {
        String getByIdSQL = "SELECT * FROM car_shop.audit WHERE id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(getByIdSQL)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToAudit(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Audit update(Audit audit) {
        String updateSQL = "UPDATE car_shop.audit SET action = ?, timestamp = ?, user_id = ? WHERE id = ? RETURNING id";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSQL)) {

            statement.setString(1, audit.getAction());
            statement.setTimestamp(2, Timestamp.valueOf(audit.getTimestamp()));
            statement.setInt(3, audit.getUserID());
            statement.setInt(4, audit.getId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return audit;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        String deleteSQL = "DELETE FROM car_shop.audit WHERE id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Audit> getAuditByAction(String action) {
        List<Audit> audits = new ArrayList<>();
        String findByActionSQL = "SELECT * FROM car_shop.audit WHERE action = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(findByActionSQL)) {

            statement.setString(1, action);
            ResultSet resultSet = statement.executeQuery();
            audits = mapResultSetToAudits(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audits;
    }

    public List<Audit> getAuditByDate(LocalDateTime date) {
        List<Audit> audits = new ArrayList<>();
        String findByDateSQL = "SELECT * FROM car_shop.audit WHERE DATE(timestamp) = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(findByDateSQL)) {

            statement.setDate(1, Date.valueOf(date.toLocalDate()));
            ResultSet resultSet = statement.executeQuery();
            audits = mapResultSetToAudits(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audits;
    }

    private List<Audit> mapResultSetToAudits(ResultSet resultSet) throws SQLException {
        List<Audit> audits = new ArrayList<>();
        while (resultSet.next()) {
            audits.add(mapResultSetToAudit(resultSet));
        }
        return audits;
    }

    private Audit mapResultSetToAudit(ResultSet resultSet) throws SQLException {
        Audit audit = new Audit();
        audit.setId(resultSet.getInt("id"));
        audit.setAction(resultSet.getString("action"));
        audit.setTimestamp(resultSet.getTimestamp("timestamp").toLocalDateTime());
        audit.setUserID(resultSet.getInt("user_id"));
        return audit;
    }
}
