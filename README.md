# HTTP job processing service

A demo project which implements a task processor. It uses Spring Boot and Java 8.

It is a practical demonstration of a topological sort of a Directed Acyclic Graph(DAG).

The input is a list of tasks:

```
{
  "tasks": [
    {
      "name": "t1",
      "command": "cmd1,
      "requires": []
    },
    {
      "name": "t2",
      "command": "cmd2",
      "requires": [
        "t3"
      ]
    },
    {
      "name": "t3",
      "command": "cmd3",
      "requires": [
        "t1"
      ]
    }
  ]
}
```

The API consists of two end-points:

- **`POST /tasks/sort`** - sorts the tasks so that the tasks on which they depend are executed first and returns the sorted list
- **`POST /tasks/build`** - builds a bash script which could be pipelined for execution

The project uses [Swagger2](https://swagger.io/) to document the end-points.

Assuming that the application runs on the default location `http://localhost:8080/` you will be able to see the api description as json on the following url:

```
http://localhost:8080/v2/api-docs
```

and by using the Swagger UI you will see the above output in a more readable form and will also have the possibility to run some tests against the api:
```
http://localhost:8080/swagger-ui.html
```

There are [JUnit](https://junit.org/junit5/) tests for each of the endpoints including tests for incorrect input.

Sample curl requests:

``` curl -s -H "Content-Type: application/json" -d @test.json http://localhost:8080/tasks/sort ```

``` curl -s -H "Content-Type: application/json" -d @test.json http://localhost:8080/tasks/build | bash ```
