package som.staffGauge.survey.api.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created by Mr.F on 2019/12/28
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class DubboApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboApiApplication.class, args);
    }
}
