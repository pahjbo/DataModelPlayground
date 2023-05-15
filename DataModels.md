Notes on Data Model Creation/Conversion
========================================


Existing VO-DML
---------------
In the cases where the VO-DML representation of a model already existed then the vodmltools
task vodmlToVodsl was used to create the vodsl. Some special cases are described below.

### CAOM
The [VODML](https://raw.githubusercontent.com/opencadc/caom2/master/caom2-dm/src/main/resources/CAOM-2.4-vodml.xml) for the 
model was not created using the standard VODML tools and actually has a number of 
issues with the VODML-IDs and VODML-REFS within it as a result of some lack of certainty within the VO-DML standard, as discussed in the [mailing list](http://mail.ivoa.net/pipermail/dm/2023-March/006364.html). The internal referencing within the CAOM has been fixed with a combination of manual edits and round-trip to VODSL.


Registry Data Models
--------------------

The registry data model is formally defined using the XML schema language
(see https://www.ivoa.net/xml/VOResource/VOResource-v1.1.xsd and associated schema).
The base VOResource and VODataService schema have been converted as well as some select others at this time.

For the purposes of potentially being able to reuse some of the concepts in
the registry data model in other data models, this note describes some of the issues
discovered when translating to VODSL.

Initially, to facilitate mainly automated translation, a vodmltools task vodmlXsdToVodsl was written using 
[xsd2dsl.xsl](https://github.com/ivoa/vo-dml/blob/master/tools/xslt/xsd2dsl.xsl) which can take an XML schema document and output a VODSL representation.
This script uses several heuristics based on xml schema design decisions used in registry
schema and so will perform less well on generic XML schema. Even with these
heuristics it is not able to do a perfect job.


### Conversion Issues

* ivo-id -> ivoid (the naming with a - not allowed)
* xs:simpleType not always a dtype (xs:attributes) - e.g. Validation
* SecurityMethod should be dtype


### VOResource Design Issues

* validation levels as numbers not allowed. (check in vodml) Validation should be otype

* creator/contributor not same type....

* ResourceName allows


### VODataService

* ignore STC 1.0!

Characterisation
----------------

The only machine-readable form of the data model is the [xml schema](https://www.ivoa.net/xml/Characterisation/Characterisation-v1.11.xsd). This schema is not structured in the same fashion as registry schema, and so the vodmlXsdToVodsl does not do such
a good job of representing the model. The initial VODSL has required quite extensive editing 
even to make it valid - however, this still will need some extra maual editing before it fully represents the original Characterisation model.


