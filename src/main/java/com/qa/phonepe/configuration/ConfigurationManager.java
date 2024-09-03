package com.qa.phonepe.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.phonepe.frameworkexception.APIFrameworkException;

public class ConfigurationManager {

	private Properties prop;
	private FileInputStream fis;

	public Properties initProp() {
		prop = new Properties();
		String envName = System.getProperty("env");
		if (envName == null) {
			System.out.println("No enviournment is given hence running on qa env....");
			try {
				fis = new FileInputStream("./src/test/resources/config/config.properties");
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Running test on " + envName + " Enviournment");
			try {
				switch (envName.toLowerCase().trim()) {
				case "qa": {
					fis = new FileInputStream("./src/test/resource/config/qa.config.properties");
				}
				case "dev": {
					fis = new FileInputStream("./src/test/resource/config/dev.config.properties");
				}
				default:
					System.out.println("Please pass the right enviornment name : " + envName);
					throw new APIFrameworkException("Wrong Env Is Given");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
