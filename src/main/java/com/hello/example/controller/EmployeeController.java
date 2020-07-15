package com.hello.example.controller;

import com.hello.example.model.EmployeeVO;
import com.hello.example.service.EmployeeManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Locale;
import java.util.Set;

@Controller
@RequestMapping("/employee-module/addNew")
@SessionAttributes("employee")
public class EmployeeController implements MessageSourceAware {

    private static final Log logger = LogFactory.getLog(EmployeeController.class);

    private Validator validator;

    @Autowired
    EmployeeManager manager;

    public EmployeeController() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @RequestMapping(value = "getAllEmployees", method = RequestMethod.GET)
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", manager.getAllEmployees());
        return "employeesListDisplay";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(Model model) {
        EmployeeVO employeeVO = new EmployeeVO();
        model.addAttribute("employee", employeeVO);
        return "addEmployee";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(@ModelAttribute("employee") EmployeeVO employeeVO, BindingResult result,
                             SessionStatus status) {
        Set<ConstraintViolation<EmployeeVO>> violations = validator.validate(employeeVO);
        for (ConstraintViolation<EmployeeVO> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            result.addError(new FieldError("employee", propertyPath,
                    "Invalid" + propertyPath + "(" + message + ")"));
        }

        if (result.hasErrors()) {
            return "addEmployee";
        }

        return "redirect:addNew/success";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success(Model model) {
        return "addSuccess";
    }

    private MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void readLocaleSpecificMessage() {
        String englishMessage = messageSource.getMessage("first.name", null, Locale.US);

        System.out.println("First name label in English : " + englishMessage);

        String chineseMessage = messageSource.getMessage("first.name", null, Locale.SIMPLIFIED_CHINESE);

        System.out.println("First name label in Chinese : " + chineseMessage);
    }

}
