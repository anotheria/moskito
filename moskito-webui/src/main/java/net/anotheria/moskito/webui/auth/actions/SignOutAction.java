package net.anotheria.moskito.webui.auth.actions;

import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.auth.AuthConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOutAction implements Action {

    @Override
    public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

    }

    @Override
    public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

        req.getSession().removeAttribute(AuthConstants.AUTH_SESSION_ATTR_NAME);
        Cookie authCookie = new Cookie(AuthConstants.AUTH_COOKIE_NAME, "");
        authCookie.setMaxAge(0);
        res.addCookie(authCookie);

        return mapping.redirect();

    }

    @Override
    public void postProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

    }

}
