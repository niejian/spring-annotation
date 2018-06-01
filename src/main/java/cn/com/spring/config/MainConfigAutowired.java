package cn.com.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/**
 * ×Ô¶¯×°Åä
 * @author niejian
 *
 */

@Configuration
@ComponentScan({
	"cn.com.spring.config",
	"cn.com.spring.dao", 
	"cn.com.spring.service",
	"cn.com.spring.bean"
})
public class MainConfigAutowired {

}
