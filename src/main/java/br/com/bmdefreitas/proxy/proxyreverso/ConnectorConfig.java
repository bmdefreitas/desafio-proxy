package br.com.bmdefreitas.proxy.proxyreverso;


import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorConfig {

	@Bean
	public TomcatServletWebServerFactory servletContainer() {
	    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
	        
	        protected void postProcessContext(Context context) {
	            SecurityConstraint securityConstraint = new SecurityConstraint();
	            securityConstraint.setUserConstraint("CONFIDENTIAL");
	            SecurityCollection collection = new SecurityCollection();
	            collection.addPattern("/*");
	            securityConstraint.addCollection(collection);
	            context.addConstraint(securityConstraint);
	        }
	    };
	    tomcat.addAdditionalTomcatConnectors(getHttpConnector());
	    return tomcat;
	}

	private Connector getHttpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		
		SSLHostConfig sslHostConfig1 = new SSLHostConfig();
		sslHostConfig1.setHostName("test1.localdomain");
		sslHostConfig1.setCertificateKeyAlias("test1.localdomain");
		sslHostConfig1.setCertificateKeystoreFile("keystore.p12");
		sslHostConfig1.setCertificateKeystorePassword("123456");
		sslHostConfig1.setCertificateKeystoreType("PKCS12");
		
		connector.addSslHostConfig(sslHostConfig1);
		
		SSLHostConfig sslHostConfig2= new SSLHostConfig();
		sslHostConfig2.setHostName("test2.localdomain");
		sslHostConfig2.setCertificateKeyAlias("test2.localdomain");
		sslHostConfig2.setCertificateKeystoreFile("keystore.p12");
		sslHostConfig2.setCertificateKeystorePassword("123456");
		sslHostConfig2.setCertificateKeystoreType("PKCS12");
		
		connector.addSslHostConfig(sslHostConfig2);
		
		return connector;
	}
}
