# Dynamograph

Dynamograph is graph database based on DynamoDB database. It was designed for storing biological data but it could be used
 for storing each type of data

This part of documentation provides generall overview of the project.

## Word etymology

Dynamograph (Dynamo-Graph) comes from connection of two words: [DynamoDB][1] and [Graph][2]. Each of them are equally important as express two orthogonal things:
  - Dynamo part (from DynamoDB) part indicates that project is based on Amazon's Key Value databse - technological information
  - Graph part reveal type of structures that could be stored and effectively managed - functional information

## Project assumptions

Solutions was designed for storing biomedical data like Gene Ontology, ncbiTaxonomy etc. As a result some assumptions were made to facilitate work with dynamoDB and take advantage of database characteristic.
The most important assumptions that introduce changes to architecture of solution:
- writing are completely separated from reading - in fact writing data to database occurs only at the beginning - fulfilment of database. After uploading data system offers only read operations
- static data model - once defined will not change with time
- connection between edges are directed - each of the node have in-edges as well as out-edges

## How it works?
Structure of data is modelled with specially designed database structure that is optimized for read operations.
Graph structure could be divided into two type of hierarchy units:
  - vertex (node)
  - edge (link, connection)

#### Vertex
For each type of the vertex defined in graph dataset there is separate table. Such table contains vertex properties as well as additional values which enables properly identify node.
Division of vertex types into separate tables ensures fast execution of read operations on vertices as well as easy connection with corresponding edges.

#### Edge
Edges modelled in Dynamograph are directed (it is possible to store undirected graphs) - there are in-edges and out-edges.
Mentioned assumption has profound impact on design of tables that reflects this kind of relationship. For each of the edge there is specials set of tables:
- table containing edge properties
- table storing connection between edge and in-vertex
- table storing connection between edge and out-vertex

<aside class="notice">
- **in-vertex** - vertex to which come directed edge
- **out-vertex** - vertex from which come directed edge
</aside>

## Why DynamoDB?

Lorem ipsum dolor sit amet, consectetur adipisicing elit.


## Interactions with other tools

Between others dynamogprah uses next tools and libraries:
- [tabula][3]
- [scarph][4]
- [aws-sdk][5]

[1]: http://aws.amazon.com/dynamodb/
[2]: http://en.wikipedia.org/wiki/Graph_(mathematics)
[3]: https://github.com/ohnosequences/tabula
[4]: https://github.com/ohnosequences/scarph/
[5]: https://github.com/aws/aws-sdk-java