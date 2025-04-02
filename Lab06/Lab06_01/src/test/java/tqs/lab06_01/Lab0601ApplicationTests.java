package tqs.lab06_01;

import tqs.lab06_01.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tqs.lab06_01.repository.EmployeeRepository;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ordenação dos testes
class EmployeeRepositoryTest {

	@Container
	public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest")
			.withDatabaseName("testdb")
			.withUsername("testuser")
			.withPassword("testpass");

	@Autowired
	private EmployeeRepository employeeRepository;

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mysql::getJdbcUrl);
		registry.add("spring.datasource.username", mysql::getUsername);
		registry.add("spring.datasource.password", mysql::getPassword);
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
	}

	@Test
	@org.junit.jupiter.api.Order(1)
	void testInsertEmployee() {
		Employee employee = new Employee();
		employee.setName	("John Doe");
		employee.setDepartment("IT");

		employeeRepository.save(employee);

		Employee foundEmployee = employeeRepository.findAll().stream()
				.filter(c -> c.getDepartment().equals("IT"))
				.findFirst()
				.orElse(null);

		assertThat(foundEmployee).isNotNull();
		assertThat(foundEmployee.getName()).isEqualTo("John Doe");
	}

	@Test
	@org.junit.jupiter.api.Order(2)
	void testRetrieveEmployee() {
		Employee foundEmployee = employeeRepository.findAll().stream()
				.filter(c -> c.getDepartment().equals("IT"))
				.findFirst()
				.orElse(null);

		assertThat(foundEmployee).isNotNull();
		assertThat(foundEmployee.getDepartment()).isEqualTo("IT");
	}

	@Test
	@org.junit.jupiter.api.Order(3)
	void testUpdateEmployee() {
		Employee employeeToUpdate = employeeRepository.findAll().stream()
				.filter(c -> c.getDepartment().equals("IT"))
				.findFirst()
				.orElse(null);

		assertThat(employeeToUpdate).isNotNull();

		employeeToUpdate.setName("Jane Doe");
		employeeRepository.save(employeeToUpdate);

		Employee updatedEmployee = employeeRepository.findAll().stream()
				.filter(c -> c.getDepartment().equals("IT"))
				.findFirst()
				.orElse(null);

		assertThat(updatedEmployee).isNotNull();
		assertThat(updatedEmployee.getName()).isEqualTo("Jane Doe");
	}

	@Test
	@org.junit.jupiter.api.Order(4)
	void testDeleteEmployee() {
		Employee employeeToDelete = employeeRepository.findAll().stream()
				.filter(c -> c.getDepartment().equals("IT"))
				.findFirst()
				.orElse(null);

		assertThat(employeeToDelete).isNotNull();

		employeeRepository.delete(employeeToDelete);

		Employee deletedEmployee = employeeRepository.findAll().stream()
				.filter(c -> c.getDepartment().equals("IT"))
				.findFirst()
				.orElse(null);

		assertThat(deletedEmployee).isNull();
	}
}
