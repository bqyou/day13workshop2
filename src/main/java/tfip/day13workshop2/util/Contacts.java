package tfip.day13workshop2.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import tfip.day13workshop2.model.Contact;

@Component("contacts")
public class Contacts {

    private static final Logger logger = LoggerFactory.getLogger(Contacts.class);

    public void saveContact(Contact ctc, Model model,
            ApplicationArguments appArgs, String defaultDataDir) throws IOException {
        String dataFilename = ctc.getId();
        PrintWriter printwriter = null;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(getDataDir(appArgs, defaultDataDir) + "/"
                    + dataFilename);
            printwriter = new PrintWriter(fileWriter);
            printwriter.println(ctc.getName());
            printwriter.println(ctc.getEmail());
            printwriter.println(ctc.getPhoneNumber());
            printwriter.println(ctc.getDateOfBirth().toString());
            printwriter.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        model.addAttribute("contact",
                new Contact(ctc.getId(), ctc.getName(), ctc.getEmail(), ctc.getPhoneNumber(), ctc.getDateOfBirth()));

    }

    private String getDataDir(ApplicationArguments appArgs,
            String defaultDataDir) {
        String dataDirResult = null;
        List<String> optValues = null;
        Set<String> opsNames = appArgs.getOptionNames();
        String[] optNamesArr = opsNames.toArray(new String[opsNames.size()]);
        if (optNamesArr.length > 0) {
            optValues = appArgs.getOptionValues(optNamesArr[0]);
            dataDirResult = optValues.get(0);
        } else {
            dataDirResult = defaultDataDir;
        }
        return dataDirResult;
    }

    public void getContactById(Model model, String contactId,
            ApplicationArguments appArgs, String defaultDataDir) {
        Contact c = new Contact();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Path filePath = new File(getDataDir(appArgs, defaultDataDir) + "/" + contactId).toPath();
        Charset charset = Charset.forName("UTF-8");
        try {
            List<String> stringListDat = Files.readAllLines(filePath, charset);
            c.setId(contactId);
            c.setName(stringListDat.get(0));
            c.setEmail(stringListDat.get(1));
            c.setPhoneNumber(Integer.parseInt(stringListDat.get(2)));
            LocalDate dob = LocalDate.parse(stringListDat.get(3), formatter);
            c.setDateOfBirth(dob);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact info not found");
        }

        model.addAttribute("contact", c);

    }

}
