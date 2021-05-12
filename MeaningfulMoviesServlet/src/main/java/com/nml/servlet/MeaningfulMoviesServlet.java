package com.nml.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Servlet implementation class MeaningfulMoviesServlet
 */
@WebServlet("/MeaningfulMoviesServlet")
public class MeaningfulMoviesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MeaningfulMoviesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// JDBC driver name and database URL
	    // final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	    final String DB_URL="jdbc:mysql://localhost/moviedb";
	    
	    //  Database credentials
	    final String USER = "moviedb";
	    final String PASS = "s4796268";
		
	    // Set response content type
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    
	    Connection conn = null;

	    out.println(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
	    
//	    try {
//	         // Register JDBC driver (not needed anymore)
//	         // Class.forName(JDBC_DRIVER);
//
//	         // Open a connection
//	         conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//	         // Execute SQL query
//	         String sql = "SELECT ? FROM ? ? ?";
//	         
//	         PreparedStatement stmt = conn.prepareStatement(sql);
//	         stmt.setString(1, sql);
//	         
//	         ResultSet rs = stmt.executeQuery(sql);
//
//	         boolean first = true;
//	         
//	         out.print("[");
//	         
//	         // Extract data from result set
//	         while(rs.next()){
//	        	 
//	        	if (!first) {
//	        		out.print(",");
//	        	} else {
//	        		first = false;
//	        	}
//	        	
//	            //Retrieve by column name
//	            String movie_id = rs.getString("movie_id");
//	            String primary_title = rs.getString("primary_title");
//
//	            String strJson = String.format("{ \"movieId\": \"%s\", \"primaryTitle\": \"%s\"}", movie_id, primary_title);
//	            
//	            //Display values
//	            out.print(strJson);
//	         }
//	         
//	         out.print("]");
//
//	         // Clean-up environment
//	         rs.close();
//	         stmt.close();
//	         conn.close();
//	      } catch(SQLException se) {
//	         //Handle errors for JDBC
//	         se.printStackTrace();
//	      } catch(Exception e) {
//	         //Handle errors for Class.forName
//	         e.printStackTrace();
//	      } finally {
//	         //finally block used to close resources
//	         try {
//	            if(stmt!=null)
//	               stmt.close();
//	         } catch(SQLException se2) {
//	         } // nothing we can do
//	         try {
//	            if(conn!=null)
//	            conn.close();
//	         } catch(SQLException se) {
//	            se.printStackTrace();
//	         } //end finally try
//	      } //end try
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}