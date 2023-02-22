# Find Best Plan

## **Problem Statement**

The input file is several lines, each line represents a plan (name, price, and one or more features,
separated by comma).

**Input:** 

A file contains list of plans

Input data requirements/assumptions:
* All plan names are unique
* Plan price are >0


**Output:**

Cheapest price followed by the combination of plans

## How to Build:

Build the project from the root directory using the following command

`mvn clean install -U dependency:copy-dependencies`

Once the build is successful, there will be app.jar file in the below path

`telepathy-problem-2/target/app.jar`

## To Execute the program

To execute the program, it requires input file location and required features as arguments

Run the below command from the app.jar folder

`java -jar app.jar "C:\input.txt" "email,voice,admin`




