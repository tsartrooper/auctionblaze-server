package com.example.auction_application.Config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class EnvConfig {

    static {
        Map<String, String> keySource = new HashMap<>();

        // Directories to try (current working dir, typical container workdir /app, project root)
        String[] dirsToTry = new String[] {
            Paths.get("").toAbsolutePath().toString(),
            "/app",
            "/workspace",
            "/"
        };

        // Try loading .env from multiple locations; ignore missing files
        for (String dir : dirsToTry) {
            try {
                Dotenv dotenv = Dotenv.configure()
                        .directory(dir)
                        .filename(".env")
                        .ignoreIfMissing()
                        .load();

                dotenv.entries().forEach(entry -> {
                    String k = entry.getKey();
                    String v = entry.getValue();
                    // Only set system property if not already provided (system property or environment variable)
                    if (System.getProperty(k) == null && System.getenv(k) == null) {
                        System.setProperty(k, v);
                        keySource.putIfAbsent(k, "dotenv(" + dir + ")");
                    } else {
                        // mark presence but keep precedence (env/system prop wins)
                        keySource.putIfAbsent(k, System.getenv(k) != null ? "env" : "system.prop");
                    }
                });
            } catch (Exception e) {
                // ignore and try next location
            }
        }

        // Ensure critical keys are set from environment variables if not set by dotenv
        String[] critical = new String[] {
            "PORT",
            "DATABASE_URL",
            "DATABASE_USERNAME",
            "DATABASE_PASSWORD",
            "JWT_SECRET",
            "JWT_EXPIRATION_LIMIT",
            "QUARTZ_DATABASE_URL",
            "QUARTZ_DATABASE_USERNAME",
            "QUARTZ_DATABASE_PASSWORD",
            "REDIS_HOST",
            "REDIS_PASSWORD"
        };

        for (String k : critical) {
            if (System.getProperty(k) == null) {
                String ev = System.getenv(k);
                if (ev != null) {
                    System.setProperty(k, ev);
                    keySource.put(k, "env");
                } else {
                    keySource.putIfAbsent(k, "null");
                }
            } else {
                keySource.putIfAbsent(k, "system.prop");
            }
        }

        // Small debug summary (key -> source). Do NOT print values to avoid leaking secrets.
        System.out.println("=== DOTENV DEBUG ===");
        System.out.println("Resolved keys: " + keySource.size());
        // show only a few keys of interest for quick check
        String[] toShow = new String[] {"PORT", "DATABASE_URL", "DATABASE_USERNAME", "DATABASE_PASSWORD"};
        for (String k : toShow) {
            String src = keySource.getOrDefault(k, "not-found");
            System.out.println(k + " -> " + src);
        }
        System.out.println("====================");
    }
}
