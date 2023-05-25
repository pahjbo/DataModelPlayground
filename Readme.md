Data Model Playground
=====================

This repository does not contain the "official versions" of the data models, 
but gathers them together to make it easy to experiment with various 
refactorings. Note that some data models from outside the traditional DM WG are also included, as they make up part of the whole VO Data Model. 

It uses the [vodml tools](https://github.com/ivoa/vo-dml/blob/master/tools/ReadMe.md) and has the original source for the models as [vodsl](https://www.ivoa.net/documents/Notes/VODSL/index.html) in the [models](./models) directory.

There are some more [detailed notes](DataModels.md) on the initial import of the various data models into this repository.

Remember it is possible to generate documentation with  

```shell
gradle vodmlDoc
```
which will create files in [doc/generated](doc/generated)

If you want an IDE for editing the vodsl look [here](https://github.com/ivoa/ProposalDM/wiki/Installing-the-Eclipse-VODSL-editor) for instructions.

Fork, branch, play!

This branch is dedicated to trying to express all of the DMs used in the VO
including those in the interfaces.

[Notes on the DataModels](DataModels.md)