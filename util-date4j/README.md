Date4j - It's an alternative to Date, Calendar, and related Java classes.
----------

Maven Reporsity
```xml
<dependency>
  <groupId>com.darwinsys</groupId>
  <artifactId>hirondelle-date4j</artifactId>
  <version>1.5.1</version>
</dependency>
```

Github proejct: https://github.com/IanDarwin/date4j

Examples:

Here are some quick examples of using date4j's DateTime class (more examples are available here):
```java
DateTime dateAndTime = new DateTime("2010-01-19 23:59:59");
DateTime dateAndTime = new DateTime("2010-01-19T23:59:59.123456789");
DateTime dateOnly = new DateTime("2010-01-19");
DateTime timeOnly = new DateTime("23:59:59");
DateTime dateOnly = DateTime.forDateOnly(2010,01,19);
DateTime timeOnly = DateTime.forTimeOnly(23,59,59,0);

DateTime dt = new DateTime("2010-01-15 13:59:15");
boolean leap = dt.isLeapYear(); //false
dt.getNumDaysInMonth(); //31
dt.getStartOfMonth(); //2010-01-01, 00:00:00.000000000
dt.getEndOfDay(); //2010-01-15, 23:59:59.999999999
dt.format("YYYY-MM-DD"); //formats as '2010-01-15'
dt.plusDays(30); //30 days after Jan 15
dt.numDaysFrom(someDate); //returns an int
dueDate.lt(someDate); //less-than
dueDate.lteq(someDate); //less-than-or-equal-to
```

Although DateTime carries no TimeZone information internally, there are methods that take a TimeZone as a parameter:
```java
DateTime now = DateTime.now(someTimeZone);
DateTime today = DateTime.today(someTimeZone);
DateTime fromMilliseconds = DateTime.forInstant(31313121L, someTimeZone);
birthday.isInFuture(someTimeZone);
dt.changeTimeZone(fromOneTimeZone, toAnotherTimeZone);
```
