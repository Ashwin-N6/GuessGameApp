package com.uttara.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GuessServlet
 */
public class GuessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in doGet() of GS");
		
		/*
		 * 1) get the user input num
		 * 2) validate
		 * 3) get the session (existing)
		 * 4) check num of attempts
		 * 5) check the guessedval with genval
		 * 6) show msg to user
		 */
		
		String num = request.getParameter("num");
		PrintWriter pw = response.getWriter();
		try
		{
			int guessVal = Integer.parseInt(num);
			
			HttpSession s = request.getSession(false);
			if(s==null)
			{
				pw.write("<html><body><h1>Error</h1><b>Who are you? Goto <a href='HomePage.html'>Home Page</a> to start the game!</b></body></html>");
			}
			else
			{
				String email = (String) s.getAttribute(StartGameServlet.USER);
				int numAttempts = (Integer)s.getAttribute(StartGameServlet.NUMATTEMPTS);
				int genVal = (Integer)s.getAttribute(StartGameServlet.GENVAL);
				
				if(numAttempts < 1)
				{
					pw.write("<html><body><h1>Error</h1><b>You have used all your attempts. Goto <a href='HomePage.html'>Home Page</a> to start the game!</b></body></html>");
				}
				else
				{
					if(guessVal== genVal)
					{
						pw.write("<html><body><h1>Success</h1><b>Hooray, you are right!! Goto <a href='HomePage.html'>Home Page</a> to start the game again!</b></body></html>");
						
						ServletContext sc = getServletContext();
						if(sc.getAttribute("pointsMap")==null)
						{
							Map<String,Integer> points = new HashMap<String,Integer>();
							points.put(email,5);
							
							sc.setAttribute("pointsMap", points);
						}
						else
						{
							Map<String,Integer> points = (Map<String, Integer>) sc.getAttribute("pointsMap");
							if(points.get(email)==null)
								points.put(email, 5);
							else
							{
								points.put(email, points.get(email)+5);
								
							}
						}
						
						s.removeAttribute(StartGameServlet.USER);
						s.removeAttribute(StartGameServlet.NUMATTEMPTS);
						s.removeAttribute(StartGameServlet.GENVAL);
						
						s.invalidate();
						
					}
					else
					{
						--numAttempts;
						s.setAttribute(StartGameServlet.NUMATTEMPTS, numAttempts);
						if(guessVal < genVal)
							pw.write("<html><body><h1>Message</h1><b>Wrong Guess...Psst...Guess higher</b></body></html>");
						else
							pw.write("<html><body><h1>Message</h1><b>Wrong Guess...Psst...Guess lower</b></body></html>");
					}
				}
			}
			
		}
		catch(NumberFormatException e)
		{
			pw.write("<html><body><h1>Error</h1><b>Your guessed number is not valid...you are banished permanently from playing this game...</b></body></html>");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
