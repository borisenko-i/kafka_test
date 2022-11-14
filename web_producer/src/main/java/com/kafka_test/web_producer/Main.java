package com.kafka_test.web_producer;

import org.springframework.web.servlet.DispatcherServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.net.InetSocketAddress;
import java.util.logging.Logger;

@SuppressWarnings("all")
public final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private Main() {}

    public static void main(String[] args) {
        LOGGER.info(String.format("HOST: %s, PORT: %s, KAFKA_HOST_KEY: %s", args[0], args[1], args[2]));
        Server server = createServer(args[0], Integer.valueOf(args[1]), args[2]);

        try {
            server.start();
            LOGGER.info("Web producer API started");
        } catch (Exception e) {
            LOGGER.severe(String.format("Error starting up a server: %s", e.getMessage()));
        }
    }

    private static Server createServer(String producerApiHost, int producerApiPort, String kafkaHost) {
        // Create a server listening to HTTP requests
        Server server = new Server(new InetSocketAddress(producerApiHost, producerApiPort));
        Connector connector = new ServerConnector(server);
        server.addConnector(connector);

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.scan("com.kafka_test.web_producer");
        applicationContext.refresh();

        /*// Create a servlet holder off of ProducerApiResource, set resource properties
        ResourceConfig resourceConfig = new ResourceConfig().register(ProducerApiResource.class);
        resourceConfig.setProperties(
                Map.of(
                        ProducerApiResource.KAFKA_BOOTSTRAP_HOST_KEY, kafkaHost,
                        "jersey.config.server.wadl.disableWadl", "true"));*/
        ServletHolder servletHolder = new ServletHolder(new DispatcherServlet(applicationContext));

        // Set up context handler to set ProducerApiResource on "/*"
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
        contextHandler.addServlet(servletHolder, "/");

        // Attach handler, stop server at app shutdown
        server.setHandler(contextHandler);
        server.setStopAtShutdown(true);

        return server;
    }
}
