package org.nuxeo.training.pomanagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.runtime.RuntimeService;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.PartialDeploy;
import org.nuxeo.runtime.test.runner.RuntimeFeature;
import org.nuxeo.runtime.test.runner.TargetExtensions;

import javax.inject.Inject;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(FeaturesRunner.class)
@Features(AutomationFeature.class)
@Deploy("org.nuxeo.training.pomanagement.pomanagement-core")
@PartialDeploy(bundle = "studio.extensions.amejia-training",
        extensions = { TargetExtensions.ContentModel.class, TargetExtensions.Automation.class })
public class TestPurchaseOrders {

    @Inject
    protected CoreSession session;

    @Inject
    protected AutomationService automationService;

    @Test
    public void shouldDeployNuxeoRuntime() {
        RuntimeService runtime = Framework.getRuntime();
        assertNotNull(runtime);
    }

    @Test
    public void shouldHaveADescription() {
        DocumentModel doc = session.createDocumentModel("/", "A Purchase Order", "PurchaseOrder");
        doc.setPropertyValue("purchaseorder:price", 100.0);
        doc.setPropertyValue("purchaseorder:quantity", 1);
        doc.setPropertyValue("purchaseorder:product", "COMPUTER/COMP_LAPTOP");

        doc = session.createDocument(doc);
        assertNotNull(doc.getPropertyValue("dc:description"));
        session.save();
    }

    @Test
    public void shouldSetADefaultDescription() throws OperationException {
        DocumentModel doc = session.createDocumentModel("/", "A Purchase Order", "PurchaseOrder");
        doc.setPropertyValue("purchaseorder:price", 100.0);
        doc.setPropertyValue("purchaseorder:quantity", 1);
        doc.setPropertyValue("purchaseorder:product", "COMPUTER/COMP_LAPTOP");

        doc = session.createDocument(doc);

        OperationContext context = new OperationContext(session);
        context.setInput(doc);
        context.setCoreSession(session);

        doc = (DocumentModel) automationService.run(context, "AC_PurchaseOrder_SetDescription");
        String desc = (String) doc.getPropertyValue("dc:description");

        assertTrue(desc.startsWith("PO_"));
    }

    @Test
    public void shouldHaveCorrectTitle() {
        DocumentModel doc = session.createDocumentModel("/", "Prestigious PO", "PurchaseOrder");
        doc.setPropertyValue("purchaseorder:price", 100.0);
        doc.setPropertyValue("purchaseorder:quantity", 1);
        doc.setPropertyValue("purchaseorder:product", "COMPUTER/COMP_LAPTOP");

        doc = session.createDocument(doc);

        assertEquals("Prestigious PO", doc.getTitle());
    }
}
