@watchList
Feature: Watch list
	CRUD operations should be enabled for watch list entries.

Scenario: Create, read, update, delete
	When entries are viewed
	Then an entry can be created
	And an entry can be edited
	And an entry can be read
	And an entry can be deleted