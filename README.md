# Curve

Rather than connecting directly to the GitHub API I opted to mock out the data in structures similar to those it would be marshalled into subsequent to retrieval.

In a realistic scenario the data required to find the shortest path would need to be lazily loaded from the API, cached, and the search depth limited. For the purposes of this example that functionality does not exist.

## Running

The container app is a Spring Boot app, dependency managed and built using Maven, tests in JUnit, and running on Java 9.

To run the demo via the tests use `mvn test`.

## Notes

The following files contain comments outlining my thoughts as I was building this demo:

* [GitHubService.java](src/main/java/io/pardoe/curve/services/GitHubService.java)
* [PathCalculatorService.java](src/main/java/io/pardoe/curve/services/PathCalculatorService.java)

Apologies in advance for the quality of the Java, it has been a few years since I've written any.
