package br.com.bmdefreitas.proxy.proxyreverso.service;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;

import javax.security.cert.CertificateException;

public interface ICertificateService {
	
	public String checkCertificates(URL url) throws IOException;
	
	public Certificate[] getCertificatesFromConnection(URL url) throws IOException, KeyManagementException, NoSuchAlgorithmException, CertificateException;

}
