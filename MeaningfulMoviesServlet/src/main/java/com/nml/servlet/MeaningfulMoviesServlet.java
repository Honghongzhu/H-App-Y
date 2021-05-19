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
		
		//Database URL
	    final String DB_URL="jdbc:mysql://localhost/moviedb";
	    
	    //  Database credentials
	    final String USER = "moviedb";
	    final String PASS = "s4796268";
		
	    // Set response content type
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    Connection conn = null;
	    Statement stmt = null;
	    
	    String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
	    
	    String STATE_DELIM = "\"statement\":";
	    String COL_DELIM = "\"columns\":";
	    String COND_DELIM = "\"condition\":";
	    String TABLE_DELIM = "\"table\":";
	    String WHERE_DELIM = "\"whereStatement\":";
	    
	    // I DO THIS BECAUSE WE CANNOT USE GSON PACKAGE AS IT IS NOT PRESENT IN RUNTIME (FRANK'S TOMCAT)  
	    String statement = requestBody.substring(requestBody.indexOf(STATE_DELIM)+STATE_DELIM.length(), requestBody.indexOf(COL_DELIM)-2).replace("\"", "");
	    String columns = requestBody.substring(requestBody.indexOf(COL_DELIM)+COL_DELIM.length(), requestBody.indexOf(TABLE_DELIM)-2).replace("\"", "");
	    String table = requestBody.substring(requestBody.indexOf(TABLE_DELIM)+TABLE_DELIM.length(), requestBody.indexOf(WHERE_DELIM)-2).replace("\"", "");
	    String whereStatement = requestBody.substring(requestBody.indexOf(WHERE_DELIM)+WHERE_DELIM.length(), requestBody.indexOf(COND_DELIM)-2).replace("\"", "");
	    String condition = requestBody.substring(requestBody.indexOf(COND_DELIM)+COND_DELIM.length(), requestBody.length()-1).replace("\"", "");   
	    
	    try {
	         // Register JDBC driver
	    	Class.forName("com.mysql.jdbc.Driver");

	         // Open a connection
	         conn = DriverManager.getConnection(DB_URL, USER, PASS);
	         
	         String sql = "<statement> <columns> FROM <table> <where> <condition>";
	         
	         sql = sql.replace("<statement>", statement);
	         sql = sql.replace("<columns>", columns);
	         sql = sql.replace("<table>", table);
	         sql = sql.replace("<where>", whereStatement);
	         sql = sql.replace("<condition>", condition);
	         sql = sql.trim();
    	 
        	 stmt = conn.createStatement();
        	 
	         ResultSet rs = stmt.executeQuery(sql);
	         
	         boolean first = true;
	         
	         String output = "";
	         output += "[";
	         
	         // Extract data from result set
	         while(rs.next()){
	        	 
	        	if (first) {
	        		first = false;
	        	} else {
	        		output += ",";
	        	}
	        	
	        	//Retrieve by column name
	            String movieId = rs.getString("movie_id");
	            String primaryTitle = rs.getString("primary_title");
	            String originalTitle = rs.getString("original_title");
	            String startYear = rs.getString("start_year");
	            String runtime = rs.getString("runtime");
	            String genres = rs.getString("genres");
	            String posterUrl = rs.getString("poster_url");

	            output += String.format(
	            		"{ movieId: %s,"
	            		+ " primaryTitle: \"%s\","
	            		+ " originalTitle: \"%s\","
	            		+ " startYear: %s,"
	            		+ " runtime: %s,"
	            		+ " genres: \"%s\","
	            		+ " posterUrl: \"%s\""
	            		+ " }", 
	            		movieId, 
	            		primaryTitle,
	            		originalTitle,
	            		startYear,
	            		runtime,
	            		genres,
	            		posterUrl);
	         }
	         
	         output += "]";
	         
	         out.print(output);

	         // Clean-up environment
	         rs.close();
	         stmt.close();
	         conn.close();         
	      
	      } catch(SQLException se) {
	         //Handle errors for JDBC
	         out.println(se + " FIRST");
	      } catch(Exception e) {
	         //Handle errors for Class.forName
	         out.println(e);
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
	        	 out.println(se + " SECOND");
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