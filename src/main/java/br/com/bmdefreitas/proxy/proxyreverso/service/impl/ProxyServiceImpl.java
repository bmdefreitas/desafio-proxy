package br.com.bmdefreitas.proxy.proxyreverso.service.impl;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bmdefreitas.proxy.proxyreverso.service.ICertificateService;
import br.com.bmdefreitas.proxy.proxyreverso.service.IProxyService;

@Service
public class ProxyServiceImpl implements IProxyService {

	@Autowired
	private ICertificateService certificateService;
	
	@Override
	public void checkRequest(URL url, HttpServletResponse response) throws IOException {
		certificateService.checkCertificates(url, response);
		
	}

}
