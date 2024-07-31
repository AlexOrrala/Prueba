package com.example.demo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/**
 *
 * @author alex_
 */
@RestController
@RequestMapping("/employed")
public class EmployedController {

    @Autowired
    private EmployedDAO employedDAO;

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employed employee) {
        try {
            employedDAO.createEmployed(employee.getId(), employee.getName(), employee.getPosition(), employee.getSalary());
            return ResponseEntity.ok("Employee created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating employee: " + e.getMessage());
        }
    }

    @GetMapping("/GET/{id}")
    public ResponseEntity<Employed> getEmployee(@PathVariable int id) {
        Employed employee = employedDAO.getEmployee(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestBody Employed employee) {
        try {
            employedDAO.updateEmployee(id, employee.getName(), employee.getPosition(), employee.getSalary());
            return ResponseEntity.ok("Employee updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating employee: " + e.getMessage());
        }
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        try {
            employedDAO.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting employee: " + e.getMessage());
        }
    }

    @GetMapping("/above-average-salary")
    public ResponseEntity<List<Employed>> getAboveAverageSalaryEmployees() {
        try {
            List<Employed> employees = employedDAO.getAboveAverageSalaryEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/transfer-salary")
    public ResponseEntity<String> transferSalary(@RequestParam int fromEmployedId, @RequestParam int toEmployedId, @RequestParam double amount) {
        try {
            employedDAO.transferSalary(fromEmployedId, toEmployedId, amount);
            return ResponseEntity.ok("Salary transferred successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error transferring salary: " + e.getMessage());
        }
    }
}
    
