# colle
Contains working sample how to run and deploy scala job

# Init project

You can create a skeleton for your project using
`mkdir -p <PROJECT NAME>/src/main/scala/colle`
`mkdir -p <PROJECT NAME>/src/test/scala`
```
  <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.amazonaws</groupId>
    <artifactId>AWSGlueApp</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>${project.artifactId}</name>
    <description>AWS Glue ETL application</description>

        <properties>
        <scala.version>2.11.1</scala.version>
        </properties>

    <dependencies>
        <dependency>
            <groupId>org.scala-lang</groupId>
            <artifactId>scala-library</artifactId>
            <version>${scala.version}</version>
        </dependency>
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>AWSGlueETL</artifactId>
			
            <version>Glue version</version>
			
        </dependency>
    </dependencies>

    <repositories>
        <repository>
            <id>aws-glue-etl-artifacts</id>
            <url>https://aws-glue-etl-artifacts.s3.amazonaws.com/release/</url>
        </repository>
    </repositories>
    <build>
        <sourceDirectory>src/main/scala</sourceDirectory>
        <plugins>
            <plugin>
                <!-- see http://davidb.github.com/scala-maven-plugin -->
                <groupId>net.alchim31.maven</groupId>
                <artifactId>scala-maven-plugin</artifactId>
                <version>3.4.0</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>testCompile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>1.6.0</version>
                <executions>
                    <execution>
                        <goals>
                        <goal>java</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                <systemProperties>
                    <systemProperty>
                        <key>spark.master</key>
                        <value>local[*]</value>
                    </systemProperty>
                    <systemProperty>
                        <key>spark.app.name</key>
                        <value>localrun</value>
                    </systemProperty>
                    <systemProperty>
                        <key>org.xerial.snappy.lib.name</key>
                        <value>libsnappyjava.jnilib</value>
                    </systemProperty>
                </systemProperties>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-enforcer-plugin</artifactId>
                <version>3.0.0-M2</version>
                <executions>
                    <execution>
                        <id>enforce-maven</id>
                        <goals>
                            <goal>enforce</goal>
                        </goals>
                        <configuration>
                            <rules>
                                <requireMavenVersion>
                                    <version>3.5.3</version>
                                </requireMavenVersion>
                            </rules>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>

```
# Build

docker build . -t colle

# Run

docker run -t colle

# Utility for test

# Parquet file generator

To make this project more useful instead of keeping handful of file for testing we will generate them using another script  
This will allow use to insure all type are properly supported and stress charge with load of files

# Fake s3

localstack (see bug is resolved for scala s3 lib)
