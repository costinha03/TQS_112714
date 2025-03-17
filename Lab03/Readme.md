# Lab 3: Unit Tests with Dependency Mocking - Notebook

## Learning Objectives
- Apply different test strategies for different test scopes in a multi-layer Spring Boot application
- Distinguish integration tests from slicing approaches

## Key Points
- `@SpringBootTest` loads the entire application context
- Better practice is to limit contexts to only needed Spring components
- `@DataJpaTest` loads only Spring data components (no Services, Controllers)
- `@WebMvcTest` tests web boundary layer (Controllers)
- Isolate functionality by limiting context of loaded frameworks/components

## 3.1 Employee Management Example

### Test Types in Spring Boot
| Type | Purpose | Examples |
|------|---------|----------|
| Unit Tests | Test logic in isolation | Test single class/method in isolation |
| Integration Tests | Validate interaction between layers | Test involving multiple layers |
| End-to-End | Simulate entire application flow | Tests requiring running application instance |

### Architecture Components
- `Employee`: Entity representing domain concept
- `EmployeeRepository`: Interface defining data access methods (JPA)
- `EmployeeService` and `EmployeeServiceImpl`: Service for business logic
- `EmployeeRestController`: Boundary endpoint for HTTP requests

### Test Strategies

#### A) Repository Testing
- **Strategy**: Slice test context with `@DataJpaTest`
- **Example**: `EmployeeRepositoryTest`
- **Notes**: 
  - Uses `TestEntityManager` to access database directly
  - Includes `@AutoConfigureTestDatabase`
  - Uses H2 in-memory database

#### B) Service Testing
- **Strategy**: Unit tests with mocked repository
- **Example**: `EmployeeService_UnitTest`
- **Notes**:
  - Uses JUnit + Mockito (no Spring context)
  - Much faster than full `@SpringBootTest`
  - No database involved

#### C) Controller Testing (With Mock Service)
- **Strategy**: `@WebMvcTest` for simplified web environment
- **Example**: `EmployeeController_WithMockServiceTest`
- **Notes**:
  - Uses `MockMvc` for server-side testing
  - Dependencies mocked with `@MockBean`
  - Focuses on boundary layer in isolation

#### D) Integration Testing (Full Context with MockMvc)
- **Strategy**: Full Spring Boot application with `@SpringBootTest`
- **Example**: `EmployeeRestControllerIT`
- **Notes**:
  - Web environment enabled
  - Uses `MockMvc` for server-side testing
  - Tests multiple components (REST, service, repository, DB)

#### E) Integration Testing (Full Context with REST Client)
- **Strategy**: Full Spring Boot application with REST client
- **Example**: `EmployeeRestControllerTemplateIT`
- **Notes**:
  - Uses `TestRestTemplate` as REST client
  - Tests request/response marshaling

### Review Questions

a) **AssertJ Examples**:
- `assertThat(foundEmployee.getName()).isEqualTo("john")`
- `assertThat(foundEmployees).hasSize(2).extracting(Employee::getName).containsOnly("bob", "alex")`

b) **Transitive Annotations in @DataJpaTest**:
- `@Transactional`
- `@AutoConfigureTestDatabase`
- `@TestConstructor(autowireMode=ALL)`

c) **Repository Mocking Example**:
- In `EmployeeService_UnitTest` using `@Mock private EmployeeRepository employeeRepository`

d) **Difference @Mock vs @MockBean**:
- `@Mock`: Standard Mockito annotation, creates mock instances
- `@MockBean`: Spring Boot annotation, creates mock AND adds to Spring context

e) **Role of application-integrationtest.properties**:
- Contains configuration for integration tests
- Used when `@ActiveProfiles("integrationtest")` is specified
- Allows different configuration for testing (DB settings, etc.)

f) **API Test Strategy Differences**:
- Strategy C: Only tests controller layer with mocked services
- Strategy D: Tests full application stack using MockMvc
- Strategy E: Tests full application stack using an actual HTTP client

## 3.2 Cars Service (Test Slicing and TDD)

### TDD Steps Completed
a) Created controller test with mocked service
   - Used `@WebMvcTest` for testing
   - Mocked `CarService` with `@MockBean`

b) Created service test with mocked repository
   - Used JUnit and Mockito
   - Mocked `CarRepository`

c) Created repository test
   - Used `@DataJpaTest`
   - Used H2 in-memory database

d) Implemented business logic for replacement car matching
   - Created logic to find car in same segment and motor type
   - Tested at service layer with appropriate mocks

## 3.3 Integration Test

### Integration Test Implementation
a) Created end-to-end test from API to repository
   - Used `@SpringBootTest` with TestRestTemplate
   - Verified complete flow through system

b) Adapted integration test to use real database
   - Set up MySQL instance (Docker container)
   - Added MySQL dependency, removed H2
   - Created test-specific properties file
   - Used `@TestPropertySource`
   - Disabled `@AutoConfigureTestDatabase`

c) **Real Database Testing Analysis**:
   - **Advantages**:
     - Tests against actual production database
     - Validates SQL queries against real DB engine
     - Tests database configuration
     - Tests performance characteristics
   - **Disadvantages**:
     - Slower tests
     - External dependency required
     - Need to manage test data
     - Potential test stability issues
     - More complex setup