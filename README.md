# db-command-executor

## Used Java 7

## Supported properties

|property                             |type     |description  |
|-------------------------------------|---------|-------------|
|db.command.executor.driverClass      | String  |driver classname
|db.command.executor.url              | String  |connection url
|db.command.executor.prop             | String  |properties that will be directly passed to DriverManager.getConnection(url, **properties**)), without **db.command.executor.prop**, example: ```db.command.executor.prop.user``` - will be passed **user** 
|db.command.executor.queries.delimiter| String  |query delimiter, default ```___```
|db.command.executor.queries          | String  |query list for execution
|db.command.executor.isIterate        | boolean |do iterate over ```ResultSet``` if query return ```ResultSet```

## Build and run example

1. Pull and run Docker
    [https://hub.docker.com/_/mysql](https://hub.docker.com/_/mysql)

    ```shell script
    docker run --name some-mysql --rm -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_DATABASE=test -e MYSQL_USER=admin -e MYSQL_PASSWORD=admin -p 3306:3306 -d mysql:latest
    ```

1. Build project

    ```shell script
    mvn clean package
    ```

1. Download MySql driver

    ```shell script
    wget -O target/mysql-connector-java-8.0.19.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.19/mysql-connector-java-8.0.19.jar 
    ```
1. Application run
    
    [https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html)

    - Create table
    
    ```shell script
    java -Ddb.command.executor.driverClass=com.mysql.cj.jdbc.Driver -Ddb.command.executor.url=jdbc:mysql://localhost:3306/test -Ddb.command.executor.queries="create table test (id int)" -Ddb.command.executor.prop.user=admin -Ddb.command.executor.prop.password=admin -cp .:target/db-command-executor-0.1.jar:target/mysql-connector-java-8.0.19.jar ru.ezhov.dbcommandexecutor.Application
    ```

    - Fill table
   
    ```shell script
    java -Ddb.command.executor.driverClass=com.mysql.cj.jdbc.Driver -Ddb.command.executor.url=jdbc:mysql://localhost:3306/test -Ddb.command.executor.queries="insert into test(id) values (1)___select * from test" -Ddb.command.executor.prop.user=admin -Ddb.command.executor.prop.password=admin -cp .:target/db-command-executor-0.1.jar:target/mysql-connector-java-8.0.19.jar ru.ezhov.dbcommandexecutor.Application
    ```
