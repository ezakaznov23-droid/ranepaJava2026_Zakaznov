package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

final class EmployeeServiceTest {

    private EmployeeService service;
    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepositoryImpl();
        service = new EmployeeService(repository);
    }

    @Test
    void shouldCalculateAverageSalary() {
        repository.save(new Employee("A", "Dev", 100.0, LocalDate.now()));
        repository.save(new Employee("B", "Dev", 200.0, LocalDate.now()));
        repository.save(new Employee("C", "Dev", 300.0, LocalDate.now()));

        BigDecimal result = service.calculateAverageSalary();

        assertEquals(new BigDecimal("200.00"), result);
    }

    @Test
    void shouldFindTopEarner() {
        repository.save(new Employee("A", "Dev", 100.0, LocalDate.now()));
        repository.save(new Employee("B", "Manager", 500.0, LocalDate.now()));
        repository.save(new Employee("C", "QA", 200.0, LocalDate.now()));

        Employee top = service.findTopEarner();

        assertNotNull(top);
        assertEquals(new BigDecimal("500.0"), top.getSalary());
        assertEquals("B", top.getName());
    }

    @Test
    void shouldReturnZeroForEmptySalary() {
        BigDecimal result = service.calculateAverageSalary();
        assertEquals(BigDecimal.ZERO, result);
    }
}