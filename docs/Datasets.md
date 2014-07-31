## Supported datasets

Below you can find description of currently supported datasets.

### Gene onthology

What is gene ontology? "Gene onthology describes how gene produces behave in cellular context ..." (from [1][1])
It was first type of dataset supported by dynamograph.

#### Model

The most important node is Description of the model presents types of the nodes as well as types of edges

##### Nodes

- Gene ontology term:
  * id
  * name
  * definition
  * comment

- Namespace:
  * id

##### Edges
- is a
- has part
- part of
- regulates
- negatively regulates
- positively regualtes
- namepsace

### Ncbi Taxonomy

The Taxonomy Database is a curated classification and nomenclature for all of the organisms in the public sequence databases.
This currently represents about 10% of the described species of life on the planet.[2][3]

#### Model

Description of the model presents types of the nodes as well as types of edges

##### Nodes

- Taxon
  * id
  * name
  * comment
  * scientific name

- Rank
  * number
  * name

##### Edges

- assigned rank
- subrank
- parent


### Sources
- [Gene ontology documentation][1]
- [Gene ontology relationship description][2]
- [NCBI Taxonomy home page][3]

[1]: http://www.geneontology.org/page/documentation
[2]: http://www.geneontology.org/page/ontology-relations
[3]: http://www.ncbi.nlm.nih.gov/taxonomy