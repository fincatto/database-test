package com.fincatto.databasetest;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabasePersonTest extends DatabaseTest {
    
    @Test
    public void testPerson1() throws SQLException {
        try (Connection conn = getConnection()) {
            try (Statement statement = conn.createStatement()) {
                statement.execute("INSERT INTO public.person (name) VALUES ('John Doe');");
                try (ResultSet resultSet = statement.executeQuery("SELECT * FROM public.person")) {
                    Assert.assertTrue(resultSet.next());
                    Assert.assertEquals(1, resultSet.getInt("id"));
                    Assert.assertEquals("John Doe", resultSet.getString("name"));
                }
            }
        }
    }
    
    @Test
    public void testPerson2() throws SQLException {
        try (Connection conn = getConnection()) {
            try (Statement statement = conn.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("SELECT * FROM public.person")) {
                    Assert.assertTrue(resultSet.next());
                    Assert.assertEquals(1, resultSet.getInt("id"));
                    Assert.assertEquals("John Doe", resultSet.getString("name"));
                }
            }
        }
    }
}
