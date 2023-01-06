package tfip.day13workshop2.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import tfip.day13workshop2.model.Contact;
import tfip.day13workshop2.util.Contacts;

@Controller
@RequestMapping(path = "/addressbook")
public class AddressBookController {

    @Autowired
    Contacts ctcz;

    @Autowired
    ApplicationArguments appArgs;

    private final String DEFAULT_DATADIR = "C:/Users/bingq/src/day13workshop2/data";

    @GetMapping
    public String showAddrBookForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "addressbook";
    }

    @PostMapping
    public String saveContact(@Valid Contact contact, BindingResult bResult,
            Model model) throws IOException {
        if (bResult.hasErrors()) {
            return "addressbook";
        }
        if (!checkAge(contact.getDateOfBirth())) {
            ObjectError err = new ObjectError("dateOfBirth",
                    "Age must be more than 10 and less than 100");
            bResult.addError(err);
            return "addressbook";
        }
        ctcz.saveContact(contact, model, appArgs, DEFAULT_DATADIR);
        return "result";
    }

    @GetMapping(path = "{contactId}")
    public String getContactById(Model model, @PathVariable String contactId) {
        ctcz.getContactById(model, contactId, appArgs, contactId);
        return "result";
    }

    private boolean checkAge(LocalDate dob) {
        int calcAge = 0;
        if (null != dob) {
            calcAge = Period.between(dob, LocalDate.now()).getYears();
        }
        System.out.println(calcAge);
        if (calcAge >= 10 && calcAge <= 100)
            return true;
        return false;
    }
}
