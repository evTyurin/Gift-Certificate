## Gift Certificates with Tags

Web service for Gift Certificates system that expose REST APIs to perform the following operations:
* CRUD operations for GiftCertificate. If new tags are passed during creation/modification – they should be created in the DB. For update operation - update only fields, that pass in request, others should not be updated. Batch insert is out of scope.
* CRD operations for Tag.
* Get certificates with tags (all params are optional and can be used in conjunction):
  * by tag name (ONE tag)
  * search by part of name/description (can be implemented, using DB function call)\
  * sort by date or by name ASC/DESC (extra task: implement ability to apply both sort type at the same time).

All exceptions are meaningful and localized on backend side. Exception code meaning:
* **40000** - invalid parameters/parameter values in json
* **40004** - not found in database
* **40009** - already exist in database
* **40017** - invalid parameters in url for search
* **40101** - not valid gift certificate name (length of the field must be between 3 and 50 symbols)
* **40102** - not valid gift certificate description (length of the field must be between 10 and 1000 symbols)
* **40103** - not valid gift certificate duration (it must be bigger than 0)
* **40104** - not valid gift certificate price (it must be bigger than 0)
* **40105** - not valid tag name (length of the field must be between 3 and 50 symbols)
* **40106** - blank field is invalid
* **40107** - not valid user id (it must be bigger than 0)