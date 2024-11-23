package com.cl932.rsmw;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import javax.net.ssl.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;


@SpringBootApplication
public class RsmwApplication {
    public static void main(String[] args) {
        SpringApplication.run(RsmwApplication.class, args);
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }

}
