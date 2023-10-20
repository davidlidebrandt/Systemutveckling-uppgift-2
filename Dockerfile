FROM quay.io/wildfly/wildfly:30.0.0.Final-jdk20
ENV WILDFLY_USERNAME=user WILDFLY_PASSWORD=password
COPY target/s2-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
