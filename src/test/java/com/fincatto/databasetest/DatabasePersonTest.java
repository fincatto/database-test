package com.fincatto.databasetest;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabasePersonTest extends DatabaseTest {
    
    @Test
    public void testPerson() throws SQLException {
        try (Connection conn = super.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                Assert.assertTrue(statement.execute("INSERT INTO public.person (name) VALUES ('John Doe')"));
                Assert.assertTrue(statement.execute("SELECT * FROM public.person"));
                try (ResultSet resultSet = statement.getResultSet()) {
                    Assert.assertTrue(resultSet.next());
                    Assert.assertEquals(1, resultSet.getInt("id"));
                    Assert.assertEquals("John Doe", resultSet.getString("name"));
                }
            }
        }
    }
}
