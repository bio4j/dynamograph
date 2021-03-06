## Project usage

This section explains how you can use Dynamograph with your own data and AWS infrastructure.

### Prerequisites

Before you will be able to use Dynamograph you must meet some requirements:

- have an AWS account with DynamoDB enabled
- have rights for creating tables as well as writing data to them (initialization db)
- sbt installed on you machine
- installed git and other basic tools
- if you want to use the distributed writing component, you should have permissions for s3, sqs, and ec2

When you successfully meet all mentioned requirement you can proceed to further steps.

### Database initialization

Database initialization process consist of two phases: _table creation_ and _data uploading_.

##### Tables creation

If you want to create tables for specific datasets you should take a look into `TableModelInitializer` - there you can find code which for given datasets creates required set of tables.
Code for this could look like this:

``` scala
object Dynamograph {
  def main(args: Array[String]) {
    TableModelInitializer.initialize()
  }
}
```

On the other hand method: `TableModelInitializer.clear()` will remove tables fron DynamoDB.

##### Parsing

Before you will able to save data to DynamoDb you should prepare data - parse to the representation acceptable by service responsible for writing. 
What is extremely important that parsing large files should read chunks and pass them further to the processing. 
Currently you can parse file by next commands:
```scala
val elements = new PullGoParser(Source.fromFile("test_file.owl))
```
As a result you will receive `Iterables` on which you can apply function `(SingleElement) => U // U is type parameter`



##### Data uploading

Firstly you should identify the dataset on which you want to work and find proper parser for it (parser is part of the project). For each of the parser there also should be mapper which translates parser output to the intermediate form.
Received intermediate form you could pass to the service which you can get from Service Provider.
Part of the code that will upload data could be similar to listing below:

``` scala
val parser = new PullGoParser(Source.fromFile(args(0)))
    for(element <- parser){
      ServiceProvider.dynamoDbExecutor.execute(ServiceProvider.mapper.map(element))
    }
```

### Using Dynamograph for reading

In order to use Dynamograph you must specify next environment variables with proper values for you aws account

``` bash
AWS_ACCESS_KEY 
AWS_SECRET_KEY
```

Next step involves running Dynamograph operation like reading edges, vertices i.e.

Let's imagine that you have GoTerm and you want to find all GoTerm that are part of given term. All you need to do is just to execute next method

``` scala
val vertex: GoTerm // vertex that you have
val partOfTerms = vertex getOutV(PartOf) // there you will have GoTerms that are part of vertex GoTerm
```

On the other hand if you wish to get simple property value you must invoke next functions:

``` scala
val vertex: GoTerm // vertex that you have
val attributeValue = vertex get identify // there you will get value of the id attribute 
```

As you can see usage of Dynamograph when you have vertices or edges is extremely easy, but what in case that you want to get some vertex but everything you know about vertex is just `id`.
In such case Dynamograph offer reader for each of the vertex type. All you need to do is just to invoke read method for appropriate reader. Code below illustrates reading GOTerm by id:

``` scala
val result =  GoTerm ->> goTermVertexReader.read(id_of_the_vertex) 
// result of this invocation returns vertex with given id and type - in case of success otherwise exception
```

Similar functions you can find for searching edges.





