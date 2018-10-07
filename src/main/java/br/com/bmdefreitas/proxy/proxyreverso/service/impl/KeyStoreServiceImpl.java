package br.com.bmdefreitas.proxy.proxyreverso.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.cryptacular.util.CertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.com.bmdefreitas.proxy.proxyreverso.service.IKeyStoreService;

@Service
public class KeyStoreServiceImpl implements IKeyStoreService{
	
	private KeyStore keyStore;
	
	@Autowired
    private Environment env;

	@Override
	public KeyStore getKeyStore() throws 
		KeyStoreException, 
		NoSuchAlgorithmException, 
		CertificateException, 
		FileNotFoundException, 
		IOException {

		File file = new File(env.getProperty("keystore-file"));

		keyStore = KeyStore.getInstance(env.getProperty("keystore-type"));
		
		if (file.exists()) {
			keyStore.load(new FileInputStream(file), env.getProperty("keystore-pass").toCharArray());
		} else {
			throw new KeyStoreException(); 
		}
		return keyStore;
	}
	
	public X509Certificate containsCertificatesInKeystore(Certificate[] certificates) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		keyStore = getKeyStore();
		for (Certificate certificate : certificates) {
			if (certificate instanceof X509Certificate) {
				X509Certificate x509cert = (X509Certificate) certificate;
				if (keyStore.containsAlias(CertUtil.subjectCN(x509cert))) {
					return (X509Certificate) keyStore.getCertificate(CertUtil.subjectCN(x509cert));
				}
			}
		}
		return null;
	}

}
