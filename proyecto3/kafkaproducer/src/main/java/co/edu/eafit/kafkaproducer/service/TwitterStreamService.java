package co.edu.eafit.kafkaproducer.service;

import com.twitter.hbc.core.Client;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

@Service
public class TwitterStreamService {

    @Autowired
    private BlockingQueue<String> messageQueue;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private Client twitterClient;

    @Value("${kafka.topic}")
    private String topicName;

    @PostConstruct
    public void init() {
        new Thread(() -> {
            while (!twitterClient.isDone()) {
                try {
                    String msg = messageQueue.take();
                    kafkaTemplate.send(topicName, msg);
                } catch (InterruptedException e) {
                    twitterClient.stop();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}