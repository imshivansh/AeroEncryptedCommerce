package com.aerospikeEcommerce.orderManagement.conf;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class AerospikeConfig {
    @Value("${spring.aerospike.host}")
    private String aerospikeHost;
    @Value("${spring.aerospike.port}")
    private int aerospikePort;


    @Bean(destroyMethod = "close")
    public AerospikeClient aerospikeClient() {

        ClientPolicy clientPolicy = new ClientPolicy();
        clientPolicy.failIfNotConnected = true; // Optional: Set this to true if you want to throw exceptions on connection failures.

        Host[] hosts = Arrays.stream(aerospikeHost.split(","))
                .map(host -> new Host(host, aerospikePort))
                .toArray(Host[]::new);

        return new AerospikeClient(clientPolicy, hosts);

    }

}