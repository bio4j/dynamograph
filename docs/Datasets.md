## Supported datasets

Below you can find description of currently supported datasets.

### Gene onthology

What is gene ontology? "Gene ontology describes how gene produces behave in cellular context ..." (from [\[1\]][1]).
It is the first dataset supported by Dynamograph, and serves as a proof-of-concept / test dataset.

#### Model

Description of the model presents types of the nodes as well as types of edges

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
- positively regulates
- namespace


### Sources

- \[1\]: [Gene ontology documentation][1]
- \[2\]: [Gene ontology relationship description][2]

[1]: http://www.geneontology.org/page/documentation
[2]: http://www.geneontology.org/page/ontology-relations
