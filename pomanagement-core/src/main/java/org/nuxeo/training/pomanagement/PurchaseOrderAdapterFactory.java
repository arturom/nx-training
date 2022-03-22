package org.nuxeo.training.pomanagement;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.adapter.DocumentAdapterFactory;

public class PurchaseOrderAdapterFactory implements DocumentAdapterFactory {

    @Override
    public Object getAdapter(DocumentModel doc, Class<?> itf) {
        if ("PurchaseOrder".equals(doc.getType()) && doc.hasSchema("dublincore")){
            return new PurchaseOrderAdapter(doc);
        }else{
            return null;
        }
    }
}
