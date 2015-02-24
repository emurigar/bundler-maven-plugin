##BUNDLER MAVEN PLUGIN

A plugin for mixed java and ruby application development with jruby and bundler.

It only needs to have java 1.7 and maven installed in the system to be used.

##How to use

Add the plugin dependency to your pom.xml

```xml
<plugin>
	<groupId>com.byclosure.maven.plugins</groupId>
	<artifactId>bundler-maven-plugin</artifactId>
	<version>0.0.1</version>
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

## Parameters for goals

* bundle:install
```bash
  Available parameters:

    binstubs (Default: ${project.basedir}/vendor/bin)
      Expression: ${bundler.binstubs}
      (no description available)

    bundler_version (Default: 1.8.2)
      Expression: ${bundler.bundler_version}
      (no description available)

    gem_home (Default: ${project.basedir}/vendor/gem_home)
      Expression: ${bundler.gem_home}
      (no description available)

    gem_path (Default: ${project.basedir}/vendor/gem_home)
      Expression: ${bundler.gem_home}
      (no description available)

    gemfile (Default: Gemfile)
      Expression: ${bundler.gemfile}
      (no description available)

    jruby_bin (Default: ${project.basedir}/vendor/jruby-1.7.19/bin)
      Expression: ${bundler.jruby_home}
      (no description available)

    jruby_download_location (Default:
    https://s3.amazonaws.com/jruby.org/downloads/1.7.19/jruby-bin-1.7.19.tar.gz)
      Expression: ${bundler.jruby_download_location}
      (no description available)

    vendor (Default: ${project.basedir}/vendor)
      Expression: ${bundler.vendor}
      (no description available)

    working_dir (Default: ${project.basedir})
      Expression: ${bundler.working_dir}
      (no description available)
```

* bundle:update
```bash
  Available parameters:

    binstubs (Default: ${project.basedir}/vendor/bin)
      Expression: ${bundler.binstubs}
      (no description available)

    bundler_version (Default: 1.8.2)
      Expression: ${bundler.bundler_version}
      (no description available)

    gem_home (Default: ${project.basedir}/vendor/gem_home)
      Expression: ${bundler.gem_home}
      (no description available)

    gem_path (Default: ${project.basedir}/vendor/gem_home)
      Expression: ${bundler.gem_home}
      (no description available)

    gemfile (Default: Gemfile)
      Expression: ${bundler.gemfile}
      (no description available)

    jruby_bin (Default: ${project.basedir}/vendor/jruby-1.7.19/bin)
      Expression: ${bundler.jruby_home}
      (no description available)

    jruby_download_location (Default:
    https://s3.amazonaws.com/jruby.org/downloads/1.7.19/jruby-bin-1.7.19.tar.gz)
      Expression: ${bundler.jruby_download_location}
      (no description available)

    vendor (Default: ${project.basedir}/vendor)
      Expression: ${bundler.vendor}
      (no description available)

    working_dir (Default: ${project.basedir})
      Expression: ${bundler.working_dir}
      (no description available)
```

* bundle:exec
```bash
  Available parameters:

    binstubs (Default: ${project.basedir}/vendor/bin)
      Expression: ${bundler.binstubs}
      (no description available)

    bundler_version (Default: 1.8.2)
      Expression: ${bundler.bundler_version}
      (no description available)

    execArgs
      Expression: ${bundler.exec_args}
      (no description available)

    gem_home (Default: ${project.basedir}/vendor/gem_home)
      Expression: ${bundler.gem_home}
      (no description available)

    gem_path (Default: ${project.basedir}/vendor/gem_home)
      Expression: ${bundler.gem_home}
      (no description available)

    jruby_bin (Default: ${project.basedir}/vendor/jruby-1.7.19/bin)
      Expression: ${bundler.jruby_home}
      (no description available)

    jruby_download_location (Default:
    https://s3.amazonaws.com/jruby.org/downloads/1.7.19/jruby-bin-1.7.19.tar.gz)
      Expression: ${bundler.jruby_download_location}
      (no description available)

    vendor (Default: ${project.basedir}/vendor)
      Expression: ${bundler.vendor}
      (no description available)

    working_dir (Default: ${project.basedir})
      Expression: ${bundler.working_dir}
      (no description available)
```

## Additional Links

* http://jruby.org/
* http://bundler.io/

## Contributions

We welcome all the help we can get!
