package org.nuxeo.training.pomanagement;

import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentRef;

/**
 *
 */
public class PurchaseOrderAdapter {
  protected DocumentModel doc;

  protected String titleXpath = "dc:title";
  protected String descriptionXpath = "dc:description";

  public PurchaseOrderAdapter(DocumentModel doc) {
    this.doc = doc;
  }

  // Basic methods
  //
  // Note that we voluntarily expose only a subset of the DocumentModel API in this adapter.
  // You may wish to complete it without exposing everything!
  // For instance to avoid letting people change the document state using your adapter,
  // because this would be handled through workflows / buttons / events in your application.
  //

  public void save() {
    CoreSession session = doc.getCoreSession();
    doc = session.saveDocument(doc);
  }

  public DocumentRef getParentRef() {
    return doc.getParentRef();
  }

  // Technical properties retrieval
  public String getId() {
    return doc.getId();
  }

  public String getName() {
    return doc.getName();
  }

  public String getPath() {
    return doc.getPathAsString();
  }

  public String getState() {
    return doc.getCurrentLifeCycleState();
  }

  public void setProduct(String value) { doc.setPropertyValue("purchaseorder:product", value);}
  public void setQuantity(int value) { doc.setPropertyValue("purchaseorder:quantity", value);}
  public void setPrice(double value) { doc.setPropertyValue("purchaseorder:price", value);}

  public String getProduct() { return (String) doc.getPropertyValue("purchaseorder:product" ); }

  public void negotiate() {
    doc.followTransition("to_negotiating");
  }

  public void toDraft() {
    doc.followTransition("to_draft");
  }

  public void approve() {
    doc.followTransition("to_approved");
  }

  // Metadata get / set
  public String getTitle() {
    return doc.getTitle();
  }

  public void setTitle(String value) {
    doc.setPropertyValue(titleXpath, value);
  }

  public String getDescription() {
    return (String) doc.getPropertyValue(descriptionXpath);
  }

  public void setDescription(String value) {
    doc.setPropertyValue(descriptionXpath, value);
  }
}
