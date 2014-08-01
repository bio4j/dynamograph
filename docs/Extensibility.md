## Extensibility

This part of documentation cover next topics:

- Code organization
- How to extends functionality of Dynamograph

### Code organization

Code is organized in packages that divide code into different responsibilities:
- base package - utils methods, general common code
- reader - store code for translating high level read request to the DynamoDB requests
- writer - contains code responsible for interpreting save request and passing them to database
- parser - classes and functions for turning raw data into intermediate form
- model - definition of scala model for supported datasets
- mapper - sources for mapping intermediate parser output to the abstract intermediate format
- dao - there is code that orchestrate database requests

Diagram below presents package organization of the project

![Package organization][packages]


### Supporting additional datasets

Dynamograph designers took into account need for providing support for additional datasets. Thanks to it extending project by adding possibility to work on different type of data is simple. There are just few steps that developer need to do:

- implement scala model for new dataset - this step involves writing model representing data. For each of the vertex type developer should define type in the model(i.e GoTermType) as well as object representation(i.e. GoTerm).
Additionally there is need to define separates types for relationships(i.e IsAType) with object representation (i.e. IsA).
- writer parser (i.e PullGParser) that will read data from datasources (i.e files) with mapper (i.e GoMapper) responsible for mapping parsed data to the models.
- next step involves definition of tables for storing data
- last but not least thing that developer should do is creation of writers and readers for types defined in first step - something similar to goVertexReader, goVertexWriter.

Currently there is also need to write some more configuration that enables working with new dataset, but it is highly probably that in future release there will not be such requirement.

### Replacing database

Theoretically it is possible to replace DynamoDB by another database without much effort.
Such change could be done by implementing custom readers and writers that will operate on new database.(it was not tested in practice)

[packages]: img/packages.png

