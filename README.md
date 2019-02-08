[![Maven](https://img.shields.io/maven-central/v/io.github.jdlopez/sqlcrud.svg)](https://mvnrepository.com/artifact/io.github.jdlopez/sqlcrud)

# SQL CRUD

Very simple CRUD application. Servlet based, jar packaged. Could be embedded in larger applications

* C Create
* R Read
* U Update
* D Delete

[CRUD wikipedia](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete)

_Features:_

* Pre-build bootstrap-style application (very simple but fully functional)
* All CRUD operations available + listing. Select table then show table content listings. Edit or create rows
* Custom Query window. Could be disabeled
* Reports window. Configurable: sqls in database table or properties. Could be disableled

## Basic usage

Add maven dependency:

    <dependency>
        <groupId>io.github.jdlopez</groupId>
        <artifactId>sqlcrud</artifactId>
        <version>${see-last-version-in-badge}</version>
    </dependency>

Add config: web.xml (actions) and cruddatabase.json

## Config

web.xml o java equivalent

* table filter or table list or prefix. All tables by default
* dataSource
* Access role (user in role?)

Example web.xml:

[web.xml in example](example/src/webapp/WEB-INF/web.xml)

Example json:

[cruddatabase.json in example](example/src/webapp/WEB-INF/cruddatabase.json)

## Building

If you want to build your own jar library:

In base project:

    mvn install

To create sample database:

    mvn sql:execute
    
In example project to test:

    mvn jetty:run
