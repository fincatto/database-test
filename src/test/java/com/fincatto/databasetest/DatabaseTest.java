package com.fincatto.databasetest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V11;

public abstract class DatabaseTest implements Loggable {
    
    private static EmbeddedPostgres postgresEmbedded;
    private static String postgresEmbeddedUrl;
    
    @BeforeClass
    public static void prepareDatabaseEnvironment() throws IOException, SQLException {
        postgresEmbedded = new EmbeddedPostgres(V11, "/tmp/pgtest");
        postgresEmbeddedUrl = postgresEmbedded.start("localhost", 5432, "dbName", "userName", "password");
        
        //cria o banco de teste
        try (Connection conn = DriverManager.getConnection(postgresEmbeddedUrl)) {
            try (Statement statement = conn.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS public.person (id serial PRIMARY KEY NOT NULL, name VARCHAR NOT NULL);");
                // ... or you can execute SQL files...
                //postgres.getProcess().importFromFile(new File("someFile.sql"))
                // ... or even SQL files with PSQL variables in them...
                //postgres.getProcess().importFromFileWithArgs(new File("someFile.sql"), "-v", "tblName=someTable")
                // ... or even restore database from dump file
                //postgres.getProcess().restoreFromFile(new File("src/test/resources/test.binary_dump"))
            }
        }
    }
    
    @AfterClass
    public static void shutdownDatabaseEnvironment() {
        postgresEmbeddedUrl = null;
        if (postgresEmbedded != null) {
            postgresEmbedded.stop();
            postgresEmbedded.close();
        }
    }
    
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(postgresEmbeddedUrl);
    }
}
