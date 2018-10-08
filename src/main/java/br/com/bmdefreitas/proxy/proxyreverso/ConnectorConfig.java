package br.com.bmdefreitas.proxy.proxyreverso;


import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorConfig {
	
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector());
		return tomcat;
	}

	private Connector createSslConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
		try {
			connector.setScheme("https");
			connector.setSecure(true);
			connector.setPort(8443);
			protocol.setSSLEnabled(true);
			protocol.setKeystoreFile("/opt/keystore.p12");
			protocol.setKeystorePass("123456");
			protocol.setKeyAlias("localdomain");
			
			SSLHostConfig sslHostConfig1 = new SSLHostConfig();	
			sslHostConfig1.setHostName("test1.localdomain");
			
			SSLHostConfigCertificate certificate1 = new SSLHostConfigCertificate(sslHostConfig1, SSLHostConfigCertificate.DEFAULT_TYPE);		
			certificate1.setCertificateKeystoreFile("/opt/keystore.p12");
			certificate1.setCertificateKeystorePassword("123456");
			certificate1.setCertificateKeyAlias("test1.localdomain");		
			certificate1.setCertificateKeystoreType("PKCS12");
			sslHostConfig1.addCertificate(certificate1);
			
			connector.addSslHostConfig(sslHostConfig1);
			
			SSLHostConfig sslHostConfig2 = new SSLHostConfig();	
			sslHostConfig2.setHostName("test2.localdomain");

			SSLHostConfigCertificate certificate2 = new SSLHostConfigCertificate(sslHostConfig1, SSLHostConfigCertificate.DEFAULT_TYPE);		
			certificate2.setCertificateKeystoreFile("/opt/keystore.p12");
			certificate2.setCertificateKeystorePassword("123456");
			certificate2.setCertificateKeyAlias("test2.localdomain");		
			certificate2.setCertificateKeystoreType("PKCS12");
			sslHostConfig2.addCertificate(certificate2);
			
			connector.addSslHostConfig(sslHostConfig2);
			return connector;
		}
		catch (Exception ex) {
			throw new IllegalStateException("Error:", ex);
		}
	}
}
