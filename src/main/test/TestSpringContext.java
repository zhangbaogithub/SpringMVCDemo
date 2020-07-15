import com.hello.example.controller.EmployeeController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestSpringContext {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"/dispatcher-servlet.xml"});
//        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"/applicationContext.xml"});
        EmployeeController employeeController = context.getBean(EmployeeController.class);
        employeeController.readLocaleSpecificMessage();
    }
}
