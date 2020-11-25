import org.example.model.Applicant;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import java.util.ArrayList;
import java.util.List;

import static org.example.kieSession.*;

public class myTest {

//    @Test
//    public void statelessWithoutXMLTest(){
//        StatelessKieSession kieSession = initiateStatelessSessionWithoutXML();
//
//        Applicant applicant = new Applicant("Smith", 12);
//        Applicant applicant1 = new Applicant("John", 20);
//        Applicant applicant2 = new Applicant("Stacey", 15);
//
//        assertTrue(applicant.isValid());
//        assertTrue(applicant1.isValid());
//        assertTrue(applicant2.isValid());
//
//        List<Applicant> applicantList = new ArrayList<Applicant>();
//        applicantList.add(applicant);
//        applicantList.add(applicant1);
//        applicantList.add(applicant2);
//
//        kieSession.execute(applicantList);
//
//        assertFalse(applicant.isValid());
//        assertTrue(applicant1.isValid());
//        assertFalse(applicant2.isValid());
//
//    }

    @Test
    public void statelessWithXMLTest(){
        StatelessKieSession kieSession = initiateStatelessSessionWithXML();

        Applicant applicant = new Applicant("Smith", 12);
        Applicant applicant1 = new Applicant("John", 20);
        Applicant applicant2 = new Applicant("Stacey", 15);

        assertTrue(applicant.isValid());
        assertTrue(applicant1.isValid());
        assertTrue(applicant2.isValid());

        List<Applicant> applicantList = new ArrayList<Applicant>();
        applicantList.add(applicant);
        applicantList.add(applicant1);
        applicantList.add(applicant2);

        kieSession.execute(applicantList);

        assertFalse(applicant.isValid());
        assertTrue(applicant1.isValid());
        assertFalse(applicant2.isValid());
    }

    @Test
    public void statefulWithXMLTest(){
        KieSession kieSession = initiateStatefulWithXML();

        Applicant applicant = new Applicant("Smith", 12);
        Applicant applicant1 = new Applicant("John", 20);
        Applicant applicant2 = new Applicant("Stacey", 15);

        assertTrue(applicant.isValid());
        assertTrue(applicant1.isValid());
        assertTrue(applicant2.isValid());

        List<Applicant> applicantList = new ArrayList<Applicant>();
        applicantList.add(applicant);
        applicantList.add(applicant1);
        applicantList.add(applicant2);

        kieSession.insert(applicant);
        kieSession.insert(applicant1);
        kieSession.insert(applicant2);
        kieSession.fireAllRules();

        assertFalse(applicant.isValid());
        assertTrue(applicant1.isValid());
        assertFalse(applicant2.isValid());
    }
}
