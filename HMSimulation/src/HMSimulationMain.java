import java.io.File;

import core.HMSimulationFederate;
import core.HMSimulationFederateAmbassador;
import skf.config.ConfigurationFactory;
import model.HMSpaceStation;
import model.Astronaut_HM;
import model.Position;


public class HMSimulationMain {
	
	private static final File conf = new File("config/conf.json");
	
	public static void main(String[] args) throws Exception {
		
		HMSpaceStation hmSpaceStation = new HMSpaceStation("HMSpaceStation", "EarthMoonL2Rotating",
				new Position (0, 0, 0));
		Astronaut_HM astronaut_hm = new Astronaut_HM("Astronaut_HM", "EarthMoonL2Rotating",
				new Position(0, 0, 0));
		
		HMSimulationFederateAmbassador ambassador = new HMSimulationFederateAmbassador();
		HMSimulationFederate federate = new HMSimulationFederate(ambassador, hmSpaceStation, astronaut_hm );
		
		// start execution
		federate.configureAndStart(new ConfigurationFactory().importConfiguration(conf));
	}

}
