package ru.kamikadze_zm.zmedia.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class Firebase {

    private static final Logger LOG = LogManager.getLogger(Firebase.class);

    @Autowired
    private Environment env;
    @Autowired
    private ServletContext sc;

    @Bean
    public FirebaseApp firebase() {

        try (InputStream is = sc.getResourceAsStream(env.getProperty("firebase.service-account-key-path"))) {

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(is))
                    .setDatabaseUrl(env.getProperty("firebase.database-url"))
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            LOG.error("Initializing firebase app exception: ", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        return FirebaseMessaging.getInstance(firebase());
    }
}
