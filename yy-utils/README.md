# YY UTILS.

> Maven support, only [oschina](http://maven.oschina.net)

```xml
<dependency>
    <groupId>net.caiban</groupId>
    <artifactId>yy-utils</artifactId>
    <version>1.1.8</version>
</dependency>
```

## Utils List

1. [MD5](#MD5)
1. [HttpRequestUtil](#HttpRequestUtil)
2. [JedisUtil](#JedisUtil)
3. [MemcachedUtils](#MemcachedUtils)

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

### JedisUtil

Class: ``net.caiban.utils.cache.JedisUtil``

Description:

> Java redis client which use [Jedis](https://github.com/xetorthio/jedis) project.

Example:

```java
Jedis jedis = null;
try {
	jedis = JedisUtil.getJedis();
 	Set<String> tags = jedis.keys("*");
 	jedis.set("key","value");
 	// Others of jedis action.
} catch (Exception e) {
	e.printStackTrace();
}finally{
	//It's necessary!
	JedisUtil.getPool().returnResource(jedis);
}
```

Startup:

> Default redis server properties : ``redis.properties``, or you could init from other properties ( ``JedisUtil.initPool("config.properties")`` or ``JedisUtil.initPool("file:/path/to/config.properties")`` ) when system startup.

Config Example:
```properties
redis.server=127.0.0.1
redis.server.port=6379
```

### MemcachedUtils

Class: ``net.caiban.utils.cache.MemcachedUtils``

Description:

> Java memcached client.

Example:

```java
MemcachedUtils.getInstance().getClient().set("user",0,"user profile");
```

Startup:

> Default memcached server properties : ``cache.properties``, you should init client ( ``MemcachedUtils.getInstance().init()`` or ``MemcachedUtils.getInstance().init("config.properties")`` or ``JedisUtil.initPool("file:/path/to/config.properties")`` ) when system startup.

Config Example:
```properties
memcached.server=127.0.0.1:11211 localhost:11210
```
