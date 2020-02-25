# NanoAda parser & translator
## Brief Introduction
This is a project to parse and translate NanoAda (or shortly Nada), a simplified programming language derived from *Ada*. It can do basic scope analysis and role analysis on Nada code translate it to Java code.
## How to Compile
In the terminal of the working directory, type
```
javac nada/nada.java
```
## How to Use
```
USAGE: java nada.Nada <src file>.
<src file> is where you put the path of your nada source file.
```
### Example
[primes.ada](./primes.ada) is a program printing all the prime numbers within 1-100. It was translated into [Primes.java](./Primes.java).

### Note
The current version does not support nested procedures because of the flat structure of Java methods. One could try to leverage *nested class* in Java to implement nested procedures, but it would result in uglily-organized Java code so I didn't do that.
