# SQL CRUD

Very simple CRUD application. Servlet based, jar packaged. Could be embedded in larger applications

* C Create
* R Read
* U Update
* D Delete

[CRUD wikipedia](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete)

## Config

web.xml o java equivalent

* table filter or table list or prefix. All tables by default
* dataSource
* Access role (user in role?)

Example web.xml:

[web.xml in example](example/src/webapp/WEB-INF/web.xml)

Example json:

[cruddatabase.json in example](example/src/webapp/WEB-INF/cruddatabase.json)
    
## Arch

servlet + html5/js

Services, REST. All refered to 'base URI':

- [x] GET /base/ displays html frontend
- [x] GET /base/def/all get available tables
- [x] GET /base/def/table get json with table definition

- [x] GET /base/l/table list all data, using body json as filter
- [x] POST /base/c/table with json data
- [x] GET  /base/r/table/id
- [x] POST /base/u/table with json data
- [x] DELETE /base/d/table/id

- [x] add some cache for definitions (DbService.getInstance)

### Json parser

1st trying JSR 374: [jee jsonp](https://javaee.github.io/jsonp/) -> no read javabean or write

Actually it uses Gson. Is lighter than Jackson

## frontend

bootstrap+jquery+datatables all in CDN

Fully customizable (replace for all templates)

## building

In base project:

    mvn install

To create sample database:

    mvn sql:execute
    
In example project:

    mvn jetty:run