package br.com.bmdefreitas.proxy.proxyreverso.controller;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.bmdefreitas.proxy.proxyreverso.service.IProxyService;

@WebServlet(
  name = "ProxyController",
  description = "Controller principal da aplicação",
  urlPatterns = {""}
)
public class ProxyController extends HttpServlet {  
	
	private static final long serialVersionUID = -1030942587591528067L;
	
	@Autowired
	private IProxyService proxyService;

	@Override
    protected void doGet(
      HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException {
		String bodyResponse = proxyService.checkRequest(new URL(request.getRequestURL().toString()));
		if ( !bodyResponse.isEmpty() ) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().print(bodyResponse);
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
    }
}