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
[own section](RegistryDM.md)

Characterisation
----------------

The only machine-readable form of the data model is the [xml schema](https://www.ivoa.net/xml/Characterisation/Characterisation-v1.11.xsd). This schema is not structured in the same fashion as registry schema, and so the vodmlXsdToVodsl does not do such
a good job of representing the model. The initial VODSL has required quite extensive editing 
even to make it valid - however, this still will need some extra maual editing before it fully represents the original Characterisation model.


