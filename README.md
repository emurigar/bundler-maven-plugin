##BUNDLER MAVEN PLUGIN

A plugin for mixed java and ruby application development with jruby and bundler.
It only needs to have java 1.7 and maven installed in the system to be used.

##How to use

Add the plugin dependency to your pom.xml

```xml
<plugin>
	<groupId>com.byclosure.maven.plugins</groupId>
	<artifactId>bundler-maven-plugin</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</plugin>
```

To install gemfile dependencies (the first time it will download jruby and install bundler to vendor folder)
```bash
mvn bundle:install
````

To invoke and installed gem, for example, invoke middleman
```bash
mvn bundle:exec -Dbundler.exec_args="middleman server"
```

## Additional Links

* http://jruby.org/
* http://bundler.io/

## Contributions

We welcome all the help we can get!
