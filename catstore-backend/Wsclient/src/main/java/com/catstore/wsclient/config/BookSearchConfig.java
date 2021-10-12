package com.catstore.wsclient.config;

import com.catstore.wsclient.client.BookSearchClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class BookSearchConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.catstore.wsclient.webService");
        return marshaller;
    }

    @Bean
    public BookSearchClient bookSearchClient(Jaxb2Marshaller marshaller) {
        BookSearchClient client = new BookSearchClient();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
