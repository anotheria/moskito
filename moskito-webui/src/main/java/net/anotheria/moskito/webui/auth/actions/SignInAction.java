package net.anotheria.moskito.webui.auth.actions;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.action.CommandRedirect;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.auth.AuthConstants;
import net.anotheria.moskito.webui.auth.api.AuthApi;
import net.anotheria.moskito.webui.auth.api.UserAO;
import net.anotheria.moskito.webui.util.APILookupUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInAction implements Action {

    /**
     * Age of the authentication cookie.
     */
    private static final int AUTH_COOKIE_AGE = 60 * 60 * 24 * 30; // 30 days

    /**
     * Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(SignInAction.class);


    @Override
    public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

    }

    @Override
    public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

        if(req.getMethod().equals("POST")) {

            final AuthApi api = APILookupUtility.getAuthApi();

            UserAO user =
                    new UserAO(
                            req.getParameter("username"),
                            req.getParameter("password")
                    );

            if (api.userExists(user)) {

                req.getSession().setAttribute(AuthConstants.AUTH_SESSION_ATTR_NAME, true);
                String encryptedAuthCookie;

                try {
                    encryptedAuthCookie = api.encryptUserCredentials(user);
                } catch (APIException e) {
                    log.error("Failed to encrypt user data for writing cookie");
                    return mapping.error();
                }

                Cookie authCookie = new Cookie(AuthConstants.AUTH_COOKIE_NAME, encryptedAuthCookie);
                authCookie.setMaxAge(AUTH_COOKIE_AGE);
                res.addCookie(authCookie);

                Object refer = req.getSession().getAttribute(AuthConstants.LOGIN_REFER_PAGE_SESSION_ATTR_NAME);

                if (refer != null)
                    return new CommandRedirect("redirect", ((String) refer));
                else
                    return mapping.findCommand("defaultRedirect");

            } else{
                req.setAttribute("title", "Log in Moskito-Inspect");
                req.setAttribute("hasError", true);
                return mapping.findCommand("loginPage");
            }

        }
        else {
            req.setAttribute("title", "Log in Moskito-Inspect");
            return mapping.findCommand("loginPage");
        }

    }

    @Override
    public void postProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

    }
}
