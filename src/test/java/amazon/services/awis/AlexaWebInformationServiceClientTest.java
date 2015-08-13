package amazon.services.awis;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import amazon.services.awis.enums.ResponseGroup;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;


public class AlexaWebInformationServiceClientTest {
    protected final static Logger logger = LoggerFactory.getLogger(AlexaWebInformationServiceClientTest.class);

    private static AWSCredentials credentials;
    
    @BeforeClass
    public static void before() {
        logger.info("AWS_ACCESS_KEY_ID:{}", System.getenv("AWS_ACCESS_KEY_ID"));
        logger.info("AWS_SECRET_KEY:{}", System.getenv("AWS_SECRET_KEY"));
        
        DefaultAWSCredentialsProviderChain defaultAWSCredentialsProviderChain = new DefaultAWSCredentialsProviderChain();
        credentials = defaultAWSCredentialsProviderChain.getCredentials();
    }
    
    @Test
    public void testGetUrlInfo() throws SignatureException, IOException {
        AlexaWebInformationServiceClient client = new AlexaWebInformationServiceClient(credentials);
        
        UrlInfoRequest request = new UrlInfoRequest();
        request.setResponseGroups(Arrays.asList(ResponseGroup.Categories, ResponseGroup.Rank));
        request.setUrl("www.bryght.com");
        
        client.getUrlInfo(request);
    }
}
