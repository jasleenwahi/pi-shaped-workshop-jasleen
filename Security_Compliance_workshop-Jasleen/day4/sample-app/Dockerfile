FROM ubuntu:18.04

RUN apt-get update && \
    apt-get -y upgrade && \
    apt-get install -y openjdk-17-jdk wget curl && \

    wget https://archive.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz && \
    tar -xvzf apache-maven-3.6.3-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.6.3 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    rm -f apache-maven-3.6.3-bin.tar.gz && \
    apt-get clean

WORKDIR /app


COPY . /app/


ENV MAVEN_OPTS="-Djava.library.path=/usr/lib/jvm/java-17-openjdk-amd64/lib --add-opens java.base/java.lang=ALL-UNNAMED"

RUN mvn clean package -DskipTests -Dmaven.compiler.source=17 -Dmaven.compiler.target=17

EXPOSE 8080/tcp

CMD ["java", "-jar", "/app/target/my-springboot-crud-app.jar"]

