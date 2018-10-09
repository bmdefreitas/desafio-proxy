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
	
	/**
	 * Adiciona o Connector configurado no Tomcat Embedded do SpringBoot
	 * <p>
	 * Este método irá retonar o Servlet Web Server Factory e adicionar
	 * o Connector configurado.
	 *
	 * @return  ServletWebServerFactory
	 */
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector());
		return tomcat;
	}
	
	/**
	 * Retorna Connector SSL para com as configurações necessárias para o 
	 * Tomcat Embedded  
	 * <p>
	 * Este método irá retornar o Connector para configurações do Tomcat Embedded do
	 * SpringBoot.
	 *
	 * @return  Connector para o Tomcat Embedded
	 */
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
			
			connector.addSslHostConfig(getSSLHostConfig("test1.localdomain"));
			
			connector.addSslHostConfig(getSSLHostConfig("test2.localdomain"));
			
			return connector;
		}
		catch (Exception ex) {
			throw new IllegalStateException("Error:", ex);
		}
	}

	/**
	 * Retorna SSLHostConfig para configuração do Tomcat Embedded
	 * a partir de um certo FQDN 
	 * <p>
	 * Este método irá apoiar nas configurações do Tomcat Embedded do
	 * SpringBoot. A partir de um FQDN será retornado todas configurações
	 * necessárias para disponibilizar o certificado correspondente do host.
	 *
	 * @param  hostname  um nome para ser configurado no Tomcat Embedded
	 * @return  SSLHostConfig para o Tomcat Embedded
	 */
	private SSLHostConfig getSSLHostConfig(String hostname) {
		SSLHostConfig sslHostConfig = new SSLHostConfig();	
		sslHostConfig.setHostName(hostname);
		
		SSLHostConfigCertificate certificate = new SSLHostConfigCertificate(sslHostConfig, SSLHostConfigCertificate.DEFAULT_TYPE);		
		certificate.setCertificateKeystoreFile("/opt/keystore.p12");
		certificate.setCertificateKeystorePassword("123456");
		certificate.setCertificateKeyAlias("test1.localdomain");		
		certificate.setCertificateKeystoreType("PKCS12");
		
		sslHostConfig.addCertificate(certificate);
		
		return sslHostConfig;
	}
}
