package br.com.bmdefreitas.proxy.proxyreverso.service;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

public interface IProxyService {

	public void checkRequest(URL url, HttpServletResponse response) throws IOException;
}
