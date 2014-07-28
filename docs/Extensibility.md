## Extensibility

This part of documentation cover next topics:

- How to extends functionality of Dynamograph
- What to do in order to support another datasets

### Extending offered functionalities

Dynamograph offers the most important functions from bio4j perspective

### Supporting additional datasets

Dynamograph designers took into account need for providing support for additional datasets. Thanks to it extending project by adding possibility to work on different type of data is simple. There are just few steps that developer need to do:

- implement scala model for new dataset - this step involves writing model representing data. For each of the vertex type developer should define type in the model(i.e GoTermType) as well as object representation(i.e. GoTerm).
Additionally there is need to define separates types for relationships(i.e IsAType) with object representation (i.e. IsA).
- writer parser (i.e PullGParser) that red data from datasources (i.e files) with mapper (i.e GoMapper) responsible for mapping parsed data to the models.
- last but not least thing that developer should do is creation of writers and readers for types defined in first step - something similar to goVertexReader, goVertexWriter.

Currently there is also need to write some more configuration that enables working with new dataset, but it is highly probably that in future release there will not be such requirement.


