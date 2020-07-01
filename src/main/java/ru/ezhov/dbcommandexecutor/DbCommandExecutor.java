package ru.ezhov.dbcommandexecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DbCommandExecutor {
    private final String driverClass;
    private final String url;
    private final Properties properties;

    public DbCommandExecutor(String driverClass, String url, Properties properties) {
        this.driverClass = driverClass;
        this.url = url;
        this.properties = properties;
    }

    public void execute(boolean isIterate, String[] queries) throws DbCommandExecutorException {
        String query = null;
        try {
            Class.forName(driverClass);
            try (Connection connection = DriverManager.getConnection(url, properties)) {
                for (String q : queries) {
                    try (Statement statement = connection.createStatement()) {
                        query = q;
                        long begin = System.currentTimeMillis();
                        final boolean execute = statement.execute(query);
                        if (isIterate && execute) {
                            try (final ResultSet resultSet = statement.getResultSet()) {
                                while (resultSet.next()) {
                                    resultSet.getObject(1);
                                }
                            }
                        }
                        long end = System.currentTimeMillis();
                        System.out.printf("time\t%s ms\tquery\t%s\n", (end - begin), query);
                    }
                }
            }
        } catch (Exception e) {
            throw new DbCommandExecutorException("Exception : " + query, e);
        }
    }
}
