@petService_annotation
Feature: petService Feature

#  Background: Sample General Preconditions Explanation
#    Given There is some predefined pet types like "dog"

  Scenario: Find Owner Feature
    Given There is an owner with ID 123
    When Find owner with ID 123 is called
    Then The owner with ID 123 is returned successfully
