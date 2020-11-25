import org.apache.poi.hpsf.Array;
import org.example.model.Applicant;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

import java.util.ArrayList;
import java.util.List;

public class myTest {

    @Test
    public void applicationValidityTest(){
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();

        StatelessKieSession kieSession = kieContainer.newStatelessKieSession();

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

//        kieSession.execute(applicant);
//        kieSession.execute(applicant1);
//        kieSession.execute(applicant2);
        kieSession.execute(applicantList);

        assertFalse(applicant.isValid());
        assertTrue(applicant1.isValid());
        assertFalse(applicant2.isValid());

    }

}
