package com.fincatto.databasetest;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseTest implements Loggable {
    
    private static EmbeddedPostgres postgresEmbedded;
    
    @BeforeClass
    public static void prepareDatabaseEnvironment() throws IOException {
        Loggable.getLogger(DatabaseTest.class).debug("Starting pg teste database...");
        postgresEmbedded = EmbeddedPostgres.start();
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                //statement.execute("CREATE ROLE teste WITH LOGIN SUPERUSER PASSWORD 'teste'");
                //statement.execute("CREATE DATABASE teste OWNER teste ENCODING = 'utf8'");
                statement.execute("CREATE TABLE public.person (id serial PRIMARY KEY NOT NULL, name VARCHAR NOT NULL)");
                statement.execute("CREATE TABLE public.contact (id serial PRIMARY KEY NOT NULL, name VARCHAR NOT NULL)");
            }
        } catch (Exception e) {
            Assert.fail(e.getLocalizedMessage());
        }
        Loggable.getLogger(DatabaseTest.class).debug("Done!");
    }
    
    @AfterClass
    public static void shutdownDatabaseEnvironment() throws IOException {
        Loggable.getLogger(DatabaseTest.class).debug("Stopping pg teste database...");
        if (postgresEmbedded != null) {
            postgresEmbedded.close();
        }
        Loggable.getLogger(DatabaseTest.class).debug("Done!");
    }
    
    static Connection getConnection() throws SQLException {
        //return postgresEmbedded.getPostgresDatabase().getConnection("teste", "teste");
        return postgresEmbedded.getPostgresDatabase().getConnection();
    }
}
