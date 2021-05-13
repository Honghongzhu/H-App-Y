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
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    
	    String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
	    
	    //String[] requestParams = requestBody.split(System.lineSeparator());

	    String COL_DELIM = "\"columns\":";
	    String COND_DELIM = "\"condition\":";
	    String TABLE_DELIM = "\"table\":";
	    String WHERE_DELIM = "\"whereStatement\":";
	    
	    
	    String columns = requestBody.substring(requestBody.indexOf(COL_DELIM)+COL_DELIM.length(), requestBody.indexOf(COND_DELIM)-1);
	    String table = requestBody.substring(requestBody.indexOf(TABLE_DELIM)+TABLE_DELIM.length(), requestBody.indexOf(WHERE_DELIM)-1);
	    String whereStatement = requestBody.substring(requestBody.indexOf(WHERE_DELIM)+WHERE_DELIM.length(), requestBody.length()-1);
	    String condition = requestBody.substring(requestBody.indexOf(COND_DELIM)+COND_DELIM.length(), requestBody.indexOf(TABLE_DELIM)-1);
	    
	    
	    try {
	         // Register JDBC driver (not needed anymore)
	         // Class.forName(JDBC_DRIVER);

	         // Open a connection
	         conn = DriverManager.getConnection(DB_URL, USER, PASS);

	         if(whereStatement == "") {
	        	 String sql = "SELECT ? FROM ?";
		         stmt = conn.prepareStatement(sql);
		         stmt.setString(1, columns);
		         stmt.setString(2, table);
	         }else {
		         String sql = "SELECT ? FROM ? ? ?";
		         stmt = conn.prepareStatement(sql);
		         stmt.setString(1, columns);
		         stmt.setString(2, table);
		         stmt.setString(3, whereStatement);
		         stmt.setString(4, condition);
	         }
	         
	         ResultSet rs = stmt.executeQuery();
	         boolean first = true;
	         
	         out.print("[");
	         
	         // Extract data from result set
	         while(rs.next()){
	        	 
	        	if (!first) {
	        		out.print(",");
	        	} else {
	        		first = false;
	        	}
	        	
	        	String strJson = new String();
	        	
	        	switch (table) {
	        		case "movie_id":
	        			//Retrieve by column name
	    	            String movieId = rs.getString("movie_id");
	    	            String primaryTitle = rs.getString("primary_title");
	    	            String originalTitle = rs.getString("original_title");
	    	            String startYear = rs.getString("start_year");
	    	            String runtime = rs.getString("runtime");
	    	            String genres = rs.getString("genres");
	    	            String posterUrl = rs.getString("poster_url");

	    	            strJson = String.format(
	    	            		"{ \"movieId\": \"%s\","
	    	            		+ " \"primaryTitle\": \"%s\","
	    	            		+ " \"originalTitle\": \"%s\","
	    	            		+ " \"startYear\": \"%s\","
	    	            		+ " \"runtime\": \"%s\","
	    	            		+ " \"genres\": \"%s\","
	    	            		+ " \"posterUrl\": \"%s\""
	    	            		+ " }", 
	    	            		movieId, 
	    	            		primaryTitle,
	    	            		originalTitle,
	    	            		startYear,
	    	            		runtime,
	    	            		genres,
	    	            		posterUrl
    	            		);
	        		default:
	        			strJson = "Error";
	        	}
	            
	            //Display values
	            out.print(strJson);
	         }
	         
	         out.print("]");

	         // Clean-up environment
	         rs.close();
	         stmt.close();
	         conn.close();
	      } catch(SQLException se) {
	         //Handle errors for JDBC
	         se.printStackTrace();
	      } catch(Exception e) {
	         //Handle errors for Class.forName
	         e.printStackTrace();
	      } finally {
	         //finally block used to close resources
	         try {
	            if(stmt!=null)
	               stmt.close();
	         } catch(SQLException se2) {
	         } // nothing we can do
	         try {
	            if(conn!=null)
	            conn.close();
	         } catch(SQLException se) {
	            se.printStackTrace();
	         } //end finally try
	      } //end try
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}