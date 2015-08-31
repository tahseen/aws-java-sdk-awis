package amazon.services.awis;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import amazon.services.awis.enums.ResponseGroup;
import amazon.services.awis.generated.TrafficHistoryResponse;
import amazon.services.awis.generated.UrlInfoResponse;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;


public class AlexaWebInformationServiceClientTest {
    protected final static Logger logger = LoggerFactory.getLogger(AlexaWebInformationServiceClientTest.class);

    private static AWSCredentials credentials;
    
    @BeforeClass
    public static void before() {
        DefaultAWSCredentialsProviderChain defaultAWSCredentialsProviderChain = new DefaultAWSCredentialsProviderChain();
        credentials = defaultAWSCredentialsProviderChain.getCredentials();
    }
    
   // @Test
    public void testGetUrlInfo() throws SignatureException, IOException, JAXBException {
        AlexaWebInformationServiceClient client = new AlexaWebInformationServiceClient(credentials);
        
        UrlInfoRequest request = new UrlInfoRequest();
        request.setResponseGroups(Arrays.asList(ResponseGroup.values()));
        request.setUrl("www.bryght.com");
        
        UrlInfoResponse urlInfoResponse = client.getUrlInfo(request);
        
        Assert.assertNotNull(urlInfoResponse);
        Assert.assertNotNull(urlInfoResponse.getResponse());
        Assert.assertNotNull(urlInfoResponse.getResponse().getUrlInfoResult().get(0));
        Assert.assertNotNull(urlInfoResponse.getResponse().getUrlInfoResult().get(0).getAlexa().getContactInfo());
        Assert.assertNotNull(urlInfoResponse.getResponse().getUrlInfoResult().get(0).getAlexa().getContentData());
        Assert.assertNotNull(urlInfoResponse.getResponse().getUrlInfoResult().get(0).getAlexa().getRelated());
        Assert.assertNotNull(urlInfoResponse.getResponse().getUrlInfoResult().get(0).getAlexa().getTrafficData());
    }
    
    @Test
    public void testGetTrafficHistory() throws SignatureException, IOException, JAXBException {
        AlexaWebInformationServiceClient client = new AlexaWebInformationServiceClient(credentials);
        
        TrafficHistoryRequest request = new TrafficHistoryRequest();
        request.setUrl("www.bryght.com");
        request.setRange(20);
        
        Calendar start = GregorianCalendar.getInstance();
        start.set(Calendar.YEAR, 2015);
        start.set(Calendar.MONTH, 1);
        start.set(Calendar.DAY_OF_MONTH, 1);
        
        request.setStart(start.getTime());

        TrafficHistoryResponse response = client.getTrafficHistory(request);
       
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getResponse());
        Assert.assertNotNull(response.getResponse().getTrafficHistoryResult().get(0));
        Assert.assertNotNull(response.getResponse().getTrafficHistoryResult().get(0).getAlexa().getTrafficHistory());
    }
}
