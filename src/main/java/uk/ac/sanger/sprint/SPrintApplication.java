package uk.ac.sanger.sprint;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SPrintApplication {

    @Value("${http.port:0}")
    private int httpPort;

    public static void main(String[] args) {
        SpringApplication.run(SPrintApplication.class, args);
    }

    // Configure additional connector to enable support for both http and https
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        if (httpPort != 0) {
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setPort(httpPort);
            tomcat.addAdditionalTomcatConnectors(connector);
        }
        return tomcat;
    }
}
