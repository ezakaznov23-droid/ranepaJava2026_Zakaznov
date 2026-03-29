package ru.ranepa;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.time.LocalDate;

public class HRMApplication {
    public static void main(String[] args) {
        Employee emp = new Employee(
                "Zakaznov Egor Alekseevich",
                "Python developer",
                145_000.0,
                LocalDate.of(2026, 3, 1)
        );

        //sout
        System.out.println(emp.getSalary());

        EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl();
        System.out.println("=======");
        System.out.println(employeeRepository.save(emp));
        System.out.println("=======");
        var emp1 = employeeRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        System.out.println("Employee was found: " + emp1);
    }
}
