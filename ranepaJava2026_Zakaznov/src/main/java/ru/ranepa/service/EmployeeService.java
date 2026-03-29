package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.StreamSupport;

public final class EmployeeService {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public BigDecimal calculateAverageSalary() {
        var employees = StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                .toList();

        if (employees.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(BigDecimal.valueOf(employees.size()), SCALE, ROUNDING_MODE);
    }

    public Employee findTopEarner() {
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                .max((e1, e2) -> e1.getSalary().compareTo(e2.getSalary()))
                .orElse(null);
    }

    public Iterable<Employee> findByPosition(String position) {
        if (position == null || position.isBlank()) {
            return java.util.List.of();
        }
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                .filter(emp -> position.equalsIgnoreCase(emp.getPosition()))
                .toList();
    }
}