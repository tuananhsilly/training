# training

Java 21 console app built with Maven (via the Maven Wrapper) and tested with JUnit 5.

## Requirements

- JDK 21 — with [SDKMAN](https://sdkman.io): `sdk env install` (reads `.sdkmanrc`)

## Usage

```bash
./mvnw verify                              # compile + run tests + package
./mvnw -q exec:java -Dexec.args="Tunna"    # run the app
java -jar target/training-1.0-SNAPSHOT.jar # run the packaged jar
```
