package org.sid.comptecqrseventsourcing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
public class CompteCqrsEventsourcingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompteCqrsEventsourcingApplication.class, args);
    }

}
