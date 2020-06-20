package de.rieckpil.blog;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class BaseIT {

  static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer()
    .withDatabaseName("test")
    .withUsername("duke")
    .withPassword("s3cret")
    .withReuse(true);

  @DynamicPropertySource
  static void datasourceConfig(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
  }

  @BeforeAll
  public static void beforeAll() {
    postgreSQLContainer.start();
  }
}
