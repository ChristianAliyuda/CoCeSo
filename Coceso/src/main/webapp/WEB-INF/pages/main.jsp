<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<!--
/**
* CoCeSo
* Client HTML main interface
* Copyright (c) WRK\Daniel Rohr
*
* Licensed under The MIT License
* For full copyright and license information, please see the LICENSE.txt
* Redistributions of files must retain the above copyright notice.
*
* @copyright     Copyright (c) 2013 Daniel Rohr
* @link          https://sourceforge.net/projects/coceso/
* @package       coceso.client.html
* @since         Rev. 1
* @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
*
* Dependencies:
*	coceso.client.css
*  coceso.client.js
*  bootstrap.dropdown.js
*/
-->
<html lang="en">
<head>
    <title>CoCeSo</title>
    <meta charset="utf-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />


    <link rel="stylesheet" href="<c:url value="/static/css/coceso.css" />" type="text/css" />

    <script src="<c:url value="/static/js/jquery.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/js/jquery.ui.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/js/jquery.ui.winman.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/js/knockout.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/js/knockout.mapping.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/js/bootstrap.dropdown.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/js/bindings.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/js/coceso.js"/>" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            Coceso.Conf.jsonBase = "${pageContext.request.contextPath}/data/";
            Coceso.Conf.contentBase = "${pageContext.request.contextPath}/static/content/";

            Coceso.startup();
        });
    </script>
</head>
<body>
<header>
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="navbar-header">
            <a class="navbar-brand">CoCeSo</a>
        </div>

        <ul class="nav navbar-nav">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Units <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="#" title="Units" onclick="return Coceso.UI.openUnits(this.title, 'units.html');">Overview</a></li>
                    <li><a href="#" title="Assigned Units" onclick="return Coceso.UI.openUnits(this.title, 'units.html', {filter: ['radio']});">Assigned Units (Radio operator)</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Incidents <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="#" title="Add Incident" onclick="return Coceso.UI.openIncident(this.title, 'incident_form.html');">Add</a></li>
                    <li><a href="#" title="Active Incidents" onclick="return Coceso.UI.openIncidents(this.title, 'incidents.html', {filter: ['overview', 'active']});">Active Incidents</a></li>
                    <li><a href="#" title="New Incidents" onclick="return Coceso.UI.openIncidents(this.title, 'incidents.html', {filter: ['overview', 'new'], showTabs: false});">New Incidents (Calltaker)</a></li>
                    <li><a href="#" title="Open Incidents" onclick="return Coceso.UI.openIncidents(this.title, 'incidents.html', {filter: ['overview', 'open'], showTabs: false});">Open Incidents (Dispo)</a></li>
                    <li><a href="#" title="Completed Incidents" onclick="return Coceso.UI.openIncidents(this.title, 'incidents.html', {filter: ['overview', 'completed']});">Completed Incidents</a></li>
                </ul>
            </li>
            <li><a href="#" title="Logs" onclick="return Coceso.UI.openLogs(this.title, 'log.html');">Logs</a></li>
            <li><a href="<c:url value="/edit/"/>" target="_blank">Admin</a></li>
            <li><a href="<c:url value="/dashboard"/>" target="_blank">Dashboard</a></li>
            <li><a href="#" title="License" onclick="return Coceso.UI.openStatic(this.title, 'license.html');">License</a></li>
        </ul>
    </nav>
</header>

<noscript>
    <div class="alert alert-danger"><strong>JavaScript required</strong><br/>Enable JavaScript to use this page.</div>
</noscript>

<footer>
    <ul id="taskbar"></ul>
</footer>
</body>
</html>
