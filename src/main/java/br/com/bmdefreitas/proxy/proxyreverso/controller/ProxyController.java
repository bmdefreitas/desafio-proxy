package br.com.bmdefreitas.proxy.proxyreverso.controller;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bmdefreitas.proxy.proxyreverso.service.IProxyService;

/**
 * Classe controladora
 *
 *
 * @author  Bruno Medeiros
 */

@WebServlet(
  name = "ProxyController",
  description = "Controller principal da aplicação",
  urlPatterns = {""}
)
public class ProxyController extends HttpServlet {  
	
	private static final long serialVersionUID = -1030942587591528067L;
	
	@Autowired
	private IProxyService proxyService;
	
	/**
	 * Este método trata requisições e respostas do tipo GET.
	 *
	 * @param  request  HttpServletRequest
	 * @param  response HttpServletResponse
	 */
	@Override
    protected void doGet(
      HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException {
		String bodyResponse = proxyService.checkRequest(new URL(request.getRequestURL().toString()));
		if ( StringUtils.isBlank(bodyResponse) ) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);			
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().print(bodyResponse);
		}
    }
	
	/**
	 * Este método trata requisições e respostas do tipo POST.
	 *
	 * @param  request  HttpServletRequest
	 * @param  response HttpServletResponse
	 */
	@Override
    protected void doPost(
      HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException {
		//TODO
    }
	
	/**
	 * Este método trata requisições e respostas do tipo PUT.
	 *
	 * @param  request  HttpServletRequest
	 * @param  response HttpServletResponse
	 */
	@Override
    protected void doPut(
      HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException {
		//TODO
    }
	
	/**
	 * Este método trata requisições e respostas do tipo DELETE.
	 *
	 * @param  request  HttpServletRequest
	 * @param  response HttpServletResponse
	 */
	@Override
    protected void doDelete(
      HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException {
		//TODO
    }
	
	/**
	 * Este método trata requisições e respostas do tipo OPTIONS.
	 *
	 * @param  request  HttpServletRequest
	 * @param  response HttpServletResponse
	 */
	@Override
    protected void doOptions(
      HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException {
		//TODO
    }
	
	/**
	 * Este método trata requisições e respostas do tipo HEAD.
	 *
	 * @param  request  HttpServletRequest
	 * @param  response HttpServletResponse
	 */
	@Override
    protected void doHead(
      HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException {
		//TODO
    }
	
	/**
	 * Este método trata requisições e respostas do tipo TRACE.
	 *
	 * @param  request  HttpServletRequest
	 * @param  response HttpServletResponse
	 */
	@Override
    protected void doTrace(
      HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException {
		//TODO
    }
	
	
}