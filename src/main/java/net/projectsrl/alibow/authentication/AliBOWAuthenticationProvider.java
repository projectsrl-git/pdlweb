
package net.projectsrl.alibow.authentication;

import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.bow.authentication.BOWAuthenticationProvider;
import net.projectsrl.webapp.security.WebAppUserSecurityInfo;

public class AliBOWAuthenticationProvider extends BOWAuthenticationProvider {

    public AliBOWAuthenticationProvider() {
        super();
    }

    public AliBOWAuthenticationProvider(String configName) {
        super(configName);
    }

    @Override
    protected void setSpecificUserSecurityInfo(WebAppUserSecurityInfo<Integer> userInfo, Row_itf dbRow)
            throws AppCrash {

        super.setSpecificUserSecurityInfo(userInfo, dbRow);

        String includedFooter = Config.GetInstance().getProperty("Page.Footer.PdLWeb.include");
        userInfo.setField(Constants_itf.INCLUDED_FOOTER_PDLWEB, includedFooter);

    }

}
