@sandbox
Feature: Sandbox

Scenario Outline: Manufacturer match, model match
	Given a product is added with manufacturer "<productManufacturer>" and model "<productModel>"
	And an entry is added with manufacturer "<entryManufacturer>" and model "<entryModel>"
	When standard matching is enabled
	Then the product with manufacturer "<productManufacturer>" and model "<productModel>" is a standard match

	Examples:
	| productManufacturer | productModel | entryManufacturer | entryModel |
	| Porsche             | Cayenne      | Porsche           | Cayenne    |
