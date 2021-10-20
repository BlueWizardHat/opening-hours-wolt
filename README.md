Opening Hours
=========================================================================

Technical assignment for job interview with Wolt


### Compile and run

Requirements:

* Maven (Tested with maven 3.8.2, any previous version may or may not work)
* A JDK 11 or higher (tested with [Eclipse Temurin](https://adoptium.net/) JDK 11 and JDK 17)

Command to compile and run (using bash):

    mvn clean package && java -jar target/opening-hours.jar


### Make a request

Example of making a request using the testdata.json file in the src/test/resources directory:

    curl -H "Content-Type: application/json" -d @src/test/resources/testdata.json http://localhost:8080/api/openinghours/format


## JSON input format

In my opinion the JSON input format could be improved in at least the following ways:

1. Using proper local times in 24-hour format instead of seconds from midnight. That would make things easier to debug since "10:00" is
   more readble than 36000 to humans, and also could be deserialized by a framework such as jackson directly to LocalTime without converting
   or making a custom deserializer.

2. Having the open/close time explicitly contain an opening and a closing time instead of having open and close times as separate entries
   into JSON (even in the case of the closing time being after midnight and therefore technically on the following day). The current format
   has potential for a lot of errors and means we have to make a lot of assumptions when reading the format.
   For instance I had to make the following assumptions in the converter layer.

   * If the closing time is missing not only on a day, but on the next day as well, I assume the store/restaunrant closes at the followings
     days open time or at midnight if the following day is closed all day.

   * I can ignore multiple "open" times in a row and the same for multiple "close" times in a row.

   With a stricter format some of these assumptions could be avoided. And also there would not be a need for parsing logic to handle the case
   where a store/restaurant does not close on the same day as they open.
