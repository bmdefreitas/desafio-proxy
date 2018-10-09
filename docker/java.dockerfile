FROM java
MAINTAINER Bruno Medeiros <bmdefreitas@gmail.com>
USER root
COPY /docker/config/hosts /root/hosts
RUN git clone https://github.com/bmdefreitas/desafio-proxy.git /opt/desafio-proxy
RUN cp /opt/desafio-proxy/keystore.p12 /opt/keystore.p12
RUN keytool -importkeystore -v -srckeystore /opt/keystore.p12 -srcstoretype PKCS12 -srcstorepass 123456 -destkeystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -deststorepass changeit
RUN cp /root/hosts /etc/hosts 
RUN /opt/desafio-proxy/gradlew -b /opt/desafio-proxy/build.gradle bootJar
RUN cp /opt/desafio-proxy/build/libs/proxy-reverso-0.1.0.jar /opt/proxy-reverso.jar
EXPOSE 22 8443
ENTRYPOINT ["java"]
CMD ["-jar", "/opt/proxy-reverso.jar"]
