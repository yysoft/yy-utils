How to use
==========

1. It dependent to spring-web project. current version is 3.2.2.RELEASE

2. Spring config xml
```xml
	<bean id="multipartResolver"
		class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<property name="maxUploadSize" value="104857600" />
		<property name="maxInMemorySize" value="4096" />
	</bean>
```

3. Use it in java

Upload one file.
```java
		try {
			UploadResult result = MvcUpload.localUpload(request, new String(path), new JarUploadFilter());
			return result.getFullPath();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UploadException e) {
			e.printStackTrace();
		}
```

Upload many files.
```java
Map<String, UploadResult> resultMap = MvcUpload.batchLocalUpload(request, "/home/parox/static/task/jar/");
```
You can extend AbstractUploadFilter to adapter your project.