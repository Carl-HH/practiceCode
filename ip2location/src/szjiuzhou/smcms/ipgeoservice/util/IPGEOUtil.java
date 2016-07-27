package szjiuzhou.smcms.ipgeoservice.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * ip定位服务类.
 * @author chen.hua
 * @since
 * @param
 */
public class IPGEOUtil {
  /**
 * 获取ip所在地理位置.
 * @author chen.hua
 * @create 2015年11月10日 上午11:46:31
 * @since
 * @param ip
 * @return String
 */
public static final String getLocation(String ip) throws Exception {
      String result = "";

	  CloseableHttpClient httpclient = HttpClients.createDefault();
      try {
    	  URI uri = new URIBuilder()
    	  .setScheme("http")
    	  .setHost("api.ip2location.com")
    	  .setParameter("ip", ip)
    	  .setParameter("key", "demo")
    	  .setParameter("package", "WS5")
    	  .setParameter("format", "json")
    	  .build();
          HttpGet httpget = new HttpGet(uri);

          System.out.println("Executing request " + httpget.getRequestLine());

          // Create a custom response handler
          ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

              @Override
              public String handleResponse(
                      final HttpResponse response) throws ClientProtocolException, IOException {
                  int status = response.getStatusLine().getStatusCode();
                  if (status >= 200 && status < 300) {
                      HttpEntity entity = response.getEntity();
                      return entity != null ? EntityUtils.toString(entity) : null;
                  } else {
                      throw new ClientProtocolException("Unexpected response status: " + status);
                  }
              }
          };
          String responseBody = httpclient.execute(httpget, responseHandler);
          result = responseBody;
          System.out.println("----------------------------------------");
          System.out.println(responseBody);
      } finally {
          httpclient.close();
      }
  return result;
  }

	public static String setListToJson(ArrayList<Hashtable<String, String>> locationList){
		StringBuffer sb = new StringBuffer();

			sb.append("{\"country_code\":\"" + locationList.get(0).get("countrycode")
					+ "\",\"country_name\":\"" + locationList.get(0).get("country")
					+ "\",\"region_name\":\""
					+ locationList.get(0).get("region")
					+ "\",\"city_name\":\"" + locationList.get(0).get("city")
					+ "\",\"latitude\":\"" + locationList.get(0).get("latitude")
					+ "\",\"longitude\":\"" + locationList.get(0).get("longitude")
					+ "\"}");
			System.out.println("listtojson:" + sb.toString());
			return sb.toString();
		}
}
