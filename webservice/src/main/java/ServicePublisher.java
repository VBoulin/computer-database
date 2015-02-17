import javax.xml.ws.Endpoint;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.formation.java.service.CompanyDBService;
import com.excilys.formation.java.service.ComputerDBService;
import com.excilys.formation.java.webservice.impl.CompanyWebServiceImpl;
import com.excilys.formation.java.webservice.impl.ComputerWebServiceImpl;


public class ServicePublisher {

  public static void main(String[] args) {
    System.out.println("Publishing...");
    
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("serviceContext.xml");
    ComputerDBService computerDBService = (ComputerDBService) context.getBean(ComputerDBService.class);
    CompanyDBService companyDBService = (CompanyDBService) context.getBean(CompanyDBService.class);
    
    Endpoint.publish("http://localhost:9999/computer-database/webservice/company", new CompanyWebServiceImpl(companyDBService));
    Endpoint.publish("http://localhost:9999/computer-database/webservice/computer", new ComputerWebServiceImpl(computerDBService, companyDBService));
    
    System.out.println("Published !");
  }

}
