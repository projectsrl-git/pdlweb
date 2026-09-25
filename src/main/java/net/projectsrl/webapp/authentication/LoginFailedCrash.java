
package net.projectsrl.webapp.authentication;

import net.project.errors.AppCrash;

public class LoginFailedCrash extends AppCrash {

    private static final long serialVersionUID = -9012365240447915352L;

    public LoginFailedCrash(Throwable error) {
        super(error);
    }

    public LoginFailedCrash(String mess) {
        super(mess);
    }

}
