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
import org.springframework.stereotype.Service;

import br.com.bmdefreitas.proxy.proxyreverso.service.IKeyStoreService;

/**
 * Classe de Implementação do Serviço do Keystore
 *
 *
 * @author  Bruno Medeiros
 */

@Service
public class KeyStoreServiceImpl implements IKeyStoreService{

	/**
	 * Retorna o KeyStore  
	 * <p>
	 * Este método acessa os dados contidos no KeyStore 
	 * 
	 * @return keyStore
	 * @throws IOException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 */
	@Override
	public KeyStore getKeyStore() throws 
		KeyStoreException, 
		NoSuchAlgorithmException, 
		CertificateException, 
		FileNotFoundException, 
		IOException {

		File file = new File("/opt/keystore.p12");
		
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		
		if (file.exists()) {
			keyStore.load(new FileInputStream(file), "123456".toCharArray());
		} else {
			throw new KeyStoreException(); 
		}
		return keyStore;
	}
	
	/**
	 * Retorna o certificado encontrado no KeyStore 
	 * <p>
	 * Este método irá verificar se contém no keystore o certificado 
	 * do host solicitado, caso encontre retornará o certificado contido,
	 * caso contrário será retornado null
	 *
	 * @param certificates lista de certificados a ser procurado no keystore 
	 * @return certificate certificado encontrado X509Certificate
	 * @throws IOException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 */
	public X509Certificate containsCertificatesInKeystore(Certificate[] certificates) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		KeyStore keyStore = getKeyStore();
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
