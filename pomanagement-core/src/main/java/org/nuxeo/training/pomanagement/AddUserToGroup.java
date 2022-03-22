package org.nuxeo.training.pomanagement;

import org.apache.commons.lang3.StringUtils;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.platform.usermanager.UserManager;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Operation(id=AddUserToGroup.ID, category="ACME", label="Add User to Group", description="Describe here what your operation does.")
public class AddUserToGroup {

    public static final String ID = "Document.AddUserToGroup";

    @Context
    protected CoreSession session;

    @Context
    protected UserManager userManager;

    @Param(name = "username", required = true)
    protected String username;

    @Param(name = "group", required = true)
    protected String group;

    @OperationMethod
    public DocumentModel run() {
        DocumentModel userModel = userManager.getUserModel(username);
        List<String> groups = (List<String>) userModel.getPropertyValue("user:groups");

        if (!groups.contains(group)) {
            groups.add(group);
        }
        userManager.updateUser(userModel);
        return userModel;
    }
}
