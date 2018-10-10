package br.com.bmdefreitas.proxy.proxyreverso.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.CertificateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bmdefreitas.proxy.proxyreverso.service.IKeyStoreService;
import br.com.bmdefreitas.proxy.proxyreverso.service.IProxyService;

/**
 * Classe de Implementação do Serviço do Proxy
 *
 *
 * @author  Bruno Medeiros
 */

@Service
public class ProxyServiceImpl implements IProxyService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProxyServiceImpl.class);
	
	private final String TEST1 = "test1.localdomain";
	
	private final String TEST2 = "test2.localdomain";

	@Autowired
	private IKeyStoreService keyStoreService;
	
	/**
	 * Retorna uma lista de certificados a partir de uma conexão 
	 * <p>
	 * Este método connecta na URL requisitada e guarda os certificados
	 * da conexão.
	 *
	 * @param url URL requisitada 
	 * @return certificates 
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 */
	private Certificate[] getCertificatesFromConnection(URL url) throws IOException, KeyManagementException, NoSuchAlgorithmException, CertificateException {
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
		SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
	    ((HttpsURLConnection) urlConnection).setSSLSocketFactory(socketFactory);
	    Certificate[] serverCertificate = {};
	    
	    try {
	    	urlConnection.connect();
			serverCertificate = urlConnection.getServerCertificates();
			if (serverCertificate.length == 0) {
				urlConnection.disconnect();
				throw new CertificateException();
			}
			urlConnection.disconnect();
			return serverCertificate;
	    } catch (Exception e) {
	    	logger.error(e.getMessage());
	    	urlConnection.disconnect();
	    	return serverCertificate;
	    }		
	}
	
	/**
	 * Retorna o corpo da resposta tratada. 
	 * <p>
	 * Este método trata a requiscição e retorna uma reposta ao solicitante
	 *
	 * @param url URL a ser tratada 
	 * @return bodyReponse Corpo da resposta tratado
	 * @throws IOException
	 */
	@Override
	public String checkRequest(URL url) throws IOException {
		String bodyResponse = null;
		
		try {
			
			X509Certificate cert = keyStoreService.containsCertificatesInKeystore(getCertificatesFromConnection(url));

			if (cert != null) {
				if (cert.getSubjectDN().getName().contains(TEST1)) {
					bodyResponse = connectBackend(bodyResponse, TEST1);
				} else if (cert.getSubjectDN().getName().contains(TEST2)) {
					bodyResponse = connectBackend(bodyResponse, TEST2);
				}								
			}
			
			return bodyResponse;			

		} catch (Exception e) {
			logger.error(e.getMessage());
			return bodyResponse;
		}
		
	}

	/**
	 * Retorna o corpo da resposta da requisição 
	 * <p>
	 * Este método connecta no backend e retorna o resultado do GET 
	 * realizado para a o corpo da resposta 
	 *
	 * @param bodyResponse Corpo da Resposta 
	 * @param host backend a ser conectado
	 * @return bodyReponse String
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private String connectBackend(String bodyResponse, String host)
			throws MalformedURLException, IOException, UnsupportedEncodingException {
		URL url = new URL(host);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
		    for (String line; (line = reader.readLine()) != null;) {
		    	bodyResponse = bodyResponse + line;
		    }
		    return bodyResponse;			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "O host a ser conectado não está disponível";
		}
		
	}
}
