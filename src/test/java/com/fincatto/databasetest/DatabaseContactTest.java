package com.fincatto.databasetest;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseContactTest extends DatabaseTest {
    
    @Test
    public void testContact() throws SQLException {
        try (Connection conn = getConnection()) {
            try (Statement statement = conn.createStatement()) {
                statement.execute("INSERT INTO public.contact (name) VALUES ('Contact 1');");
                try (ResultSet resultSet = statement.executeQuery("SELECT * FROM public.contact")) {
                    Assert.assertTrue(resultSet.next());
                    Assert.assertEquals(1, resultSet.getInt("id"));
                    Assert.assertEquals("Contact 1", resultSet.getString("name"));
                }
            }
        }
    }
}
