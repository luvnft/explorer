package org.royllo.explorer.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Web application.
 */
@SpringBootApplication(scanBasePackages = "org.royllo.explorer")
@SuppressWarnings({"checkstyle:FinalClass", "checkstyle:HideUtilityClassConstructor"})
public class WebApplication {

    /**
     * Main.
     *
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
