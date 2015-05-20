Trinity (Pre-alpha)
=====
[![Build Status](https://travis-ci.org/nhaarman/trinity.svg?branch=master)](https://travis-ci.org/nhaarman/trinity)

Inspired by Dagger and Ollie, Trinity generates all database stuff you don't want to write, without using reflection, and just the way you would write it.
Just annotate your repository interfaces with the `@Repository` tag, and annotate your model methods:

```java
@Table("employees")
public class Employee {

	private Long mId;

	private String mName;
	
	@Column("id")
	public Long getId() {
		return mId;
	}
	
	@Column("id")
	public void setId(Long id) {
		mId = id;
	}
	
	@Column("name")
	public String getName() {
		return mName;
	}
	
	@Column("name")
	public String setName(String name) {
		mName = name;
	}
}
```

```java
@Repository("employees")
public interface EmployeeRepository {
	
	public Employee find(Long id);
	
	public Long create(Employee employee);
	
}
```

Trinity will generate a `TrinityEmployeeRepository` class, which implements the `EmployeeRepository`.
See the example project on how to use Trinity.


*Note: Trinity is actively being developed, do not use this in production code!*

Build
-----

To build:

```
$ git clone git@github.com:nhaarman/trinity.git
$ cd trinity/
$ ./gradlew build
```

Debugging:

Add the following to ~/.gradle/gradle.properties, and run `./gradlew daemon`.

```
org.gradle.daemon=true
org.gradle.jvmargs=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
```

Next, in IntelliJ, add a Remote Run Configuration and start debugging. When building the project (`gradlew build`), the debugger will halt at breakpoints in the annotation
processor.

License
=======

    Copyright 2015 Niek Haarman

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
