package guru.springframework.sfgjms;

import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfgJmsPdibenApplication
{

    public static void main(String[] args) throws Exception
    {
        // Normally, we don't run a JMS Server, embedded like this.
        ActiveMQServer server = ActiveMQServers
                                    .newActiveMQServer(new ConfigurationImpl()
                                    .setPersistenceEnabled(false)
                                    .setJournalDirectory("target.data/journal")
                                    .setSecurityEnabled(false)
                                    .addAcceptorConfiguration("invm", "vm://0"));
        server.start();

        SpringApplication.run(SfgJmsPdibenApplication.class, args);
    }

}
