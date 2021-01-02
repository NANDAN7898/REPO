package in.webproto.schoolmgmt.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(value = { "in.webproto.schoolmgmt.controller", "in.webproto.schoolmgmt.dao", "in.webproto.schoolmgmt.service", "in.webproto.schoolmgmt.util"})
@PropertySource(value = { "classpath:ApplicationProperties.properties" })
public class WebAppConfig
{

}
