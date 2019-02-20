package library;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class ApacheRestart {

	// private final String USER_AGENT = "Mozilla/5.0";
	String Bearertoken;

	public static void serverRestart() throws Exception {
		ApacheRestart http = new ApacheRestart();
		System.out.println("\n Performing Apache Restart request ");
		http.getBearerToken();
		http.stopServer();
		TimeUnit.MINUTES.sleep(2);
		http.startServer();
		TimeUnit.MINUTES.sleep(2);
	}

	// HTTP POST request to get Bearer token
	//@Parameters({"author","searchKey"}) (Eg : Parameters which will be read from testng.xml)
	private void getBearerToken() throws Exception {
		String url = "https://login.microsoftonline.com/710c3e08-c6d7-437f-8452-a7e3ed2133b8/oauth2/token";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
		urlParameters.add(new BasicNameValuePair("client_id", "771af916-5f51-4eb0-af52-556a5f78e0f0"));
		urlParameters.add(new BasicNameValuePair("client_secret", "3uH+FJ9axQtUHpabuVzgYrKnwOgEZY1LLxb88SkgOPU="));
		urlParameters.add(new BasicNameValuePair("resource", "https://management.azure.com/"));
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		HttpResponse response = client.execute(post);
		System.out.println("\n Request to get BearerToken  ");
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		Bearertoken = result.substring(result.lastIndexOf(":") + 1);
		Bearertoken = Bearertoken.substring(1, Bearertoken.length() - 2);
		Bearertoken = "Bearer " + Bearertoken;
	}

	// HTTP POST request to stop the Apache server
	private void stopServer() throws Exception {
		String url = "https://management.azure.com/subscriptions/08b67d18-8f1a-4e79-a7a8-ac09b757fea5/resourceGroups/platform/providers/Microsoft.Web/sites/newline-portal/slots/qa/stop?api-version=2015-08-01";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		post.setHeader("Authorization", Bearertoken);
		HttpResponse response = client.execute(post);
		System.out.println("\n Request to stop the server ");
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
	}

	// HTTP POST request to start the Apache server
	private void startServer() throws Exception {
		String url = "https://management.azure.com/subscriptions/08b67d18-8f1a-4e79-a7a8-ac09b757fea5/resourceGroups/platform/providers/Microsoft.Web/sites/newline-portal/slots/qa/start?api-version=2015-08-01";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		post.setHeader("Authorization", Bearertoken);
		HttpResponse response = client.execute(post);
		System.out.println("\nRequest to start the server  ");
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
	}
}