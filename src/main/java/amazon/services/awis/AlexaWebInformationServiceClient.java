package amazon.services.awis;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

import com.amazonaws.auth.AWSCredentials;


public class AlexaWebInformationServiceClient {
    protected final static Logger logger = LoggerFactory.getLogger(AlexaWebInformationServiceClient.class);

    private static final String SERVICE_HOST = "awis.amazonaws.com";
    private static final String AWS_BASE_URL = "http://" + SERVICE_HOST + "/?";
    private static final String HASH_ALGORITHM = "HmacSHA256";

    private static final String DATEFORMAT_AWS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    
    private AWSCredentials credentials;
    
    private Map<String, String> queryParams;
    
    public AlexaWebInformationServiceClient(AWSCredentials credentials) {
        if(credentials == null) {
            throw new IllegalArgumentException("Parameter credentials can not be null.");
        }
        
        this.credentials = credentials; 
        
        queryParams = new TreeMap<>();
        queryParams.put("AWSAccessKeyId", credentials.getAWSAccessKeyId());
        queryParams.put("SignatureVersion", "2");
        queryParams.put("SignatureMethod", HASH_ALGORITHM);
    }
    
    /**
     * Generates a timestamp for use with AWS request signing
     *
     * @param date current date
     * @return timestamp
     */
    protected static String getTimestamp(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT_AWS);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(date);
    }
    
    private static final String ACTION_NAME = "UrlInfo";
    private static final String RESPONSE_GROUP_NAME = "Rank,ContactInfo,LinksInCount";
    
    /**
     * Builds the query string
     */
    protected String buildQueryString(Request request) throws UnsupportedEncodingException {
        String timestamp = getTimestamp(Calendar.getInstance().getTime());

        Map<String, String> queryParams = new TreeMap<String, String>();
        queryParams.put("Action", ACTION_NAME);
        queryParams.put("ResponseGroup", RESPONSE_GROUP_NAME);
        queryParams.put("AWSAccessKeyId", credentials.getAWSAccessKeyId());
        queryParams.put("Timestamp", timestamp);
        queryParams.put("Url", "bryght.com");
        queryParams.put("SignatureVersion", "2");
        queryParams.put("SignatureMethod", HASH_ALGORITHM);
        
//        Map<String, String> queryParams = new TreeMap<String, String>();
//        queryParams.put("Action", request.getAction().name());
//        queryParams.put("ResponseGroup", request.getResponseGroups().stream().map( rg -> rg.name()).reduce(",", String::concat));
//        queryParams.put("AWSAccessKeyId", credentials.getAWSAccessKeyId());
//        queryParams.put("Timestamp", timestamp);
//        if(request instanceof UrlInfoRequest) {
//            UrlInfoRequest req = (UrlInfoRequest) request;
//            queryParams.put("Url", req.getUrl());
//        }
//        queryParams.put("SignatureVersion", "2");
//        queryParams.put("SignatureMethod", HASH_ALGORITHM);

        
        StringBuffer query = new StringBuffer();
        boolean first = true;
        for(String name : queryParams.keySet()) {
            if (first) {
                first = false;
            } else {
                query.append("&");
            }
            query.append(name).append("=").append(URLEncoder.encode(queryParams.get(name), "UTF-8"));
        }

        return query.toString();
    }
    
    /**
     * Computes RFC 2104-compliant HMAC signature.
     *
     * @param data The data to be signed.
     * @return The base64-encoded RFC 2104-compliant HMAC signature.
     * @throws java.security.SignatureException
     *          when signature generation fails
     */
    protected String generateSignature(String query) throws java.security.SignatureException {
        if(credentials == null) {
            throw new IllegalStateException("AWS credentials are not intialized.");
        }
        
        String result = null;
        try {
            String toSign = "GET\n" + SERVICE_HOST + "\n/\n" + query;
            
            // get a hash key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(credentials.getAWSAccessKeyId().getBytes(), HASH_ALGORITHM);

            // get a hasher instance and initialize with the signing key
            Mac mac = Mac.getInstance(HASH_ALGORITHM);
            mac.init(signingKey);

            //compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(toSign.getBytes());

            // base64-encode the hmac
            result = new BASE64Encoder().encode(rawHmac);

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : "  + e.getMessage());
        }
        return result;
    }
    
    /**
     * The UrlInfo action provides information about a website, such as:
     * - how popular the site is
     * - what sites are related
     * - contact information for the owner of the site
     * 
     * @param request 
     * @return
     * @throws SignatureException
     * @throws IOException 
     */
    public UrlInfoResult getUrlInfo(UrlInfoRequest request) throws SignatureException, IOException {
        String query = buildQueryString(request);
        String signature = generateSignature(query);

        String uri = AWS_BASE_URL + query + "&Signature=" + URLEncoder.encode(signature, "UTF-8");

        logger.info("Request Url: {}", uri);
        
        String xmlResponse = makeRequest(uri);
        
        logger.info(xmlResponse);
        
        return null;
    }  
    
    public TrafficHistoryResult getTrafficHistory(TrafficHistoryRequest request) {
        return null;
    }   
    
    public CategoryBrowseResult getCategoryBrowse(CategoryBrowseRequest request) {
        return null;
    }   
    
    public CategoryListingsResult getCategoryListings(CategoryListingsRequest request) {
        return null;
    }  

    public SitesLinkingInResult getSitesLinkingIn(SitesLinkingInRequest request) {
        return null;
    }
    
    /**
     * Makes a request to the specified Url and return the results as a String
     *
     * @param requestUrl url to make request to
     * @return the XML document as a String
     * @throws IOException
     */
    public static String makeRequest(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        InputStream in;
        try {
            in = conn.getInputStream();
        } catch(Exception e) {
            in = conn.getErrorStream();
        }
       
        StringBuffer sb = null;
        if(in != null) {
            // Read the response
            sb = new StringBuffer();
            int c;
            int lastChar = 0;
            while ((c = in.read()) != -1) {
                if (c == '<' && (lastChar == '>'))
                    sb.append('\n');
                sb.append((char) c);
                lastChar = c;
            }
            in.close();
        }
        
        return sb == null ? null : sb.toString();
    }
}
