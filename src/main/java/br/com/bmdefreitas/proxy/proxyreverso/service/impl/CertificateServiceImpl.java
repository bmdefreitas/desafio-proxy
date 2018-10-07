package br.com.bmdefreitas.proxy.proxyreverso.service.impl;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.CertificateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bmdefreitas.proxy.proxyreverso.service.ICertificateService;
import br.com.bmdefreitas.proxy.proxyreverso.service.IKeyStoreService;

@Service
public class CertificateServiceImpl implements ICertificateService {
	
	@Autowired
	private IKeyStoreService keyStoreService;
	
	public Certificate[] getCertificatesFromConnection(URL url) throws IOException, KeyManagementException, NoSuchAlgorithmException, CertificateException {
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
		SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
	    ((HttpsURLConnection) urlConnection).setSSLSocketFactory(socketFactory);
	    
	    urlConnection.connect();
		Certificate[] serverCertificate = urlConnection.getServerCertificates();
		if (serverCertificate.length == 0) {
			urlConnection.disconnect();
			throw new CertificateException();
		}
		urlConnection.disconnect();
		
		return serverCertificate;
	}

	@Override
	public String checkCertificates(URL url) throws IOException {
		
		try {		
			
			String bodyResponse = null;
			
			X509Certificate cert = keyStoreService.containsCertificatesInKeystore(getCertificatesFromConnection(url));

			if (cert != null) {
				bodyResponse = "";
				if (cert.getSubjectDN().getName().contains("test1.localdomain")) {
					URL url2 = new URL("http://10.0.0.11:8000/");
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream(), "UTF-8"))) {
					    for (String line; (line = reader.readLine()) != null;) {
					    	bodyResponse = bodyResponse + line;
					    }
						
					}					
				} else if (cert.getSubjectDN().getName().contains("test2.localdomain")) {
					URL url2 = new URL("http://10.0.0.2:8000/");
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream(), "UTF-8"))) {
					    for (String line; (line = reader.readLine()) != null;) {
					    	bodyResponse = bodyResponse + line;
					    }
						
					}
				}								
			}
			
			return bodyResponse;			

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}		
		
	}

}
