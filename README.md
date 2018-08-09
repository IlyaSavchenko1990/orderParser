# orderParser
Console app for parsing fies with orders data in specified format. Json and CSV files extensions are supported

This console application is used to parse files with strictly specified format but with different extensions.
For now supported format are csv and json, but it is easy to add a different format support. It is all it's all thanks 
to a convenient application strcuture.

To install this app you need to download this project and in cmd use maven "mvn package" comand. 
App will be extracted into target folder of extracted project. 
You have to use OrderParser-1.0-SNAPSHOT-jar-with-dependencies.jar file

To run this application you have to use jvm commands in cmd - "java -jar app.jar". It receives files names to parse.
For example, the command to start parsing files "order1.json", "order2.json", "order3.csv" is - 
"java -jar app.jar order1.json order2.json order3.json". 

Converted result will be printed out to your console. 
If all order file format is correct then result field = "OK", if there occur an error, then you will see it
in the result field.

Passed files must be located in the same directory as jar file. If files was not passed then you will see
"elements is empty!" error. If only one or several files was not found then the error message will print it,
but other files will be parsed succesfully;

You can find test files with "json" and "csv" extension in src/test/resources directory of package. Just place them in 
the same directory as jar file.
