## Functionalities

Text below describes functionalities offered by Dynamograph.

### Basic functionalities

Functionalities offered by Dynamograph could be divided into two orthogonal groups:
- saving data to DynamoDB
- reading data from DynamoDB

Each of the groups covers the most important functionalities from Bio4j perspective, as a result user can imagine a lot of dfferent application that will require extending offered functionality.
This topic is described in more details in the [Extensibility](Extensibility.md) section.

## Writing group

This group of functionalities concentrate on uploading data to DynamoDB. Between others methods user can find:
- parsing raw data and transalting it into intermediate form
- mapping intermediate form into upload instructions — DynamoDB instructions
- execute upload instructions

Description of typical workflow for uploading data could be found in the [Architecture](Architecture.md) section.
What is extremely important is that writing data to db is organized in streams — part of data is red from file, parsed, translated into intermediate form and saved to db.
Such organization protect programs from gigantic memory usage and enables processing extremely big volumes of data.

Based on Dynamoraph functionalities concentrated around writing another project/task was build: [dynamograph-ddwriter](https://github.com/bio4j/dynamograph-ddwriter)

## Reading group

There user finds methods for retrieving data from DynamoDB and map them to scala model.
This group is more important as it will be more often used (writing will be used just for filling db with data).

<!-- Any other info about reading stuff? -->
