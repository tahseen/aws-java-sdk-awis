package amazon.services.awis;

import java.io.UnsupportedEncodingException;
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

import sun.misc.BASE64Encoder;

import com.amazonaws.auth.AWSCredentials;


public class AlexaWebInformationServiceClient {
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
    
    /**
     * Builds the query string
     */
    protected String buildQueryString(Request request) throws UnsupportedEncodingException {
        String timestamp = getTimestamp(Calendar.getInstance().getTime());

        Map<String, String> queryParams = new TreeMap<String, String>();
        queryParams.put("Action", request.getAction().name());
        //queryParams.put("ResponseGroup", request.getResponseGroups());
        queryParams.put("AWSAccessKeyId", credentials.getAWSAccessKeyId());
        queryParams.put("Timestamp", timestamp);
        queryParams.put("SignatureVersion", "2");
        queryParams.put("SignatureMethod", HASH_ALGORITHM);

        String query = "";
        boolean first = true;
        for (String name : queryParams.keySet()) {
            if (first)
                first = false;
            else
                query += "&";

            query += name + "=" + URLEncoder.encode(queryParams.get(name), "UTF-8");
        }

        return query;
    }
    
    /**
     * Computes RFC 2104-compliant HMAC signature.
     *
     * @param data The data to be signed.
     * @return The base64-encoded RFC 2104-compliant HMAC signature.
     * @throws java.security.SignatureException
     *          when signature generation fails
     */
    protected String generateSignature(Map<String, String> params) throws java.security.SignatureException {
        if(credentials == null) {
            throw new IllegalStateException("AWS credentials are not intialized.");
        }
        
        String result = null;
        try {
            // get a hash key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(credentials.getAWSAccessKeyId().getBytes(), HASH_ALGORITHM);

            // get a hasher instance and initialize with the signing key
            Mac mac = Mac.getInstance(HASH_ALGORITHM);
            mac.init(signingKey);

            // compute the hmac on input data bytes
            // byte[] rawHmac = mac.doFinal(data.getBytes());

            // base64-encode the hmac
            // result = Encoding.EncodeBase64(rawHmac);
            //result = new BASE64Encoder().encode(rawHmac);

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : "
                    + e.getMessage());
        }
        return result;
    }
    
    public UrlInfoResult getUrlInfo(UrlInfoRequest request) {
        
        
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
}
