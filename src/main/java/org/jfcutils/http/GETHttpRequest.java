package org.jfcutils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.jfcutils.files.read.BufferManager;

/**
 * Makes HTTP requests changing the HTTP header.
 * 
 * <br/>Example of usage: 
 * <pre>
 * {@code
 * GETHttpRequest req = new GETHttpRequest();
 * Map<String, String> header_map = new HashMap<String, String>();
 * header_map.put("accept", "application/sparql-results+xml");
 * System.out.println(req.dereferenceURI("http://agris.fao.org/void.ttl", header_map, 0));
 * }
 * </pre>
 * or
 * <pre>
 * {@code
 * GETHttpRequest req = new GETHttpRequest();
 * Map<String, String> header_map = new HashMap<String, String>();
 * header_map.put("accept", "application/sparql-results+xml");
 * req.printBufferedReader(req.dereferenceURI("http://agris.fao.org/void.ttl", header_map, 0));
 * }
 * </pre>
 * or
 * <pre>
 * {@code
 * GETHttpRequest req = new GETHttpRequest();
 * Map<String, String> header_map = new HashMap<String, String>();
 * header_map.put("accept", "application/sparql-results+xml");
 * System.out.println(req.readBufferedReader(req.dereferenceURIReader("http://agris.fao.org/void.ttl", header_map, 5000)));
 * }
 * </pre>
 * 
 * Makes HTTP requests following HTTP redirect (301, 302, 303).
 * 
 * <br/>Example of usage: 
 * <pre>
 * {@code
 * String url = "http://...";
 * GETHttpRequest req = new GETHttpRequest();
 * System.out.println(req.getUrlContentWithRedirect(url), 0);
 * }
 * </pre>
 *  
 * @author fabrizio celli
 *
 */
public class GETHttpRequest {

	/**
	 * Make a GET HTTP request setting the Mime Type in a header map. 
	 * Return the content as a String
	 * @param uri the URI of a resource
	 * @param header_map map of header options, nullable
	 * @param connectionTimeoutMillis connection timeout in milliseconds, 0 means no timeout
	 * @return the String of the content of the URI dereferencing
	 * @throws IOException
	 */
	public String dereferenceURI(String uri, Map<String, String> header_map, int connectionTimeoutMillis) throws IOException{
		return (new BufferManager()).readBufferedReader(this.dereferenceURIReader(uri, header_map, connectionTimeoutMillis));
	}

	/**
	 * Make a GET HTTP request setting the Mime Type in a header map. 
	 * Return the content as a BufferedReader
	 * @param uri the URI of a resource
	 * @param header_map map of header options, nullable
	 * @param connectionTimeoutMillis connection timeout in milliseconds, 0 means no timeout
	 * @return the BufferReader of the content of the URI dereferencing
	 * @throws IOException
	 */
	public BufferedReader dereferenceURIReader(String uri, Map<String, String> header_map, int connectionTimeoutMillis) throws IOException{
		URL url = new URL(uri);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setRequestMethod("GET");
		urlConn.setConnectTimeout(connectionTimeoutMillis);
		//check options
		if(header_map!=null){
			for(String option: header_map.keySet())
				urlConn.setRequestProperty(option, header_map.get(option));
		}
		return new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
	}

	/**
	 * Extract the content from an HTTP URL with an HTTP redirect (301, 302, 303)
	 * @param url the URL to read 
	 * @param connectionTimeoutMillis connection timeout in milliseconds, 0 means no timeout
	 * @return the content from an HTTP URL with an HTTP redirect (301, 302, 303)
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public String getUrlContentWithRedirect(String url, int connectionTimeoutMillis) throws MalformedURLException, IOException{
		HttpURLConnection conn = this.openConnection(new URL(url), connectionTimeoutMillis);
		boolean redirect = false;
		// normally, 3xx is redirect
		int status = conn.getResponseCode();
		if (status != HttpURLConnection.HTTP_OK) {
			if (status == HttpURLConnection.HTTP_MOVED_TEMP
					|| status == HttpURLConnection.HTTP_MOVED_PERM
					|| status == HttpURLConnection.HTTP_SEE_OTHER)
				redirect = true;
		}

		if (redirect) {
			// get redirect url from "location" header field
			String newUrl = conn.getHeaderField("Location");
			// get the cookie if need, for login
			String cookies = conn.getHeaderField("Set-Cookie");
			// open the new connnection again
			conn = this.openConnection(new URL(newUrl), connectionTimeoutMillis);
			conn.setRequestProperty("Cookie", cookies);
		}

		//read the connection
		return this.getContentFromConnection(conn);
	}

	/*
	 * Open a HttpURLConnection from a URL object
	 */
	private HttpURLConnection openConnection(URL obj, int connectionTimeoutMillis) throws IOException{
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setConnectTimeout(connectionTimeoutMillis);
		conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
		conn.addRequestProperty("User-Agent", "Mozilla");
		conn.addRequestProperty("Referer", "google.com");
		return conn;
	}

	/**
	 * Extract the content from an HttpURLConnection
	 * @param conn the connection object to read
	 * @return the content of the HttpURLConnection
	 * @throws IOException
	 */
	public String getContentFromConnection(HttpURLConnection conn) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer html = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			html.append(inputLine);
		}
		in.close();
		return html.toString();
	}

}
