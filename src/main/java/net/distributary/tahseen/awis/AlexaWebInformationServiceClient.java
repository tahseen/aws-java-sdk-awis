package net.distributary.tahseen.awis;

import com.amazonaws.auth.AWSCredentials;
import net.distributary.tahseen.awis.generated.CategoryBrowseResponse;
import net.distributary.tahseen.awis.generated.CategoryListingsResponse;
import net.distributary.tahseen.awis.generated.SitesLinkingInResponse;
import net.distributary.tahseen.awis.generated.TrafficHistoryResponse;
import net.distributary.tahseen.awis.generated.UrlInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AlexaWebInformationServiceClient {
    protected final static Logger logger = LoggerFactory.getLogger(AlexaWebInformationServiceClient.class);

    private static final String SERVICE_HOST = "awis.amazonaws.com";
    private static final String SERVICE_ENDPOINT = "awis.us-west-1.amazonaws.com";
    private static final String SERVICE_URI = "/api";
    private static final String SERVICE_REGION = "us-west-1";
    private static final String SERVICE_NAME = "awis";
    private static final String AWS_BASE_URL = "https://" + SERVICE_HOST + SERVICE_URI;
    private static final String HASH_ALGORITHM = "HmacSHA256";
    private static final String DATEFORMAT_AWS = "yyyyMMdd'T'HHmmss'Z'";
    private static final String DATEFORMAT_CREDENTIAL = "yyyyMMdd";
    private static final String ALGORITHM = "AWS4-HMAC-SHA256";
    private static final String SIGNED_HEADERS = "host;x-amz-date";

    private AWSCredentials credentials;

    public String amzDate;
    public String dateStamp;

    private Map<String, String> queryParams;

    public AlexaWebInformationServiceClient(AWSCredentials credentials) {
        if (credentials == null) {
            throw new IllegalArgumentException("Parameter credentials can not be null.");
        }

        this.credentials = credentials;

        queryParams = new TreeMap<>();
        queryParams.put("AWSAccessKeyId", credentials.getAWSAccessKeyId());

        Date now = new Date();
        SimpleDateFormat formatAWS = new SimpleDateFormat(DATEFORMAT_AWS);
        formatAWS.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.amzDate = formatAWS.format(now);

        SimpleDateFormat formatCredential = new SimpleDateFormat(DATEFORMAT_CREDENTIAL);
        formatCredential.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.dateStamp = formatCredential.format(now);
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

    protected String sha256(String textToHash) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] byteOfTextToHash = textToHash.getBytes("UTF-8");
        byte[] hashedByteArray = digest.digest(byteOfTextToHash);
        return bytesToHex(hashedByteArray);
    }

    protected byte[] HmacSHA256(String data, byte[] key) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HASH_ALGORITHM);
        mac.init(new SecretKeySpec(key, HASH_ALGORITHM));
        return mac.doFinal(data.getBytes("UTF8"));
    }

    protected String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes)
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

    /**
     * Generates a V4 Signature key for the service/region
     *
     * @param key         Initial secret key
     * @param dateStamp   Date in YYYYMMDD format
     * @param regionName  AWS region for the signature
     * @param serviceName AWS service name
     * @return byte[] signature
     * @throws Exception
     */
    protected byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName)
            throws UnsupportedEncodingException, GeneralSecurityException {
        byte[] kSecret = ("AWS4" + key).getBytes("UTF8");
        byte[] kDate = HmacSHA256(dateStamp, kSecret);
        byte[] kRegion = HmacSHA256(regionName, kDate);
        byte[] kService = HmacSHA256(serviceName, kRegion);
        byte[] kSigning = HmacSHA256("aws4_request", kService);
        return kSigning;
    }

    /**
     * Builds the query string
     */
    protected <T> String buildQueryString(Request<T> request) throws UnsupportedEncodingException {
        String timestamp = getTimestamp(Calendar.getInstance().getTime());

        Map<String, String> queryParams = new TreeMap<String, String>();
        queryParams.put("Action", request.getAction().name());
        queryParams.put("ResponseGroup", request.getResponseGroups().stream().map(rg -> rg.toString()).collect(Collectors.joining(",")));
        queryParams.put("AWSAccessKeyId", credentials.getAWSAccessKeyId());
        queryParams.put("Timestamp", timestamp);
        if (request instanceof UrlInfoRequest) {
            UrlInfoRequest req = (UrlInfoRequest) request;
            queryParams.put("Url", req.getUrl());
        } else if (request instanceof TrafficHistoryRequest) {
            TrafficHistoryRequest req = (TrafficHistoryRequest) request;
            queryParams.put("Url", req.getUrl());
            if (req.getRange() != null) {
                queryParams.put("Range", req.getRange() + "");
            }
            if (req.getStart() != null) {
                queryParams.put("Start", req.getStart());
            }
        } else if (request instanceof CategoryBrowseRequest) {
            CategoryBrowseRequest req = (CategoryBrowseRequest) request;
            queryParams.put("Path", req.getPath());
            if (req.getDescriptions() != null) {
                queryParams.put("Descriptions", req.getDescriptions() + "");
            }
        } else if (request instanceof CategoryListingsRequest) {
            CategoryListingsRequest req = (CategoryListingsRequest) request;
            queryParams.put("Path", req.getPath());
            if (req.getSortBy() != null) {
                queryParams.put("SortBy", req.getSortBy() + "");
            }
            if (req.getRecursive() != null) {
                queryParams.put("Recursive", req.getRecursive() + "");
            }
            if (req.getStart() != null) {
                queryParams.put("Start", req.getStart() + "");
            }
            if (req.getCount() != null) {
                queryParams.put("Count", req.getCount() + "");
            }
            if (req.getDescriptions() != null) {
                queryParams.put("Descriptions", req.getDescriptions() + "");
            }
        } else if (request instanceof SitesLinkingInRequest) {
            SitesLinkingInRequest req = (SitesLinkingInRequest) request;
            queryParams.put("Url", req.getUrl());
            if (req.getStart() != null) {
                queryParams.put("Start", req.getStart() + "");
            }
            if (req.getCount() != null) {
                queryParams.put("Count", req.getCount() + "");
            }
        }

        StringBuffer query = new StringBuffer();
        boolean first = true;
        for (String name : queryParams.keySet()) {
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
     * @param query The data to be signed.
     * @return The base64-encoded RFC 2104-compliant HMAC signature.
     * @throws java.security.SignatureException when signature generation fails
     */
    protected String generateSignature(String query, String credentialScope) throws SignatureException {
        if (credentials == null) {
            throw new IllegalStateException("AWS credentials are not intialized.");
        }
        try {
            String canonicalHeaders = "host:" + SERVICE_ENDPOINT + "\n" + "x-amz-date:" + this.amzDate + "\n";

            String payloadHash = this.sha256("");
            String canonicalRequest =
                    "GET" + "\n" + SERVICE_URI + "\n" + query + "\n" + canonicalHeaders + "\n" + SIGNED_HEADERS + "\n" + payloadHash;

            String stringToSign = ALGORITHM + '\n' + this.amzDate + '\n' + credentialScope + '\n' + this.sha256(canonicalRequest);
            byte[] signingKey = getSignatureKey(credentials.getAWSSecretKey(), this.dateStamp, SERVICE_REGION, SERVICE_NAME);

            // Sign the string_to_sign using the signing_key
            return bytesToHex(HmacSHA256(stringToSign, signingKey));
        } catch (Exception e) {
            throw new SignatureException("Failed to generate signature: " + e.getMessage());
        }
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
     * <p>
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
    public CategoryBrowseResponse getCategoryBrowse(CategoryBrowseRequest request) throws JAXBException, SignatureException, IOException {
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
    public CategoryListingsResponse getCategoryListings(CategoryListingsRequest request) throws SignatureException, IOException, JAXBException {
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
    public SitesLinkingInResponse getSitesLinkingIn(SitesLinkingInRequest request) throws SignatureException, IOException, JAXBException {
        String xmlResponse = getResponse(request);

        JAXBContext jc = JAXBContext.newInstance(SitesLinkingInResponse.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        SitesLinkingInResponse response = (SitesLinkingInResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));

        return response;
    }

    private <T> String getResponse(Request<T> request) throws IOException, SignatureException {
        String query = buildQueryString(request);
        String credentialScope = this.dateStamp + "/" + SERVICE_REGION + "/" + SERVICE_NAME + "/" + "aws4_request";

        String signature = generateSignature(query, credentialScope);

        String uri = AWS_BASE_URL + "?" + query;

        logger.info("Request Url: {}", uri);

        String authorization =
                ALGORITHM + " " + "Credential=" + credentials.getAWSAccessKeyId() + "/" + credentialScope + ", " + "SignedHeaders=" + SIGNED_HEADERS
                        + ", " + "Signature=" + signature;
        String xmlResponse = makeRequest(uri, authorization, this.amzDate);

        xmlResponse = xmlResponse.replace("xmlns:aws=\"http://awis.amazonaws.com/doc/2005-07-11\"", "");
        xmlResponse = xmlResponse.replace("xmlns:aws=\"http://alexa.amazonaws.com/doc/2005-10-05/\"", "");
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
    public String makeRequest(String requestUrl, String authorization, String amzDate) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", "application/xml");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("X-Amz-Date", amzDate);
        conn.setRequestProperty("Authorization", authorization);
        InputStream in;
        try {
            in = conn.getInputStream();
        } catch (Exception e) {
            logger.error("Http request failed.", e);
            in = conn.getErrorStream();
        }

        StringBuffer sb = null;
        if (in != null) {
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
