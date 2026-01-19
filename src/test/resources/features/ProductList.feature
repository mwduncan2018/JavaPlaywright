@productList
Feature: Product list
	CRUD operations should be enabled for products.

Scenario: Create, read, update, delete
	When products are viewed
	Then a product can be created
	And a product can be edited
	And a product can be read
	And a product can be deleted