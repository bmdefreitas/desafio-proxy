package br.com.bmdefreitas.proxy.proxyreverso.service;

import java.io.IOException;
import java.net.URL;

public interface IProxyService {

	public String checkRequest(URL url) throws IOException;
}
