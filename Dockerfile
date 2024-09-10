FROM payara/micro:6.2024.8-jdk21

COPY target/JakartaEEMicroserv-1.0-SNAPSHOT.war $DEPLOY_DIR