package asw01cs;


import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
//This code uses the Fluent API

public class SimpleFluentClient {

	private static String URI = "http://localhost:8080/waslab01_ss/";

	public final static void main(String[] args) throws Exception {
    	
    	/* Insert code for Task #4 here */
    	String idR = Request.Post(URI)
    	.bodyForm(Form.form().add("author",  "Joana").add("tweet_text", "Hola! Soc producte de la tasca 4.").build())
    	.addHeader("Accept", "text/plain")
    	.execute().returnContent().asString();
    	
    	System.out.println(Request.Get(URI).addHeader("Accept", "text/plain").execute().returnContent());
    	
    	/* Insert code for Task #5 here */
    	Request.Post(URI).bodyForm(Form.form().add("id", idR).build())
    	.addHeader("Accept", "Delete").execute();
    }
}

