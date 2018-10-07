package br.com.bmdefreitas.proxy.proxyreverso.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public interface IKeyStoreService {
	
	public KeyStore getKeyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException;
	
	public X509Certificate containsCertificatesInKeystore(Certificate[] certificates) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException;
}
