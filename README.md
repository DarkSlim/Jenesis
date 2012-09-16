A Sega Genesis Emulator Written in Java
======

[![Build Status](https://secure.travis-ci.org/wmbest2/Jenesis.png?branch=master)](http://travis-ci.org/wmbest2/Jenesis)


## Contribution

Currently this project is closed for contribution.  It is strictly a learning bed for myself at the moment.  If you would like to use the code the code is available under GPL 3.

## TODO List

The following todo shows the current list of opcodes needing implemented.  I will hopefully soon have all the opcode decoded, only requiring implementation.

http://wmbest2.github.com/Jenesis/todo.html

## Building and Running

#### Compilation with Maven 3

        mvn clean compile assembly:single test

#### Cross Platform Compilation

All major platforms can be built.  The projected uses SWT so you will need to specify the build on which you are targetting.

Mac OS X x64 is the default profile but can also be targeted with the ` -P mac ` flag.  Other options include

* mac_x86
* linux
* linux_x86
* windows
* windows_x86

#### Running the JAR

On Non-Mac platforms

        java -jar target/Jenesis-1.0-SNAPSHOT-jar-with-dependencies.jar

On Mac platforms you must force the jar onto the main thread

        java -jar target/Jenesis-1.0-SNAPSHOT-jar-with-dependencies.jar -XstartOnFirstThread

