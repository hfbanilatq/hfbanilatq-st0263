package co.edu.eafit.kafkaproducer.config;
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// Otros imports...

@Configuration
public class TwitterConfig {

    @Value("${twitter.apiKey}")
    private String apiKey;

    @Value("${twitter.apiSecretKey}")
    private String apiSecretKey;

    @Value("${twitter.accessToken}")
    private String accessToken;

    @Value("${twitter.accessTokenSecret}")
    private String accessTokenSecret;
    @Value("${twitter.terms}")
    private String[] terms;
    @Bean
    public Client twitterClient(BlockingQueue<String> msgQueue) {
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.trackTerms(List.of(terms));

        Authentication auth = new OAuth1(apiKey, apiSecretKey, accessToken, accessTokenSecret);

        Client client = new ClientBuilder()
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(msgQueue))
                .build();
        client.connect();
        return client;
    }

    @Bean
    public BlockingQueue<String> msgQueue() {
        return new LinkedBlockingQueue<String>(10000);
    }
}
