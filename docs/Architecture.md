## General architecture

All operations offered by Dynamograph are strongly related to data manipulation. As a result application architecture is concentrated mainly around data.

General plan of system could be described with next layers:

- user interface layer — functions offered for the user of the system
- Scala model layer — definition of the data model
- database layer — functions responsible for data manipulation, communication with database
- DynamoDB <!-- TODO DynamoDB? -->

The diagram below presents the layered architecture of the system:

![Layered architecture][architecture]

### Writing

Writing data to DynamoDB could be divided in 2 phases:

- parsing / gathering data
- uploading the data to db

What is more writing data is organized into stream. <!-- TODO what do you mean here? -->

The diagram below represents the standard workflow of saving data to DynamoDB.

![Writing workflow][writing]

##### Parsing / gathering data

This phase is responsible for parsing data located in source files and storing them in an intermediate format. At beginning files with data are parsed into singleElements which are further processed to the universal representation. This step is specific for type of dataset — in other words for each of dataset this phase should be different.


##### Uploading data to DynamoDB

Here is where we are actually writing to the database. Data stored in intermediate format is translated into DynamoDB requests which are further executed. This step is specific for database to which data will be saved.

Thanks to organizing writing into 2 phases and introducing intermediate format it is easy to extend collection of supported datasets or use another database. All things that developer must do is just implementing proper phase (parsing ow uploading) depending what he or she is trying to achieve.

### Reading

Reading could also be divided into two steps but separation is not visible at first glance. Responsibilities for mentioned layers are as follow:
- interpreting user request and translating raw data returned from database layer
- executing low level request and returning raw data from database

Diagram below represent typical reading workflow

![Reading workflow][reading]

When user invokes method for getting specific data request is passed to the `DynamoDBDao` which is responsible for orchestration of requests related to DB.
As a next step mentioned class pass request further to the correct reader which translates high level query to the DynamoDB interface. Last step in request part involve communicating with DynamoDB.
Response from database is returned synchronously to the reader which returned raw data to the model object by DynamoDB. Specific model instance interpret raw data and returns instance of the object with proper type.



[writing]: img/write.png
[reading]: img/read.png
[architecture]: img/architecture.png
