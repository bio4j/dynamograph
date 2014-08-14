# Dynamograph documentation

Presented document describes Dynamograph — a prototype for a DynamoDB-backed graph database.
This document is divided into several units — each of them covering different aspects of Dynamograph. Below you can find links to other important topics

1. [Functionalities][6]
2. [Architecture][7]
3. [Project usage][8]
4. [Supported datasets][9]
5. [Example layout description][10]
6. [Extensibility][11]
7. [Summary][12]

## Introduction

Dynamograph is a simple graph database based on [DynamoDB][1]. Its design is heavily influenced by the needs of [Bio4j](http://bio4j.com), so it would be a nice fit for those scenarios where _TODO explain/link to these particular needs — project assumptions?_

This part of the documentation provides general overview of the project.

### Word etymology

Dynamograph comes from connection of two words: [DynamoDB][1] and [Graph][2]. Each of them are equally important as express two orthogonal things:
 - First part (from DynamoDB) indicates that the project is based on Amazon's Key Value database — technological information
 - Graph part reveal type of structures that could be stored and effectively managed — functional information

### Project assumptions

Dynamograph was designed for storing biomedical data like Gene Ontology, ncbiTaxonomy etc. As a result some assumptions were made to facilitate work with dynamoDB and take advantage of database characteristic.
The most important assumptions that introduce changes to architecture of solution:
- writing is completely separated from reading — in fact writing data to the database occurs only at the beginning. After uploading data system offers only read operations
- static data model — once defined it will not change over time
- connection between edges are directed — each of the node have in-edges as well as out-edges

### How it works?

Structure of data is modeled with specially designed database structure that is optimized for read operations.
Graph structure could be divided into two type of hierarchy units:
  - vertex (node)
  - edge (link, connection)

#### Vertex

For each type of the vertex defined in graph dataset there is separate table. Such table contains vertex properties as well as additional values which enables properly identify node.
Division of vertex types into separate tables ensures fast execution of read operations on vertices as well as easy connection with corresponding edges.

#### Edge

The edges modeled in Dynamograph are directed (it is possible to store undirected graphs): there are in-edges and out-edges.

This has a profound impact on the design of tables, that reflects this kind of relationship. For each edge type there is a special set of tables:

- a table containing edge properties
- a table storing the connection between an edge and its in-vertex
- a table storing the connection between edge and its out-vertex

<aside class="notice">
- **in-vertex** — vertex to which come directed edge
- **out-vertex** — vertex from which come directed edge
</aside>

## Why DynamoDB?

<!-- TODO Something's missing here -->
Lorem ipsum dolor sit amet, consectetur adipisicing elit.


## Interactions with other tools

Between others Dynamograph uses these tools and libraries:

- [scarph][4]
- [aws-sdk][5]

[1]: http://aws.amazon.com/dynamodb/
[2]: http://en.wikipedia.org/wiki/Graph_(mathematics)
[4]: https://github.com/ohnosequences/scarph/
[5]: https://github.com/aws/aws-sdk-java
[6]: Functionalities.md
[7]: Architecture.md
[8]: Usage.md
[9]: Datasets.md
[10]: GoTableLayout.md
[11]: Extensibility.md
[12]: Summary.md
