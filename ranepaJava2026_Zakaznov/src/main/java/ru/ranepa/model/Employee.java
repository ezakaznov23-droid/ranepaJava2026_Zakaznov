package ru.ranepa.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class Employee {

    private static final BigDecimal MIN_SALARY = BigDecimal.ZERO;

    private Long id;
    private final String name;
    private final String position;
    private BigDecimal salary;
    private final LocalDate hireDate;

    public Employee(String name, String position, double salary, LocalDate hireDate) {
        validateName(name);
        validatePosition(position);
        validateSalary(salary);

        this.name = name;
        this.position = position;
        this.salary = BigDecimal.valueOf(salary);
        this.hireDate = hireDate;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    private void validatePosition(String position) {
        if (position == null || position.isBlank()) {
            throw new IllegalArgumentException("Position cannot be empty");
        }
    }

    private void validateSalary(double salary) {
        if (BigDecimal.valueOf(salary).compareTo(MIN_SALARY) < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        if (salary.compareTo(MIN_SALARY) < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }
}