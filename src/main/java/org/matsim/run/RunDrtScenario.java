package org.matsim.run;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Route;
import org.matsim.contrib.av.robotaxi.fares.drt.DrtFareModule;
import org.matsim.contrib.av.robotaxi.fares.drt.DrtFaresConfigGroup;
import org.matsim.contrib.drt.routing.DrtRoute;
import org.matsim.contrib.drt.routing.DrtRouteFactory;
import org.matsim.contrib.drt.run.*;
import org.matsim.contrib.dvrp.run.DvrpConfigGroup;
import org.matsim.contrib.dvrp.run.DvrpModule;
import org.matsim.contrib.dvrp.run.DvrpQSimComponents;
import org.matsim.contrib.otfvis.OTFVisLiveModule;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.population.routes.RouteFactories;
import org.matsim.core.scenario.ScenarioUtils;

import java.util.function.Function;

public class RunDrtScenario {

	public static void main(String[] args) {
		Gbl.assertIf(args.length >= 1 && !args[0].equals(""));
		run(ConfigUtils.loadConfig(args[0], new DrtConfigGroup(), new DvrpConfigGroup(), new DrtFaresConfigGroup()));
		// makes some sense to not modify the config here but in the run method to help  with regression testing.
	}

	static void run(Config config) {

		DrtConfigGroup drtCfg = DrtConfigGroup.get(config);
		DrtConfigs.adjustDrtConfig(drtCfg, config.planCalcScore());

		config.addConfigConsistencyChecker(new DrtConfigConsistencyChecker());
		config.checkConsistency();

		Scenario scenario1 = ((Function<Config, Scenario>) cfg -> {
			Scenario scenario2 = ScenarioUtils.createScenario(cfg);
			RouteFactories routeFactories = scenario2.getPopulation().getFactory().getRouteFactories();
			if (routeFactories.getRouteClassForType(DrtRoute.ROUTE_TYPE).equals(Route.class)) {
				routeFactories.setRouteFactory(DrtRoute.class, new DrtRouteFactory());
			}
			Scenario scenario = scenario2;
			ScenarioUtils.loadScenario(scenario);
			return scenario;
		}).apply(config);

		Controler controler1 = new Controler(scenario1);
		controler1.addOverridingModule(new DrtModule());
		controler1.addOverridingModule(new DvrpModule());
		controler1.configureQSimComponents(DvrpQSimComponents.activateModes(drtCfg.getMode()));

		if (false) {
			controler1.addOverridingModule(new OTFVisLiveModule());
		}
		Controler controler = controler1;
		DrtControlerCreator.addDrtRouteFactory(controler.getScenario());
		controler.addOverridingModule(new DrtFareModule());
		controler.getConfig().plansCalcRoute().setInsertingAccessEgressWalk(true);
		controler.run();
	}
}
