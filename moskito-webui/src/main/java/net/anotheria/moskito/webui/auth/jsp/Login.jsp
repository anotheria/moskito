<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>${title}</title>
        <link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/ext/bootstrap-3.1.1/css/bootstrap.css" />
        <link type="text/css" rel="stylesheet" href="../moskito/ext/font-awesome-4.3.0/css/font-awesome.css" />
        <link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/font/style.css" />
        <link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/ext/select2-3.4.6/select2.css" />
        <link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/int/css/common.css" />
        <!--[if lt IE 8]><link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/static-int/css/bootstrap-ie7.css" /><![endif]-->
        <link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/int/css/auth.css"/>
    </head>

    <body>

        <div class="login__container">

            <div class="login__content">

                <div class="login__block login__titleContainer">
                    <img src="${pageContext.request.contextPath}/moskito/int/img/moskito.png" height="200">
                    <h1>MoSKito Inspect</h1>
                </div>

                <div class="login__block login__formContainer">

                    <form action="mskSignIn" method="post">

                        <c:if test="${hasError}">
                        <span class="login__error">
                            Username or password is invalid
                        </span>
                        </c:if>

                        <label>
                            <input class="form-control" placeholder="username" type="text" name="username" required>
                        </label>

                        <label>
                            <input class="form-control" placeholder="password" type="password" name="password" required>
                        </label>

                        <button class="btn btn-primary" type="submit">Sign In</button>

                    </form>

                </div>

            </div>

        </div>

    </body>

</html>