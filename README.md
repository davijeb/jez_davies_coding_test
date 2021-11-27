# Simple Integer Arithmetic Problem

## Table of contents
* [Solution Guide](#solution-guide)
* [Technologies](#technologies)
* [Setup](#setup)

## Solution Guide

I've written a solution to the problem in both Python and Java.

Note: There is a test file named input.csv that is used by both languages.
The file is a headerless csv with two columns: EXPRESSION, EXPECTED_RESULT (integer)

Python

1. Start with arithmetic.ipyng. This notebook walks through my approach to the problem and has the actual code in-line.

2. Next, the file algo.py is simply the same code described in the notebook in a python file ready for testing.

3. The last notebook arithmetic-tests.ipynb imports the above algo library and executes a series of unit and integration tests.

Java

1. Run Algo.java to see a worked example.

For the Java solution I have not included any tests as it would hav required loading external libraries (JUnit) and the tests in the Python implementation were extensive and it would have been a copy using like-for-like JUnit tests.
	
## Technologies
Project runtime is:
* Python 3.9.6
* Java OpenJDK 64-Bit Server VM (build 17.0.1+12-39, mixed mode, sharing)

The solution hs been tested on an Apple Mac Air M1
	
## Setup
To run this project, ensure the above language versions are loaded.

Note:
For the Python solution ideally you should have installed Jupyter (pip install jupyter) to use the interactive notebook (jupyter notebook). 