package ru.ezhov.dbcommandexecutor;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        String driverClass = System.getProperty("db.command.executor.driverClass");
        String url = System.getProperty("db.command.executor.url");
        Properties properties = properties("db.command.executor.prop");
        String delimiter = System.getProperty("db.command.executor.queries.delimiter", "___");
        String[] queries = queries("db.command.executor.queries", delimiter);

        DbCommandExecutor dbCommandExecutor = new DbCommandExecutor(driverClass, url, properties);
        try {
            dbCommandExecutor.execute(queries);
        } catch (DbCommandExecutorException e) {
            LOG.log(
                    Level.SEVERE,
                    "Ошибка выполнения запроса(ов)." +
                            " driverClass: " + driverClass +
                            " url: " + url +
                            " properties: " + properties.stringPropertyNames() +
                            " queries: " + Arrays.asList(queries),
                    e
            );
        }
    }

    private static String[] queries(String property, String delimiter) {
        String queriesProperty = System.getProperty(property);
        return queriesProperty.split(delimiter);
    }

    private static Properties properties(String property) {
        Properties properties = System.getProperties();
        Set<String> propertyNames = properties.stringPropertyNames();
        Properties prop = new Properties();
        for (String name : propertyNames) {
            if (name.startsWith(property)) {
                String s = name.substring(property.length() + 1 /*точка*/);
                prop.setProperty(s, properties.getProperty(name));
            }
        }
        return prop;
    }
}
