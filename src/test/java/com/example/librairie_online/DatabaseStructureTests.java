package com.example.librairie_online;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootTest
class DatabaseStructureTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void testDatabaseStructure() throws SQLException {
        DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();

        // Obtenir les tables
        ResultSet tables = metaData.getTables(null, null, "%", new String[] { "TABLE" });
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            System.out.println("Table: " + tableName);

            // Obtenir les colonnes pour chaque table
            ResultSet columns = metaData.getColumns(null, null, tableName, "%");
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME");
                int columnSize = columns.getInt("COLUMN_SIZE");
                System.out.println("    Column: " + columnName + " - " + columnType + "(" + columnSize + ")");
            }
        }
    }
}
