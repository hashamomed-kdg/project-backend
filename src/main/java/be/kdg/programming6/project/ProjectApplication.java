package be.kdg.programming6.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.Modulith;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Modulith
@EnableScheduling
public class ProjectApplication {

    private static final Logger log = LoggerFactory.getLogger(ProjectApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    void onApplicationStarted() {
        ApplicationModules modules = ApplicationModules.of(ProjectApplication.class);
        modules.forEach(module -> log.info("\n{}", module));
    }
}
