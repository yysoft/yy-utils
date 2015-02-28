# YY UTILS.

## Utils List

1. [MD5](#MD5)
1. [HttpRequestUtil](#HttpRequestUtil)

### MD5

Class: ``net.caiban.utils.MD5``

Description:

> Encode string use MD5 method.

Example:

```java
try {
	MD5.encode("be-encoded-string", MD5.LENGTH_16);
} catch (NoSuchAlgorithmException e) {
	e.printStackTrace();
} catch (UnsupportedEncodingException e) {
	e.printStackTrace();
}
```

### HttpRequestUtil

Class: ``net.caiban.utils.http.HttpRequestUtil``

Description:

> Request http connection, only get/post method provide.

Example:

```java
//Get method, return as String.
String respGet = HttpRequestUtil.httpGet("http://www.example.com");

//Post method, return as String.
String respPost = HttpRequestUtil.httpPost("http://www.example.com");

//Post method with parameters.
List<NameValuePair> params = new ArrayList<NameValuePair>();
params.add(new BasicNameValuePair("key", "value"));

String respPostWithParam = HttpRequestUtil.httpPost("http://www.example.com", params);

```

> :exclamation: Allways do this while start and shutdown server recommend.

```java
//start server.
httpRequestUtil.monitor();

//shutdown server.
HttpRequestUtil.shutdown();
```

> Maven support, only [oschina](http://maven.oschina.net)

```xml
<dependency>
    <groupId>net.caiban</groupId>
    <artifactId>yy-utils</artifactId>
    <version>1.1.7</version>
</dependency>
```
