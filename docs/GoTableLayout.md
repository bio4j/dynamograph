## Gene ontology DynamoDb Table Layout

Aim of this document is to describe the way in which Gene Ontology is (will be) stored in graph database based on DynamoDb.

### Introduction

In order to provide solution highly efficient that will meet all requirements there is strong need to understand all the DynamoDb features as well as know trade-offs of their application.
As a result presented solution could seem to be overcomplicated but in fact it was chosen by comparison to alternatives.


### Initial/General assumptions/requirements

Next assumptions influence tables layout:

- from API perspective it should be possible to query about in or out relationship per relationship type - there is no need to query about all the in or out relationships
- each of the node type will use separate table i.e there will be special table for GO as well as another one for taxonomy.
- for every type of the relation there will be 3 separate tables:
   - general table for relation containing id of the relation as well as other information
   - in relation table (explanation in Edge representation section)
   - out relation table (explanation in Edge representation section)

### Node representation - GO Term

For each node type there will be a separate table. Such solution increase performance as well as utilize read/write capacity in more reasonable way.
What is also important it enables to assume that each record in table will contain some basic values i.e namespace and definition.

```
GO_NODE : Hash Primary key
{
- id : Hash Primary Key,
- name
- namespace
- definition
- comment
}
```

### Edge representation - (Is a, Part of, Has part of, Negatively regulates, Positively regulates)

An Edge reflects some kind of relationship between 2 nodes. As a result edge consist of next values(between others):

 - source - first of the node in relationships
 - target - second node in relationships

 For each type of the relationship there will be next set of tables

 ```
 GO_[relationship_name]_IN : Hash Range Primary Key
 {
 - id : Hash Key
 - rel_id : Range Key
 }
 GO_[relationship_name]_OUT : Hash Range Primary Key
 {
 - id : Hash Key
 - rel_id : Range Key
 }
 GO_[relationship_name] : Hash Primary Key / Has Range Primary Key
 {
 - id : Hash Key
 - source
 - target
 }
 ```

 As a result all edges for given node will be distributed into several set of tables. Thanks to it query for single relationship will be faster and utilize resources in more reasonable way.


### Notes


Temporarily next values for GO Term will be ommited:
  - cross refs - in further work it will be used
  - subset - in further work it will be used
  - obsolete - terms with this flag are dropped
  - secondary_ids - legacy data

Presented name of tables could change with name but general idea should be the same.

## References

Some more information regarding Gene Onthology and model could be found in next places:
  - [bio4j PR](https://github.com/bio4j/bio4j/pull/40)
  - [Page witg GO to download](http://www.geneontology.org/GO.downloads.ontology.shtml)
  - [GO structure](http://beta.geneontology.org/page/ontology-structure)
  - [GO relations](http://beta.geneontology.org/page/ontology-relations)




