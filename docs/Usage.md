## Project usage

This section explain how to use dynamograph.

### Prerequisites

Before you will be able to use Dynamograph you must meet some requirements:
- have DynamoDB account on aws with privileges for  reading data
- have rights for creating tables as well as writing data to them (initialization db)
- sbt installed on you machine
- installed git and other basic tools
- for some specific use cases you should have permissions for s3, sqs, ec2

When you successfully meet all mentioned requirement you can proceed to further steps.

### Database initialization

Database initialization process consist of two phases: Tables creation and Uploading data

##### Tables creation

If you want to create tables for specific datasets you should take a look into `TableModelInitializer` - there you can find code which for given datasets creates required set of tables.
Code for this could look like this:
```scala
object Dynamograph {
  def main(args: Array[String]) {
    TableModelInitializer.initialize()
  }
}
```

On the other hand method: `TableModelInitializer.clear()` will remove tables fron DynamoDB.

##### Uploading data

Firstly you should identify dataset on which you want to work and find proper parser for it (parser is part of the project). For each of the parser there also should be mapper which translates parser output to the intermediate form.
Received intermediate form you could pass to the service which you can get from Service Provider.
Part of the code that will upload data could be similar to listing below:
```scala
    val parser = new PullGoParser(Source.fromFile(args(0)))
    for(element <- parser){
      ServiceProvider.dynamoDbExecutor.execute(ServiceProvider.mapper.map(element))
    }
```

### Using Dynamograph in user programs


