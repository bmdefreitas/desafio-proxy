FROM    java
MAINTAINER Bruno Medeiros <bmdefreitas@gmail.com>
USER    root
COPY /docker/config/hosts /etc/hosts
COPY proxy-reverso.jar /opt/.
COPY keystore.p12 /opt/.
EXPOSE 8443
ENTRYPOINT ["java"]
CMD ["-jar", "/opt/proxy-reverso.jar"]
