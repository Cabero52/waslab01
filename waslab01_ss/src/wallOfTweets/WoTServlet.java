package wallOfTweets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WoTServlet
 */
@WebServlet("/")
public class WoTServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Locale currentLocale = new Locale("en");
	String ENCODING = "ISO-8859-1";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WoTServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Vector<Tweet> tweets = Database.getTweets();
			printHTMLresult(tweets, request, response);
		}

		catch (SQLException ex ) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// This method does NOTHING but redirect to the main page

		response.sendRedirect(request.getContextPath());
	}

	private void printHTMLresult (Vector<Tweet> tweets, HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, currentLocale);
		DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT, currentLocale);
		res.setContentType ("text/html");
		res.setCharacterEncoding(ENCODING);
		PrintWriter  out = res.getWriter ( );
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head><title>Wall of Tweets</title>");
		out.println("<link href=\"wallstyle.css\" rel=\"stylesheet\" type=\"text/css\" />");
		out.println("</head>");
		out.println("<body class=\"wallbody\">");
		out.println("<h1>Wall of Tweets</h1>");
		out.println("<div class=\"walltweet\">"); 
		out.println("<form method=\"post\">");
		out.println("<table border=0 cellpadding=2>");
		out.println("<tr><td>Your name:</td><td><input name=\"author\" type=\"text\" size=70></td><td></td></tr>");
		out.println("<tr><td>Your tweet:</td><td><textarea name=\"tweet_text\" rows=\"2\" cols=\"70\" wrap></textarea></td>"); 
		out.println("<td><input type=\"submit\" name=\"action\" value=\"Tweet!\"></td></tr>"); 
		out.println("</table></form></div>");
		String currentDate = "None";
		for (Tweet tweet: tweets) {
			String messDate = dateFormatter.format(tweet.getDate());
			if (!currentDate.equals(messDate)) {
				out.println("<br><h3>...... " + messDate + "</h3>");
				currentDate = messDate;
			}
			out.println("<div class=\"wallitem\">");
			out.println("<h4><em>" + tweet.getAuthor() + "</em> @ "+ timeFormatter.format(tweet.getDate()) +"</h4>");
			out.println("<p>" + tweet.getText() + "</p>");
			out.println("</div>");
		}
		out.println ( "</body></html>" );
	}
}
