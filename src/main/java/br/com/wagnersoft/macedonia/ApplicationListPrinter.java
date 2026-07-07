package br.com.wagnersoft.macedonia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import java.util.Map;

@Component
public class ApplicationListPrinter implements ApplicationListener<ApplicationReadyEvent> {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationListPrinter.class);
  
  private final Map<String, ? extends ServletRegistration> servlets;
  
  public ApplicationListPrinter(ServletContext servletContext) {
    this.servlets = servletContext.getServletRegistrations();
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    logger.info("---- Registered servlets ----");
    servlets.forEach((name, reg) -> {
      logger.info("name={}, mappings={}, class={} ",name , reg.getMappings(), reg.getClassName());
    });
    logger.info("---- end servlets ----");
  }

}
