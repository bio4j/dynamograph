## Functionalities

Text below describes functionalities offered by dynamograph.

### Basic functionalities

Functionalities offered by Dynamograph could be divided into two orthogonal groups:
- functionalities concenrated on saving data to DynamoDB
- functionalities grouped around reading data from DynamoDb

Each of the groups cover the most important functionalities from Bio4j perspective - as a result user can imagine a lot of dfferent application that will require extending offered functionality,
This topic is described in more details in [Extensibility][1] section

## Writing group

This group of functionalities concentrate on uploading data to DynamoDB. Between others methods user can find:
- parsing raw data and transalting it into intermediate form
- mapping intermediate form into upload instructions - DynamoDB instructions
- execute upload instructions

Description of typical workflow for uploading data could be found on [architecture][2] page.

Based on Dynamoraph functionalities concentrated around writing another project/task was build: [dynamograph-ddwriter][3]

## Reading group

There user finds methods for retrieving data from DynamoDb and map them to scala model.
This group is more important as it will be more often used (writing will be used just for filling db with data)

[1]: Extensibility.md
[2]: Architecture.md
[3]: https://github.com/bio4j/dynamograph-ddwriter
