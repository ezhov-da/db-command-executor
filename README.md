# db-command-executor

## Версия Java: 7

## Поддерживаемые свойства

|свойство                             |тип|описание  |
|-------------------------------------|---|----------|
|db.command.executor.driverClass      | String  |название класса драйвера
|db.command.executor.url              | String  |url подключения
|db.command.executor.prop             | String  |свойства, которые будут напрямую переданы в DriverManager.getConnection(url, **properties**)), без **db.command.executor.prop**, например: ```db.command.executor.prop.user``` - будет передано **user** 
|db.command.executor.queries.delimiter| String  |разделитель для запросов, по умолчанию ```___```
|db.command.executor.queries          | String  |список запросов для выполнения
|db.command.executor.isIterate        | boolean |делать ли итерацию по ```ResultSet``` в случае запроса, возвращающего ```ResultSet```

## Пример сборки и запуска

1. Скачивание и запуск докера
    [https://hub.docker.com/_/mysql](https://hub.docker.com/_/mysql)

    ```shell script
    docker run --name some-mysql --rm -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_DATABASE=test -e MYSQL_USER=admin -e MYSQL_PASSWORD=admin -p 3306:3306 -d mysql:latest
    ```

1. Сборка проекта

    ```shell script
    mvn clean package
    ```

1. Скачивание драйвера MySql

    ```shell script
    wget -O target/mysql-connector-java-8.0.19.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.19/mysql-connector-java-8.0.19.jar 
    ```
1. Запуск приложения
    
    [https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html)

    - Создание таблицы
    
    ```shell script
    java -Ddb.command.executor.driverClass=com.mysql.cj.jdbc.Driver -Ddb.command.executor.url=jdbc:mysql://localhost:3306/test -Ddb.command.executor.queries="create table test (id int)" -Ddb.command.executor.prop.user=admin -Ddb.command.executor.prop.password=admin -cp .:target/db-command-executor-0.1.jar:target/mysql-connector-java-8.0.19.jar ru.ezhov.dbcommandexecutor.Application
    ```

    - Наполнение таблицы
   
    ```shell script
    java -Ddb.command.executor.driverClass=com.mysql.cj.jdbc.Driver -Ddb.command.executor.url=jdbc:mysql://localhost:3306/test -Ddb.command.executor.queries="insert into test(id) values (1)___select * from test" -Ddb.command.executor.prop.user=admin -Ddb.command.executor.prop.password=admin -cp .:target/db-command-executor-0.1.jar:target/mysql-connector-java-8.0.19.jar ru.ezhov.dbcommandexecutor.Application
    ```
