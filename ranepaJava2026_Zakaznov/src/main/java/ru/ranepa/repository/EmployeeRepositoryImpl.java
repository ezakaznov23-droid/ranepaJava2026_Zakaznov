package ru.ranepa.repository;

import ru.ranepa.model.Employee;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class EmployeeRepositoryImpl implements EmployeeRepository {

    private final Map<Long, Employee> employees = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public String save(Employee employee) {
        employee.setId(nextId);
        employees.put(nextId, employee);
        return "Employee " + nextId++ + " successfully saved";
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public Iterable<Employee> findAll() {
        return employees.values();
    }

    @Override
    public String delete(Long id) {
        return employees.remove(id) != null
                ? "Employee " + id + " deleted"
                : "Employee not found";
    }
}