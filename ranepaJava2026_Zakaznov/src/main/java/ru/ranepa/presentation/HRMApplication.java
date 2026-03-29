package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public final class HRMApplication {

    private static final String PROMPT_CHOOSE_OPTION = "Choose an option: ";
    private static final String PROMPT_ENTER_NAME = "Enter name: ";
    private static final String PROMPT_ENTER_POSITION = "Enter position: ";
    private static final String PROMPT_ENTER_SALARY = "Enter salary: ";
    private static final String PROMPT_ENTER_DATE = "Enter hire date (YYYY-MM-DD): ";
    private static final String PROMPT_ENTER_ID = "Enter ID: ";

    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepositoryImpl();
        EmployeeService service = new EmployeeService(repository);
        Scanner scanner = new Scanner(System.in);

        boolean work = true;
        while (work) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> showAllEmployees(repository);
                    case "2" -> addEmployee(repository, scanner);
                    case "3" -> deleteEmployee(repository, scanner);
                    case "4" -> findEmployeeById(repository, scanner);
                    case "5" -> showStatistics(service);
                    case "6" -> {
                        System.out.println("Exit");
                        work = false;
                    }
                    default -> System.out.println("Wrong option, try again");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD");
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== HRM System Menu ===");
        System.out.println("1. Show all employees");
        System.out.println("2. Add employee");
        System.out.println("3. Delete employee by ID");
        System.out.println("4. Find employee by ID");
        System.out.println("5. Show statistics");
        System.out.println("6. Exit");
        System.out.print(PROMPT_CHOOSE_OPTION);
    }

    private static void showAllEmployees(EmployeeRepository repo) {
        System.out.println("List of employees:");
        var employees = repo.findAll();
        boolean isEmpty = true;
        for (Employee emp : employees) {
            System.out.println(emp);
            isEmpty = false;
        }
        if (isEmpty) {
            System.out.println("(list is empty)");
        }
    }

    private static void addEmployee(EmployeeRepository repo, Scanner scanner) {
        System.out.print(PROMPT_ENTER_NAME);
        String name = scanner.nextLine();

        System.out.print(PROMPT_ENTER_POSITION);
        String position = scanner.nextLine();

        System.out.print(PROMPT_ENTER_SALARY);
        double salary = Double.parseDouble(scanner.nextLine());

        System.out.print(PROMPT_ENTER_DATE);
        LocalDate hireDate = LocalDate.parse(scanner.nextLine());

        Employee newEmp = new Employee(name, position, salary, hireDate);
        System.out.println(repo.save(newEmp));
    }

    private static void deleteEmployee(EmployeeRepository repo, Scanner scanner) {
        System.out.print(PROMPT_ENTER_ID);
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println(repo.delete(id));
    }

    private static void findEmployeeById(EmployeeRepository repo, Scanner scanner) {
        System.out.print(PROMPT_ENTER_ID);
        Long id = Long.parseLong(scanner.nextLine());

        repo.findById(id)
                .ifPresentOrElse(
                        emp -> System.out.println("Found: " + emp),
                        () -> System.out.println("Employee not found")
                );
    }

    private static void showStatistics(EmployeeService service) {
        System.out.println("Statistics:");
        BigDecimal avg = service.calculateAverageSalary();
        System.out.println("Average salary: " + avg);

        Employee top = service.findTopEarner();
        if (top != null) {
            System.out.println("Top earner: " + top.getName() + " - " + top.getSalary());
        } else {
            System.out.println("No employees yet");
        }
    }
}