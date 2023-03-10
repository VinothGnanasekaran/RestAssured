#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: This Feature is to create the Place Validations of RSA in framework developement
 
  Scenario Outline: Scenario for the AddPlace API
    Given User to add all Request Builder Spec for the AddPlace request with "<name>" "<language>" "<address>"
      When User calls for "addPlace" Api call with "Post" method
        Then User should be successful with request status code 200
    	And User validates the "status" in the response body is  "OK"
    And User to validate the "<name>" in the response body when "getPlace" api is called
    
    
     Examples:
  |	name 	| language 		| address 				|
 # |Test1		|	English-IN	|	World Center|
 # |Test2		|	French-IN		|	Europe Center|
  |Test3		| Tamil-IN			|	Indian-South|


    		  
  
  