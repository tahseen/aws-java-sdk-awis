package amazon.services.awis;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import amazon.services.awis.generated.CategoryBrowseResponse;
import amazon.services.awis.generated.CategoryListingsResponse;
import amazon.services.awis.generated.SitesLinkingInResponse;
import amazon.services.awis.generated.TrafficHistoryResponse;
import amazon.services.awis.generated.UrlInfoResponse;

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
  
    /**
     * Builds the query string
     */
    protected <T> String buildQueryString(Request<T> request) throws UnsupportedEncodingException {
        String timestamp = getTimestamp(Calendar.getInstance().getTime());
        
        Map<String, String> queryParams = new TreeMap<String, String>();
        queryParams.put("Action", request.getAction().name());
        queryParams.put("ResponseGroup", request.getResponseGroups().stream().map( rg -> rg.toString()).collect(Collectors.joining(",")));
        queryParams.put("AWSAccessKeyId", credentials.getAWSAccessKeyId());
        queryParams.put("Timestamp", timestamp);
        if(request instanceof UrlInfoRequest) {
            UrlInfoRequest req = (UrlInfoRequest) request;
            queryParams.put("Url", req.getUrl());
        } else  if(request instanceof TrafficHistoryRequest) {
        	TrafficHistoryRequest req = (TrafficHistoryRequest) request;
            queryParams.put("Url", req.getUrl());
            if(req.getRange() != null) {
                queryParams.put("Range", req.getRange() + "");
            }   
            if(req.getStart() != null) {
                queryParams.put("Start", req.getStart());
            }
        } else  if(request instanceof CategoryBrowseRequest) {
        	CategoryBrowseRequest req = (CategoryBrowseRequest) request;
            queryParams.put("Path", req.getPath());
            if(req.getDescriptions() != null) {
                queryParams.put("Descriptions", req.getDescriptions() + "");
            }
        } else  if(request instanceof CategoryListingsRequest) {
        	CategoryListingsRequest req = (CategoryListingsRequest) request;
            queryParams.put("Path", req.getPath());
            if(req.getSortBy() != null) {
                queryParams.put("SortBy", req.getSortBy() + "");
            }            
            if(req.getRecursive() != null) {
                queryParams.put("Recursive", req.getRecursive() + "");
            }            
            if(req.getStart() != null) {
                queryParams.put("Start", req.getStart() + "");
            }
            if(req.getCount() != null) {
                queryParams.put("Count", req.getCount() + "");
            }
            if(req.getDescriptions() != null) {
                queryParams.put("Descriptions", req.getDescriptions() + "");
            }
        } else  if(request instanceof SitesLinkingInRequest) {
        	SitesLinkingInRequest req = (SitesLinkingInRequest) request;
            queryParams.put("Url", req.getUrl());
            if(req.getStart() != null) {
                queryParams.put("Start", req.getStart() + "");
            }
            if(req.getCount() != null) {
                queryParams.put("Count", req.getCount() + "");
            }
        } 
        queryParams.put("SignatureVersion", "2");
        queryParams.put("SignatureMethod", HASH_ALGORITHM);

        
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
            SecretKeySpec signingKey = new SecretKeySpec(credentials.getAWSSecretKey().getBytes(), HASH_ALGORITHM);

            // get a hasher instance and initialize with the signing key
            Mac mac = Mac.getInstance(HASH_ALGORITHM);
            mac.init(signingKey);

            //compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(toSign.getBytes());

            // base64-encode the hmac
            result = Base64.getEncoder().encodeToString(rawHmac);

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
     * @throws JAXBException 
     */
    public UrlInfoResponse getUrlInfo(UrlInfoRequest request) throws SignatureException, IOException, JAXBException {
        String xmlResponse = getResponse(request);
        
        JAXBContext jc = JAXBContext.newInstance(UrlInfoResponse.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        UrlInfoResponse response = (UrlInfoResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
        
        return response;
    }  
    
    /**
     * The TrafficHistory action returns the daily Alexa Traffic Rank, Reach per Million Users, 
     * and Unique Page Views per Million Users for each day since August 2007. This same data is used to produce the traffic graphs found on alexa.com.
     * 
     * @param request
     * @return
     * @throws JAXBException
     * @throws IOException
     * @throws SignatureException
     */
    public TrafficHistoryResponse getTrafficHistory(TrafficHistoryRequest request) throws JAXBException, IOException, SignatureException {
        String xmlResponse = getResponse(request);
        
        JAXBContext jc = JAXBContext.newInstance(TrafficHistoryResponse.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        TrafficHistoryResponse response = (TrafficHistoryResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
        
        return response;
    }
    
    /**
     * The CategoryBrowse action and CategoryListings actions together provide a directory service based on the Open Directory, 
     * www.dmoz.org, and enhanced with Alexa traffic data.
     * 
     * For any given category, the CategoryBrowse action returns a list of sub-categories. Within a particular category you can use the 
     * CategoryListings action to get the documents within that category ordered by traffic.
     * 
     * @param request
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     * @throws SignatureException
     * @throws IOException
     */
    public CategoryBrowseResponse getCategoryBrowse(CategoryBrowseRequest request) throws JAXBException, UnsupportedEncodingException, SignatureException, IOException {
        String xmlResponse = getResponse(request);
        
        JAXBContext jc = JAXBContext.newInstance(CategoryBrowseResponse.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        CategoryBrowseResponse response = (CategoryBrowseResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
        
        return response;
    }   
    
    /***
     * The CategoryListings action is a directory service based on the Open Directory, www.dmoz.org. 
     * For any given category, it returns a list of site listings contained within that category.
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws SignatureException
     * @throws IOException
     * @throws JAXBException
     */
    public CategoryListingsResponse getCategoryListings(CategoryListingsRequest request) throws UnsupportedEncodingException, SignatureException, IOException, JAXBException {
        String xmlResponse = getResponse(request);
        
        JAXBContext jc = JAXBContext.newInstance(CategoryListingsResponse.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        CategoryListingsResponse response = (CategoryListingsResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
        
        return response;  
    }  

    /**
     * The SitesLinkingIn action returns a list of web sites linking to a given web site. 
     * Within each domain linking into the web site, only a single link - the one with the highest page-level traffic - is returned.
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws SignatureException
     * @throws IOException
     * @throws JAXBException
     */
    public SitesLinkingInResponse getSitesLinkingIn(SitesLinkingInRequest request) throws UnsupportedEncodingException, SignatureException, IOException, JAXBException {
        String xmlResponse = getResponse(request);
        
        JAXBContext jc = JAXBContext.newInstance(SitesLinkingInResponse.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        SitesLinkingInResponse response = (SitesLinkingInResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
        
        return response;
    }

	private <T> String getResponse(Request<T> request) throws UnsupportedEncodingException, SignatureException, IOException {
		String query = buildQueryString(request);
        String signature = generateSignature(query);

        String uri = AWS_BASE_URL + query + "&Signature=" + URLEncoder.encode(signature, "UTF-8");

        logger.info("Request Url: {}", uri);
        
        String xmlResponse = makeRequest(uri);

        xmlResponse = xmlResponse.replace("xmlns:aws=\"http://awis.amazonaws.com/doc/2005-07-11\"", "");

        logger.info(xmlResponse);
		return xmlResponse;
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
        	logger.error("Http request failed.", e);
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
