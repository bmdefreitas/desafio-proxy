package br.com.bmdefreitas.proxy.proxyreverso.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
  name = "ProxyController",
  description = "Controller principal da aplicação",
  urlPatterns = {""}
)
public class ProxyController extends HttpServlet {  
	
	private static final long serialVersionUID = -1030942587591528067L;

	@Override
    protected void doGet(
      HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException {
  
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<p>Controller Principal!</p>");
    }
}