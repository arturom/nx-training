package org.nuxeo.training.pomanagement;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.training.pomanagement.PurchaseOrderAdapter;

@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@Deploy({"org.nuxeo.training.pomanagement.pomanagement-core"})
public class TestPurchaseOrderAdapter {
  @Inject
  CoreSession session;

  @Test
  public void shouldCallTheAdapter() {
    String doctype = "PurchaseOrder";
    String testTitle = "My Adapter Title";

    DocumentModel doc = session.createDocumentModel("/", "test-adapter", doctype);
    PurchaseOrderAdapter adapter = doc.getAdapter(PurchaseOrderAdapter.class);
    adapter.setTitle(testTitle);
    Assert.assertEquals("Document title does not match when using the adapter", testTitle, adapter.getTitle());
  }
}
