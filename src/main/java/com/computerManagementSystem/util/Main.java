package com.computerManagementSystem.util;

        import com.computerManagementSystem.controller.SystemApplication;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.boot.SpringApplication;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);

        logger.info("nihao");
        SpringApplication.run(SystemApplication.class, args);
    }
}
