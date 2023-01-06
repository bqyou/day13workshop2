package tfip.day13workshop2;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tfip.day13workshop2.util.IOUtil;

@SpringBootApplication
public class Day13workshop2Application {

	private static final Logger logger = LoggerFactory.getLogger(Day13workshop2Application.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Day13workshop2Application.class);
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		List<String> opsVal = appArgs.getOptionValues("dataDir");
		if (opsVal != null) {
			logger.info("" + (String) opsVal.get(0));
			IOUtil.createDir(opsVal.get(0));
		} else {
			logger.warn("No data directory input");
			System.exit(1);
		}
		app.run(args);
	}

}
