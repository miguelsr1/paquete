package sv.gob.mined.seguridadapi;

import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@SpringBootApplication
public class PaquescolarApiApplication {

    
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        DataSource dataSource = dsLookup.getDataSource("java:/SeguridadDS");
        return dataSource;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(PaquescolarApiApplication.class, args);
    }

}
