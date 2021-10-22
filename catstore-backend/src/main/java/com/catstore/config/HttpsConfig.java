//package com.catstore.config;
//
//import org.apache.catalina.Context;
//import org.apache.catalina.connector.Connector;
//import org.apache.tomcat.util.descriptor.web.SecurityCollection;
//import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class HttpsConfig {
////    @Bean
////    public EmbeddedServletContainerC    ustomizer containerCustomizer() {
////        return new EmbeddedServletContainerCustomizer() {
////            @Override
////            public void customize(ConfigurableEmbeddedServletContainer container) {
////                Ssl ssl = new Ssl();
////                //Server.jks中包含服务器私钥和证书
////                ssl.setKeyStore("证书名");
////                ssl.setKeyStorePassword("密码");
////                container.setSsl(ssl);
////                container.setPort(443);
////            }
////        };
////    }
//
//    @Bean
//    public Connector connector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(true);
//        connector.setRedirectPort(8443);
//        return connector;
//    }
//
//    @Bean
//    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector) {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(connector);
//        return tomcat;
//    }
//}
