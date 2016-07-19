# JFCUtil
This is a Java API with utilities to work with files, strings and HTTP connections. 

## Apache Maven Dependency Information

`<dependency>  
    <groupId>com.github.fcproj</groupId>  
    <artifactId>JFCUtil</artifactId>  
    <version>2.0.1</version>  
</dependency>`  

## System Requirements

- git >= 1.8.1.4
- java >= 1.6
- maven >= 3.0.3

## Building the jar
First of all, download the project using `git`:  

`git clone https://github.com/fcproj/jfcutil`  
  
Then, build the jar file:  
`cd jfcutil/`   
`mvn clean package`  

You can now find some jars in the directory `target` (also available in the directory `compiled` of this project):

- JFCUtil-$VERSION.jar does not include dependances
- JFCUtil-$VERSION-shaded.jar includes all dependances
- JFCUtil-$VERSION-javadoc.jar contains the javadoc
