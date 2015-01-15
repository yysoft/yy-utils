# YY UTILS.

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

