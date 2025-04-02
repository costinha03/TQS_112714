package tqs.lab06_01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.lab06_01.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
