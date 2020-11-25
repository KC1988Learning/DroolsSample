package org.example;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.conf.ClockTypeOption;

public class kieSession {

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

    public static void programmaticAPI(){
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

        // Add kieBases and kieSessions to the virtual kieFileSystem
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.writeKModuleXML(kieModuleModel.toXML());

        // Add all KIE artifacts (knowledge authoring tools) to the file system
        kfs.write("src/main/resources/ApplicantValidity/license.drl",
                kieServices.getResources().newFileSystemResource(
                        "src/main/resources/ApplicantValidity/license.drl"
                ));

    }
}
