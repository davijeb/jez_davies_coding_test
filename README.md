# Simple Integer Arithmetic Problem

## Table of contents
* [Solution Guide](#solution-guide)
* [Technologies](#technologies)
* [Setup](#setup)

## Solution Guide

I've written a solution to the problem in both Python and Java.

Note: There is a test file named input.csv that is used by both solutions.
The test file is a csv with two columns: EXPRESSION, EXPECTED_RESULT (integer)

## Python
```
1. Start with arithmetic.ipyng. This notebook walks through my 
   approach to the problem and has the actual code in-line.

2. Next, the file algo.py is simply the same code described in 
   the notebook in a python file ready for testing.

3. The last notebook arithmetic-tests.ipynb imports the above 
   algo library and executes a series of unit and integration tests.
```

## Java
```
1. Run Algo.java to see a worked example.

For the Java solution I have written a basic test class (AlgoTest) 
which is included in the Algo class. The reason is that for simplicity 
I have no dependencies on external libraries, like JUnit, to run the 
tests. The tests are not as extensive as the Python solution but 
in the real-world I would have included an actual test-library 
and would have mirrored the Python tests.
```
## Technologies
```
Project runtime is:
* Python 3.9.6
* Java OpenJDK 64-Bit Server VM (build 17.0.1+12-39, mixed mode, sharing)

The solution has been tested on a MacBook Air (M1, 2020)
```

## Setup
```
To run this project, ensure the above language versions are loaded.

Note:
For the Python solution ideally you should have installed 
Jupyter (pip install jupyter) to use the interactive 
notebook (jupyter notebook).
``` 