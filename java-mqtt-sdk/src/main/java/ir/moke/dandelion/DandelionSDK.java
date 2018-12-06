package ir.moke.dandelion;

import ir.moke.dandelion.logger.LoggerProducer;
import ir.moke.dandelion.mqtt.MessageConsumer;
import ir.moke.dandelion.mqtt.MessageListener;
import ir.moke.dandelion.mqtt.MessageListenerHandler;
import org.eclipse.paho.client.mqttv3.MqttClient;

import java.util.logging.Logger;

public class DandelionSDK {
    private static final Logger logger = LoggerProducer.produceLogger();

    private String endpoint = "tcp://localhost:1883";
    private String apiKey;

    public DandelionSDK(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void registerMessageListener(Class<? extends MessageListener> messageListener) {
        MessageListenerHandler.instance.registerMessageListener(messageListener);
    }

    public void init() throws Exception {
        DandelionCredentialFactory.initialize();
    }

    public void start() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MqttClient mqttClient = MessageConsumer.connect(apiKey, endpoint);
//        while (mqttClient.isConnected()) {
//        }
    }

    public void stop() {
        MessageConsumer.disconnect();
    }

    private void sleep(int milSec) {
        try {
            Thread.sleep(milSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}