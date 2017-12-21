package com.uttara.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class StartGameServlet
 */
public class StartGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	public static final String USER = "user";
	public static final String GENVAL = "genVal";
	public static final String NUMATTEMPTS = "numAttempts";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartGameServlet() {
        super();
        System.out.println("in SGS no-arg constr");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in SGS doGet()");
		/*
		 * 1) access user input email
		 * 2) validate
		 * 3) generate random num between 0-9
		 * 4) create session.
		 * 5) store email, genval, num of attempts in session
		 * 6) forward to Guess.html
		 */
		try{
		String email = request.getParameter("email");
		PrintWriter pw = response.getWriter();
		if(email==null || email.trim().equals("") || !email.contains("@"))
			pw.write("<html><body><h1>Error</h1><b>Your email is not valid...you are banished permanently from playing this game...</b></body></html>");
		else
		{
			int val = (int)(Math.random()*1000);
			System.out.println("SGS -> email = "+email+ " val = "+val);
			HttpSession s = request.getSession(true);
		
			s.setAttribute(USER, email);
			s.setAttribute(GENVAL, val);
			s.setAttribute(NUMATTEMPTS, 3);
			
			RequestDispatcher rd = request.getRequestDispatcher("Guess.html");
			rd.forward(request, response);
		}
	}
		catch(Exception e){
			e.printStackTrace();
		}
			
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in SGS doPost();");
	}

}
