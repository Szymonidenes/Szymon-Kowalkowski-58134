package org.example.service;

import org.example.config.DbConnection;
import org.example.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagerImpl implements EmployeeManager {
    PreparedStatement stmt;
    Connection conn;

    @Override
    public int createEmployee(Employee employee) {
        conn = DbConnection.createDbConnection();
        String sqlQuery = "INSERT INTO employee(firstname, lastname, street, zipcode, city, photo) VALUE(?,?,?,?,?,?)";

        try {
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, employee.getFirstname());
            stmt.setString(2, employee.getLastname());
            stmt.setString(3, employee.getStreet());
            stmt.setString(4, employee.getZipcode());
            stmt.setString(5, employee.getCity());
            stmt.setBytes(6, employee.getPhoto());
            int updateResult = stmt.executeUpdate();

            if (updateResult != 0)
                System.out.println("Employee Inserted Successfully !!!");

            return updateResult;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources();
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        conn = DbConnection.createDbConnection();
        String sqlQuery = "SELECT * FROM employee";

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sqlQuery);

            while (result.next()) {
                int id = result.getInt("id");
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                String street = result.getString("street");
                String zipcode = result.getString("zipcode");
                String city = result.getString("city");

                byte[] photo = result.getBytes("photo");

                Employee employee = new Employee(id, firstname, lastname, street, zipcode, city, photo);
                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return employees;
    }

    @Override
    public Employee getEmployeeById(int id) {
        Employee employee = null;
        conn = DbConnection.createDbConnection();
        String sqlQuery = "SELECT * FROM employee WHERE id = ?";

        try {
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                String street = result.getString("street");
                String zipcode = result.getString("zipcode");
                String city = result.getString("city");

                employee = new Employee(id, firstname, lastname, street, zipcode, city);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return employee;
    }

    @Override
    public void updateEmployee(Employee employee) {
        conn = DbConnection.createDbConnection();
        String sqlQuery = "UPDATE employee SET firstname = ?, lastname = ?, street = ?, zipcode = ?, city = ? WHERE id = ?";

        try {
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, employee.getFirstname());
            stmt.setString(2, employee.getLastname());
            stmt.setString(3, employee.getStreet());
            stmt.setString(4, employee.getZipcode());
            stmt.setString(5, employee.getCity());
            stmt.setInt(6, employee.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    @Override
    public void deleteEmployee(int id) {
        conn = DbConnection.createDbConnection();
        String sqlQuery = "DELETE FROM employee WHERE id = ?";

        try {
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    public void closeResources() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}