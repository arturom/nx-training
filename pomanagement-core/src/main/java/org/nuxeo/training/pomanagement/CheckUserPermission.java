package org.nuxeo.training.pomanagement;

import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.usermanager.UserManager;

/**
 *
 */
@Operation(id=CheckUserPermission.ID, category="ACME", label="Check User Permission", description="Describe here what your operation does.")
public class CheckUserPermission {

    public static final String ID = "Document.CheckUserPermission";

    @Context
    protected CoreSession session;
    @Context
    protected OperationContext ctx;

    @Context
    protected UserManager userManager;

    @Param(name = "username", required = true)
    protected String username;

    @Param(name = "permission", required = true)
    protected String permission;

    @Param(name = "docId", required = true)
    protected String docId;

    @Param(name = "variableName", required = true)
    protected String variableName;

    @OperationMethod
    public void run() {
        NuxeoPrincipal pcipal = userManager.getPrincipal(username);
        boolean hasPermission = session.hasPermission(pcipal, new IdRef(docId), permission);
        ctx.put(variableName, hasPermission);
    }
}
