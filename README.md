# Celebrity Finder

Celebrity Finder is an application to solve Find the Celebrity problem using Spring boot.

For this solution is used the JSON format to pass the information to the application.

Description: In a team of n people, a celebrity is known by everyone but he/she doesn't know anybody.
## Run UnitTest

## Assumptions

* A celebrity could know himself or not
* Could be no celebrities at the party
* Party have contain more than one person
* A person could know other people

```bash
gradlew test
```
## Build


```bash
gradlew build
```


## Usage

```bash
$java -jar celebrity-finder-test\build\libs\CelebrityTest-0.0.1.jar
```
## To Test
go to <http://localhost:7100/swagger-ui.html> there is the service documentation.

if you want to test the application on a rest client, inside testData folder you can find a few test data cases