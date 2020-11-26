package org.example.topic;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.conf.ClockTypeOption;

public class kieSessionCreation {

    // Include only kmodule tag in the kmodule.xml file
    // before using
    public static StatelessKieSession initiateStatelessSessionWithoutXML(){
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();

        StatelessKieSession kieSession = kieContainer.newStatelessKieSession();
        return kieSession;
    }

    // Include kmodule, kbase and ksession tags in the kmodule.xml file
    // before using
    public static StatelessKieSession initiateStatelessSessionWithXML(){
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();

        KieBase kieBase = kieContainer.getKieBase();
        StatelessKieSession kieSession = kieContainer.newStatelessKieSession("kSession2");
        return kieSession;
    }

    // Include kmodule, kbase and ksession tags in the kmodule.xml file
    // before using
    public static KieSession initiateStatefulWithXML(){
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();

        KieBase kieBase = kieContainer.getKieBase();
        KieSession kieSession = kieContainer.newKieSession();
        return kieSession;
    }

    public static KieSession initiateStatefulWithProgrammaticAPI(){
        KieServices kieServices = KieServices.Factory.get();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

        KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel("KBase1")
                                    .setDefault(true)
                                    .setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
                                    .setEventProcessingMode(EventProcessingOption.STREAM);

        KieSessionModel kieSessionModel1 = kieBaseModel1.newKieSessionModel("KSession1")
                                                        .setDefault(true)
                                                        .setType(KieSessionModel.KieSessionType.STATEFUL)
                                                        .setClockType(ClockTypeOption.get("realtime"));

        // configure kieSession with the kieBase and kieSession to the virtual kieFileSystem
        KieFileSystem kfs = kieServices.newKieFileSystem();

        // convert ModuleModel to XML and then add the XML to kieFile system
        kfs.writeKModuleXML(kieModuleModel.toXML());

        // Add all KIE artifacts as Resources to the kieFile system
        kfs.write("src/main/resources/ApplicantValidity/license.drl",
                kieServices.getResources().newFileSystemResource(
                        "src/main/resources/ApplicantValidity/license.drl"
                ).setResourceType(ResourceType.DRL));

        // Build the kieFile system
        // Once successfully built, the resulting kieModule is automatically added to the
        // KieRepository.
        // KieRepository is a singleton acting as a repository for all the available KieModules
        kieServices.newKieBuilder(kfs).buildAll();

        //
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();
        KieSession kieSession = kieContainer.newKieSession();
        return kieSession;
    }
}
