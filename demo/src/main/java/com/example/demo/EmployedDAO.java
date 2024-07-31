package com.example.demo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alex_
 */



import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public class EmployedDAO {
    private static final String URL = "jdbc:oracle:thin:@192.168.56.1:1521:xe";
    private static final String USER = "SYS as SYSDBA";
    private static final String PASSWORD = "123";
    
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    
    public void transferSalary(int fromEmployedId, int toEmployedId, double amount) {
    String Transfer = "UPDATE SET salary = CASE WHEN id = ? THEN salary - ? " +
                     "WHEN id = ? THEN salary + ? END WHERE id IN (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement row = conn.prepareStatement(Transfer)) {

            row.setInt(1, fromEmployedId);
            row.setDouble(2, amount);
            row.setInt(3, toEmployedId);
            row.setDouble(4, amount);
            row.setInt(5, fromEmployedId);
            row.setInt(6, toEmployedId);

            if (row.executeUpdate() > 0) {
                System.out.println("Transferencia de salario realizada exitosamente.");
            } else {
                System.out.println("Error en la transferencia de salario.");
            }

        } catch (SQLException e) {
            System.out.println("Error al realizar la transferencia de salario: " + e.getMessage());
        }
    }
    public List<Employed> getAboveAverageSalaryEmployees() {
       // Hay que crear la clase Employee con su contructor con los campos de la BD
    List<Employed> ListEmployeds = new ArrayList<>();
    String Promedio = "SELECT id, Name, Position, Salary FROM Employed WHERE Salary > (SELECT AVG(salary) FROM employed)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(Promedio);
                
             ResultSet registro = pstmt.executeQuery()) {
            
            while (registro.next()) {
                int id = registro.getInt("id");
                String name = registro.getString("name");
                String position = registro.getString("position");
                float salary = registro.getFloat("salary");
                ListEmployeds.add(new Employed(id, name, position, salary));
            }
        } catch (SQLException e) {
            System.out.println("Error al recuperar empleados con salario superior al promedio: " + e.getMessage());
        }
        return ListEmployeds;
    }


    
    
    public void createEmployed(int id, String name, String position, double salary) {
        // Implementar código aquí
        String Insert = "INSERT INTO employeed (ID, Name, Position, Salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement rows = conn.prepareStatement(Insert)) {

            rows.setInt(1, id);
            rows.setString(2, name);
            rows.setString(3, position);
            rows.setDouble(4, salary);

            rows.executeUpdate();
            System.out.println("<< Empleado insertado con éxito. >>");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Employed getEmployee(int ID) {
        String Query = "SELECT * FROM employees WHERE id = ?";
        Employed employee = null;

        try (Connection conn = getConnection();
             PreparedStatement Campos = conn.prepareStatement(Query)) {
            
            Campos.setInt(1, ID);
            ResultSet row = Campos.executeQuery();

            if (row.next()) {
                String Name = row.getString("Name");
                String Position = row.getString("Position");
                float Salary = row.getFloat("Salary");
                employee = new Employed(ID, Name, Position, Salary);
            }

        } catch (SQLException e) {
            System.out.println("Error al recuperar el empleado: " + e.getMessage());
        }
        return employee;
    }
    
    public void updateEmployee(int id, String name, String position, double salary) {
        // Implementar código aquí
        String UPDATE = "UPDATE employed SET Name = ?, Position = ?, Salary = ? WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement row = conn.prepareStatement(UPDATE)) {

            row.setString(1, name);
            row.setString(2, position);
            row.setDouble(3, salary);
            row.setInt(4, id);
            
            if (row.executeUpdate() > 0) {
                System.out.println("Empleado actualizado exitosamente.");
            } else {
                System.out.println("No se encontró el empleado con el ID proporcionado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el empleado: " + e.getMessage());
        }
    }

    
    public void deleteEmployee(int id) {
        // Implementar código aquí
                String Delete = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement Row = conn.prepareStatement(Delete)) {

            Row.setInt(1, id);

            if (Row.executeUpdate() > 0) {
                System.out.println("Empleado eliminado exitosamente.");
            } else {
                System.out.println("No se encontró el empleado con el ID proporcionado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar el empleado: " + e.getMessage());
        }
    }
}