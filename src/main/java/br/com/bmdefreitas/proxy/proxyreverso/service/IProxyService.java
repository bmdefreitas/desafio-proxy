package br.com.bmdefreitas.proxy.proxyreverso.service;

import java.io.IOException;
import java.net.URL;

/**
 * Interface do Serviço do Proxy
 *
 *
 * @author  Bruno Medeiros
 */

public interface IProxyService {

	
	public String checkRequest(URL url) throws IOException;
}
