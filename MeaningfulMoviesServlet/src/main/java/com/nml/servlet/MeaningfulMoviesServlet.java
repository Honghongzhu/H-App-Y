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
    
    public String getColumnContent(ResultSet rs, String columns, String column) throws SQLException {
    	String result = null;
    	if(columns.contains(column) || columns.equals("*")) {
    		result = rs.getString(column); 
    	} else {
    		result = "$";
    	}
    	return result;
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
	    
	    String STATE1_DELIM = "\"firstStatement\":";
	    String COL_DELIM = "\"columns\":";
	    String TABLE_DELIM = "\"table\":";
	    String STATE2_DELIM = "\"secondStatement\":";
	    String COND_DELIM = "\"condition\":";
	    
	    // I DO THIS BECAUSE WE CANNOT USE GSON PACKAGE AS IT IS NOT PRESENT IN RUNTIME (FRANK'S TOMCAT)  
	    String firstStatement = requestBody.substring(requestBody.indexOf(STATE1_DELIM)+STATE1_DELIM.length(), requestBody.indexOf(COL_DELIM)-2).replace("\"", "");
	    String columns = requestBody.substring(requestBody.indexOf(COL_DELIM)+COL_DELIM.length(), requestBody.indexOf(TABLE_DELIM)-2).replace("\"", "");
	    String table = requestBody.substring(requestBody.indexOf(TABLE_DELIM)+TABLE_DELIM.length(), requestBody.indexOf(STATE2_DELIM)-2).replace("\"", "");
	    String secondStatement = requestBody.substring(requestBody.indexOf(STATE2_DELIM)+STATE2_DELIM.length(), requestBody.indexOf(COND_DELIM)-2).replace("\"", "");
	    String condition = requestBody.substring(requestBody.indexOf(COND_DELIM)+COND_DELIM.length(), requestBody.length()-1).replace("\"", "");   
	    
	    try {
	         // Register JDBC driver
	    	Class.forName("com.mysql.jdbc.Driver");

	         // Open a connection
	         conn = DriverManager.getConnection(DB_URL, USER, PASS);

	         // Set up the expressions for the queries based on the request's body
	         
	         String op = new String();
	         String sql = new String();
	         
	         int OPERATION = 0;
	         firstStatement = firstStatement.toLowerCase().trim();
	         
	         // this assigns a number to OPERATION to further handle cases
	         if (firstStatement.equals("select")) {
	        	 OPERATION = 1;
	         } else if (firstStatement.equals("update")) {
	        	 OPERATION = 2;
	         } else if (firstStatement.equals("insert")) {
	        	 OPERATION = 3;
	         } else if (firstStatement.equals("delete")) {
	        	 OPERATION = 4;
	         }
	         
	         switch(OPERATION) {
	         	case 1: // select
	         		op = "from";
	         		sql = "<firstStatement> <columns> <op> <table> <secondStatement> <condition>";
	         		break;
	         	case 2: // update
	         		op = "set";
		        	sql = "<firstStatement> <table> <op> <columns> <secondStatement> <condition>";
		        	break;
	         	case 3: // insert
	         		op = "into";
		        	sql = "<firstStatement> <op> <table> <columns> <secondStatement> <condition>";
		        	break;
	         	case 4: // delete
	         		op = "from";
		        	sql = "<firstStatement> <op> <table> <secondStatement> <condition> <columns>";
		        	break;
	         	case 0: // error
	         		out.println(String.format("The first statement %s is not valid.", firstStatement));
	         }
	         
	         sql = sql.replace("<firstStatement>", firstStatement);
	         sql = sql.replace("<columns>", columns);
	         sql = sql.replace("<op>", op);
	         sql = sql.replace("<table>", table);
	         sql = sql.replace("<secondStatement>", secondStatement);
	         sql = sql.replace("<condition>", condition);
	         
	         sql = sql.trim();
        	 stmt = conn.createStatement();
        	 
        	 ResultSet rs = null;
        	 int result = 0;
        	 
        	 switch(OPERATION) {
        	 	case 1:
        	 		rs = stmt.executeQuery(sql);
        	 		break;
        	 	case 2:
        	 		result = stmt.executeUpdate(sql);
        	 		break;
        	 	case 3:
        	 		result = stmt.executeUpdate(sql);
        	 		break;
        	 	case 4:
        	 		result = stmt.executeUpdate(sql);
        	 		break;
        	 }
	         
        	 // Now we handle the response from the database
        	 
        	 String output = "";
        	 
        	 switch(OPERATION) {
        		 
        	 	case 1:
        	 
        	 		boolean first = true;
				 
        	 		output += "[";
		         
        	 		String movieId, primaryTitle, originalTitle, startYear, runtime, genres, posterUrl,
		         		votesEnjoyment, averageEnjoyment, votesMeaning, averageMeaning, creativity,
		         		curiosity, judgement, loveOfLearning, perspective, bravery, honesty, zest, 
		         		perseverance, love, kindness, socialIntelligence,teamwork, fairness, leadership,
		         		forgiveness, humility, prudence, selfRegulation, appreciationBeautyExcellence,
		         		gratitude, hope, humor, spirituality, ratingId, userId, enjoymentRating,
		         		meaningRating, androidId, savedId;

        	 		
        	 		// Extract data from result set
        	 		while(rs.next()){
		        	 
			        	if (first) {
			        		first = false;
			        	} else {
			        		output += ",";
			        	}
		        	
			        	if(table.equals("movie_info")) {
		        			
			        		movieId = getColumnContent(rs, columns, "movie_id");
			        		primaryTitle = getColumnContent(rs, columns, "primary_title");
			        		originalTitle = getColumnContent(rs, columns, "original_title");
			        		startYear = getColumnContent(rs, columns, "start_year");
			        		runtime = getColumnContent(rs, columns, "runtime");
			        		genres = getColumnContent(rs, columns, "genres");
			        		posterUrl = getColumnContent(rs, columns, "poster_url");
			
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
		    	           
			        	} else if (table.equals("movie_ratings")) {
		        			
		        			movieId = getColumnContent(rs, columns, "movie_id");
		    	            votesEnjoyment = getColumnContent(rs, columns, "votes_enjoyment");
		    	            averageEnjoyment = getColumnContent(rs, columns, "average_enjoyment");
		    	            votesMeaning = getColumnContent(rs, columns, "votes_meaning");
		    	            averageMeaning = getColumnContent(rs, columns, "average_meaning");
		    	            creativity = getColumnContent(rs, columns, "creativity");
		    	            curiosity = getColumnContent(rs, columns, "curiosity");
		    	            judgement = getColumnContent(rs, columns, "judgement");
		    	            loveOfLearning = getColumnContent(rs, columns, "love_of_learning");
		    	            perspective = getColumnContent(rs, columns, "perspective");
		    	            bravery = getColumnContent(rs, columns, "bravery");
		    	            honesty = getColumnContent(rs, columns, "honesty");
		    	            zest = getColumnContent(rs, columns, "zest");
		    	            perseverance = getColumnContent(rs, columns, "perseverance");
		    	            love = getColumnContent(rs, columns, "love");
		    	            kindness = getColumnContent(rs, columns, "kindness");
		    	            socialIntelligence = getColumnContent(rs, columns, "social_intelligence");
		    	            teamwork = getColumnContent(rs, columns, "teamwork");
		    	            fairness = getColumnContent(rs, columns, "fairness");
		    	            leadership = getColumnContent(rs, columns, "leadership");
		    	            forgiveness = getColumnContent(rs, columns, "forgiveness");
		    	            humility = getColumnContent(rs, columns, "humility");
		    	            prudence = getColumnContent(rs, columns, "prudence");
		    	            selfRegulation = getColumnContent(rs, columns, "self_regulation");
		    	            appreciationBeautyExcellence = getColumnContent(rs, columns, "appreciation_beauty_excellence");
		    	            gratitude = getColumnContent(rs, columns, "gratitude");
		    	            hope = getColumnContent(rs, columns, "hope");
		    	            humor = getColumnContent(rs, columns, "humor");
		    	            spirituality = getColumnContent(rs, columns, "spirituality");
		    	            
		    	            output += String.format(
		    	            		"{ movieId: %s,"
		    	            		+ " votesEnjoyment: %s,"
		    	            		+ " averageEnjoyment: %s,"
		    	            		+ " votesMeaning: %s,"
		    	            		+ " averageMeaning: %s,"
		    	            		+ " creativity: %s,"
		    	            		+ " curiosity: %s,"
		    	            		+ " judgement: %s,"
		    	            		+ " loveOfLearning: %s,"
		    	            		+ " perspective: %s,"
		    	            		+ " bravery: %s,"
		    	            		+ " honesty: %s,"
		    	            		+ " zest: %s,"
		    	            		+ " perseverance: %s,"
		    	            		+ " love: %s,"
		    	            		+ " kindness: %s,"
		    	            		+ " socialIntelligence: %s,"
		    	            		+ " teamwork: %s,"
		    	            		+ " fairness: %s,"
		    	            		+ " leadership: %s,"
		    	            		+ " forgiveness: %s,"
		    	            		+ " humility: %s,"
		    	            		+ " prudence: %s,"
		    	            		+ " selfRegulation: %s,"
		    	            		+ " appreciationBeautyExcellence: %s,"
		    	            		+ " gratitude: %s,"
		    	            		+ " hope: %s,"
		    	            		+ " humor: %s,"
		    	            		+ " spirituality: %s"
		    	            		+ " }", 
		    	            		movieId, 
		    	            		votesEnjoyment,
		    	            		averageEnjoyment,
		    	            		votesMeaning,
		    	            		averageMeaning,
		    	            		creativity,
		    	            		curiosity,
		    	            		judgement,
		    	            		loveOfLearning,
		    	            		perspective,
		    	            		bravery,
		    	            		honesty,
		    	            		zest,
		    	            		perseverance,
		    	            		love,
		    	            		kindness,
		    	            		socialIntelligence,
		    	            		teamwork,
		    	            		fairness,
		    	            		leadership,
		    	            		forgiveness,
		    	            		humility,
		    	            		prudence,
		    	            		selfRegulation,
		    	            		appreciationBeautyExcellence,
		    	            		gratitude,
		    	            		hope,
		    	            		humor,
		    	            		spirituality
		    	            		);
		        		
			        	} else if (table.equals("user_ratings")) {
		        			
		        			ratingId = getColumnContent(rs, columns, "rating_id");
		    	            userId = getColumnContent(rs, columns, "user_id");
		    	            movieId = getColumnContent(rs, columns, "movie_id");
		    	            enjoymentRating = getColumnContent(rs, columns, "enjoyment_rating");
		    	            meaningRating = getColumnContent(rs, columns, "meaning_rating");
		    	            creativity = getColumnContent(rs, columns, "creativity");
		    	            curiosity = getColumnContent(rs, columns, "curiosity");
		    	            judgement = getColumnContent(rs, columns, "judgement");
		    	            loveOfLearning = getColumnContent(rs, columns, "love_of_learning");
		    	            perspective = getColumnContent(rs, columns, "perspective");
		    	            bravery = getColumnContent(rs, columns, "bravery");
		    	            honesty = getColumnContent(rs, columns, "honesty");
		    	            zest = getColumnContent(rs, columns, "zest");
		    	            perseverance = getColumnContent(rs, columns, "perseverance");
		    	            love = getColumnContent(rs, columns, "love");
		    	            kindness = getColumnContent(rs, columns, "kindness");
		    	            socialIntelligence = getColumnContent(rs, columns, "social_intelligence");
		    	            teamwork = getColumnContent(rs, columns, "teamwork");
		    	            fairness = getColumnContent(rs, columns, "fairness");
		    	            leadership = getColumnContent(rs, columns, "leadership");
		    	            forgiveness = getColumnContent(rs, columns, "forgiveness");
		    	            humility = getColumnContent(rs, columns, "humility");
		    	            prudence = getColumnContent(rs, columns, "prudence");
		    	            selfRegulation = getColumnContent(rs, columns, "self_regulation");
		    	            appreciationBeautyExcellence = getColumnContent(rs, columns, "appreciation_beauty_excellence");
		    	            gratitude = getColumnContent(rs, columns, "gratitude");
		    	            hope = getColumnContent(rs, columns, "hope");
		    	            humor = getColumnContent(rs, columns, "humor");
		    	            spirituality = getColumnContent(rs, columns, "spirituality");
		    	            
		    	            output += String.format(
		    	            		"{ ratingId: %s,"
		    	            		+ " userId: %s,"
		    	            		+ " movieId: %s,"
		    	            		+ " enjoymentRating: %s,"
		    	            		+ " meaningRating: %s,"
		    	            		+ " creativity: %s,"
		    	            		+ " curiosity: %s,"
		    	            		+ " judgement: %s,"
		    	            		+ " loveOfLearning: %s,"
		    	            		+ " perspective: %s,"
		    	            		+ " bravery: %s,"
		    	            		+ " honesty: %s,"
		    	            		+ " zest: %s,"
		    	            		+ " perseverance: %s,"
		    	            		+ " love: %s,"
		    	            		+ " kindness: %s,"
		    	            		+ " socialIntelligence: %s,"
		    	            		+ " teamwork: %s,"
		    	            		+ " fairness: %s,"
		    	            		+ " leadership: %s,"
		    	            		+ " forgiveness: %s,"
		    	            		+ " humility: %s,"
		    	            		+ " prudence: %s,"
		    	            		+ " selfRegulation: %s,"
		    	            		+ " appreciationBeautyExcellence: %s,"
		    	            		+ " gratitude: %s,"
		    	            		+ " hope: %s,"
		    	            		+ " humor: %s,"
		    	            		+ " spirituality: %s"
		    	            		+ " }", 
		    	            		ratingId, 
		    	            		userId,
		    	            		movieId,
		    	            		enjoymentRating,
		    	            		meaningRating,
		    	            		creativity,
		    	            		curiosity,
		    	            		judgement,
		    	            		loveOfLearning,
		    	            		perspective,
		    	            		bravery,
		    	            		honesty,
		    	            		zest,
		    	            		perseverance,
		    	            		love,
		    	            		kindness,
		    	            		socialIntelligence,
		    	            		teamwork,
		    	            		fairness,
		    	            		leadership,
		    	            		forgiveness,
		    	            		humility,
		    	            		prudence,
		    	            		selfRegulation,
		    	            		appreciationBeautyExcellence,
		    	            		gratitude,
		    	            		hope,
		    	            		humor,
		    	            		spirituality
		    	            		);
		        			
			        	} else if (table.equals("users")) {
		        		
		        			userId = getColumnContent(rs, columns, "user_id");
		        			androidId = getColumnContent(rs, columns, "android_id");
		    	            
		    	            output += String.format(
		    	            		"{ userId: %s,"
		    	            		+ " androidId: \"%s\""
		    	            		+ " }", 
		    	            		userId,
		    	            		androidId
		    	            		);
		    	            
			        	} else if (table.equals("saved_movies")) {
			        		
			        		savedId = getColumnContent(rs, columns, "saved_id");
			        		userId = getColumnContent(rs, columns, "user_id");
			        		movieId = getColumnContent(rs, columns, "movie_id");
			        		
			        		output += String.format(
		    	            		"{ savedId: %s,"
		    	            		+ " userId: %s,"
		    	            		+ " movieId: \"%s\""
		    	            		+ " }", 
		    	            		savedId,
		    	            		userId,
		    	            		movieId
		    	            		);
			        		
			        	}
			        } //end while
		         
			        output += "]";
			        break;
		         
        	 	case 2:
        	 		output += String.format("[{ result: \"%s rows were modified by UPDATE statement\"}]", result);
        	 		break;
        	 		
        	 	case 3:
        	 		output +=String.format("[{ result: \"%s rows were modified by INSERT statement\"}]", result);
        	 		break;
        	 	
        	 	case 4:
        	 		output +=String.format("[{ result: \"%s rows were modified by DELETE statement\"}]", result);
        	 		break;
		         
        	 } // end switch

        	 out.print(output);
        	 
	         // Clean-up environment
        	 if (rs != null) {
        		 rs.close();
        	 }
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