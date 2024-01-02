package org.royllo.explorer.core.test.integration.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Testcontainers
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:16:///explorer",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"
})
@DirtiesContext
@DisplayName("PostgreSQL test")
public class PostgreSQLTest {

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("Liquibase execution test")
    public void liquibaseExecutionTest() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            final ResultSet results = connection.createStatement()
                    .executeQuery("""
                             SELECT  count(*) as USER_COUNT
                             FROM    APPLICATION_USER
                             WHERE   USERNAME = 'anonymous'
                            """);
            results.next();
            Assertions.assertEquals(1, results.getInt("USER_COUNT"));
        }
    }

    @Test
    @DisplayName("LTree installed")
    public void lTreeInstalled() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            final ResultSet results = connection.createStatement()
                    .executeQuery("""
                             SELECT  count(*) as LTREE_EXTENSION_COUNT
                             FROM    pg_extension
                             where   extname = 'ltree'
                            """);
            results.next();
            Assertions.assertEquals(1, results.getInt("LTREE_EXTENSION_COUNT"));
        }
    }

}
