## General architecture

All operations offered by Dynamograph are strongly related to the data manipulation. As a result application architecture is concentrated mainly around data.
General plan of system could be described with next layers:
- user interface layer - there are functions offered for the user of the system
- scala model layer - there is definition of data model
- database layer - this layer contains function responsible for data manipulation, communication with database
- DynamoDB

Diagram below present layered architecture of the system:
![Layered architecture][architecture]

###Writing

Writing data to DynamoDb could be divide into 2 phases:
- parsing/gathering data
- uploading to data to db
What is more writing data is organized into stream.

Diagram below represent standard workflow of saving data to db.

![Writing workflow][writing]

##### Parsing/gathering data

This phase is responsible for parsing data located in source files and storing them in an intermediate format. At beginning files with data are parsed into singleElements which are further processed to the universal representation. This step is specific for type of dataset - in other words for each of dataset this phase should be different.


##### Uploading data to DynamoDB

There happens real writes to database. Data stored in intermediate format is translated into DynamoDb requests which are further executed. This step is specific for database to which data will be saved.

Thanks to organizing writing into 2 phases and introducing intermediate format it is easy to extend collection of supported datasets or use another database. All things that developer must do is just implementing proper phase (parsing ow uploading) depending what he or she is trying to achieve.

### Reading

Reading could also be divided into two steps but separation is not visible at first glance. Responsibilities for mentioned layers are as follow:
- interpreting user request and translating raw data returned from database layer
- executing low level request and returning raw data from database

Diagram below represent typical reading workflow
![Reading workflow][reading]

Reading is the most important part of offered operation set.



[writing]: img/write.png
[reading]: img/read.png
[architecture]: img/architecture.png
