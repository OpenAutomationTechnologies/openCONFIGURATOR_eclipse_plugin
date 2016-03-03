openCONFIGURATOR Plugin {#page_openconfig_eclipse_plugin}
================

## Build openCONFIGURATOR plugin:
------------------------------

### Pre-requisites:

- JDK of version 1.7 or above.
- Apache-maven 3.3.3 or above.

### Maven installation steps:

#### Windows x86 and x86_64:

- Download "apache-maven-3.3.3" or above.
- Add "bin" directory of maven package to the target system path by updating it into system's environment variables with variable name MAVEN_HOME or by using command terminal
        Set PATH = "C:\Program Files\apache-maven-3.3.3\bin";%PATH%

- Add bin directory of JDK to the target system path by updating it into system's environment variables with variable name JAVA_HOME
- OR by using command terminal
        Set JAVA_HOME = "C:\program files\jdk1.7.0_69\bin";%JAVA_HOME%

#### Linux x86 and x86_64:

- Install maven into the target system using software centre OR
- by using the following command in a terminal
        sudo apt-get install maven

### Maven-build steps:

After installing the maven into the system, perform the following steps in the command terminal to build openCONFIGURATOR plugin,

- Set the path to openCONFIGURATOR plugin code as the working directory.
- Execute the following command to build the package
        mvn clean compile package
- The final built output package will be available in the org.epsg.openconfigurator.updatesite/target directory.
