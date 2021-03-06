# Chess [![MIT license](https://img.shields.io/badge/License-MIT-blue.svg)](https://lbesson.mit-license.org/)
A classic game of chess with a hint of artificial intelligence 
 


### Under Development

This is a *mostly* functional game of chess written in java. The project is being
developed under the *Model-View-Controller (MVC)* design pattern. There are also
some uses of the *factory* design pattern. The goal is to hopefully use machine learning algorithms to 
play chess instead of using the traditional brute force method 
of searching a decision tree. 

### Currrent Structure
![UML](Chess_UML.png)

## Goals
> The primary goal of this project is to create well organized and well documented software.  

### To Be Implemented:
* ~~castling~~
* ~~pawn on pawn en passante~~
* players, ~~clocks~~, ~~movelogs~~
* pawn promotion
* ~~player turns~~
* a brute force engine
* a more aesthetic gui
* ability to play online
* a machine learning algorithm  

## Getting Started  

### Cloning

To import this chess software, execute the following line of code in your command console

```
 git clone https://github.com/haxdds/Chess.git
```

### Running  

To run the current version of this chess software, execute the following lines of code
in your command console in the .../Chess/src/chess directory:
```
 javac Test.java
 java Test
```
This should cause the chess gui to pop up. 

### If using bash  

in your terminal run

`make`

from this projects root directory.
then it should be compiled then the gui should show up.

If your on windows checkout:

https://gist.github.com/evanwill/0207876c3243bbb6863e65ec5dc3f058

to install make for git bash.

## Release History  

* 0.0.1
    * Work in progress  

## Contributing

We love your input! We want to make contributing to this project as easy and transparent as possible, whether it's:

- Reporting a bug
- Discussing the current state of the code
- Submitting a fix
- Proposing new features
- Becoming a maintainer

## Authors

* **Rahul Chowdhury** - [haxdds](https://github.com/haxdds)
* **Stephon Lawrence** - [stephonlawrence](https://github.com/stephonlawrence)


