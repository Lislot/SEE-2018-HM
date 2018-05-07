
import java.io.File;

import federate.RFPAmbassador;
import federate.RFPFederate;
import skf.config.Configuration;
import skf.config.ConfigurationFactory;

public class Main {

	private static final File conf = new File("conf/conf.json");

	public static void main(String[] args) throws Exception {
		
		ConfigurationFactory factory = new ConfigurationFactory();
		Configuration configuration = factory.importConfiguration(conf);

		RFPAmbassador rfp_amb = new RFPAmbassador();
		RFPFederate rfp_federate = new RFPFederate(rfp_amb);

		rfp_federate.configure(configuration);
		rfp_federate.start();
	}//main

}

