package org.jfcutils.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

/**
 * Client for RESTful web services. Allows to send POST, GET, PUT, and DELETE requests to a web service.
 * In case of POST and PUT, a payload and the content-type are required. 
 * In case of authentication, password is encoded Base64. Authentication is optional, thus username and password can be null.
 * 
 * <br>
 * Example of usage of POST: 
 * <pre>
 * {@code
 * RESTClient client = new RESTClient();
 * String json = "{["example1", "example2"]}";
 * String result = client.send_request("http://localhost:8080/myWebService/method", json, "application/json", "POST", null, null);
 * }
 * </pre>
 * 
 * Example of usage of DELETE: 
 * <pre>
 * {@code
 * RESTClient client = new RESTClient();
 * client.send_request(urlSec+"?ID=12", "DELETE", username, password);
 * }
 * </pre>
 * 
 * Example of usage of GET: 
 * <pre>
 * {@code
 * RESTClient client = new RESTClient();
 * client.send_request(urlSec+"?username=fabrizio.celli", "GET", username, password);
 * }
 * </pre>
 * 
 * @author Fabrizio
 *
 */
public class RESTClient {
	
	private static final String _errorMsg = "ERROR";

	/**
	 * Method to send a POST or PUT request to a RESTful web service. Returns the response. In case of authentication, password is encoded Base64.
	 * @param url the URL of the web service
	 * @param payload the content of this request
	 * @param contentType the content type of the payload (e.g. application/json)
	 * @param method POST or PUT
	 * @param username (optional) in case of authentication, can be null
	 * @param password (optional) in case of authentication, can be null. This password is encoded using Base64.
	 * @throws IOException IOException
	 * @return the response
	 */
	public String send_request(String url, String payload, String contentType, String method, String username, String password) throws IOException{
		//createAccount
		HttpURLConnection conn = get_connection(url, method, username, password);
		conn.setRequestProperty("Content-Type", contentType);
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.writeBytes(payload);
		out.flush();
		return get_response(conn);
	}

	/**
	 * Method to send a GET or DELETE request to a RESTful web service. Returns the response. 
	 * In case of authentication, password is encoded Base64.
	 * @param url the URL of the web service
	 * @param method GET or DELETE
	 * @param username (optional) in case of authentication, can be null
	 * @param password (optional) in case of authentication, can be null. This password is encoded using Base64.
	 * @throws IOException IOException
	 * @return the response
	 */
	public String send_request(String url, String method, String username, String password) throws IOException{
		//createAccount
		HttpURLConnection conn = get_connection(url, method, username, password);
		//conn.addRequestProperty("accept", "text/plain");
		conn.connect();
		return get_response(conn);
	}

	/*
	 * Build the connection
	 */
	private HttpURLConnection get_connection(String url_string, String verb, String username, String password){
		HttpURLConnection conn = null;
		try {
			URL url = new URL(url_string);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(verb);
			conn.setDoInput(true);
			//check if it is a DELETE
			if(!verb.equalsIgnoreCase("DELETE"))
				conn.setDoOutput(true);
			//authentication
			if(username!=null && password!=null)
				addAuthentication(conn, username, password);
		} catch(IOException e){
			System.err.println(e);
		}
		return conn;
	}

	/*
	 * Add Base64 password authentication to the connection
	 */
	private void addAuthentication(HttpURLConnection conn, String username, String password){
		String userpass = username + ":" + password;
		String basicAuth = "Basic " + new String(Base64.encodeBase64(userpass.getBytes()));
		conn.setRequestProperty ("Authorization", basicAuth);
	}

	/*
	 * Access and return the response
	 */
	private String get_response(HttpURLConnection conn){
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String next = null;
			String res = "";
			while ((next = reader.readLine())!=null)
				res += next;
			return res;
		} catch(IOException e){
			System.err.println(e);
		}
		return RESTClient._errorMsg;
	}

}
