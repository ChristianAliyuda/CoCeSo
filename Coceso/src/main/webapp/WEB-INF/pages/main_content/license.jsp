<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!--
/**
* CoCeSo
* Client HTML License information
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
*/
-->
<html lang="en">
<head>
    <title><spring:message code="label.main.license" /></title>
    <meta charset="utf-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link rel="stylesheet" href="<c:url value="/static/css/coceso.css"/>" type="text/css" />
</head>
<body>
<div class="container">
    <div class="ajax_content">
        <p class="alert alert-success">
            This client application for <a href="https://sourceforge.net/projects/coceso/" target="_blank">CoCeSo</a> is licensed under <a href="http://www.opensource.org/licenses/mit-license.php" target="_blank"><em>The MIT License</em></a>.
        </p>

        <p>
            It also makes use of the following software components (in parts or complete):
        </p>

        <table class="table table-striped">
            <tr>
                <th></th>
                <th>Software</th>
                <th>Version</th>
                <th>License</th>
            </tr>
            <tr>
                <td class="license_icon"><img src="https://jquery.com/jquery-wp-content/themes/jquery.com/i/favicon.ico" alt="" /></td>
                <td><a href="http://jquery.com/" target="_blank">jQuery</a></td>
                <td>1.10.2</td>
                <td><a href="http://www.opensource.org/licenses/mit-license.php" target="_blank">MIT License</a></td>
            </tr>
            <tr>
                <td class="license_icon"><img src="https://jqueryui.com/jquery-wp-content/themes/jqueryui.com/i/favicon.ico" alt="" /></td>
                <td><a href="http://jqueryui.com/" target="_blank">jQuery UI</a></td>
                <td>1.10.3</td>
                <td><a href="http://www.opensource.org/licenses/mit-license.php" target="_blank">MIT License</a></td>
            </tr>
            <tr>
                <td class="license_icon"><img src="http://getbootstrap.com/docs-assets/ico/favicon.png" alt="" /></td>
                <td><a href="http://addyosmani.github.io/jquery-ui-bootstrap/" target="_blank">jQuery UI Bootstrap</a></td>
                <td>1.0 Alpha</td>
                <td><a href="http://www.opensource.org/licenses/mit-license.php" target="_blank">MIT License</a></td>
            </tr>
            <tr>
                <td class="license_icon"><img src="http://getbootstrap.com/docs-assets/ico/favicon.png" alt="" /></td>
                <td><a href="http://addyosmani.github.io/jquery-ui-bootstrap/" target="_blank">Bootstrap</a></td>
                <td>3.0.2</td>
                <td><a href="http://www.opensource.org/licenses/mit-license.php" target="_blank">MIT License</a></td>
            </tr>
            <tr>
                <td class="license_icon"><img src="http://knockoutjs.com/img/favicon.ico" alt="" /></td>
                <td><a href="http://knockoutjs.com/" target="_blank">KnockoutJS</a></td>
                <td>3.0.0</td>
                <td><a href="http://www.opensource.org/licenses/mit-license.php" target="_blank">MIT License</a></td>
            </tr>
            <tr>
                <td class="license_icon"><img src="http://knockoutjs.com/img/favicon.ico" alt="" /></td>
                <td><a href="https://github.com/SteveSanderson/knockout.mapping" target="_blank">KnockoutJS Mapping Plugin</a></td>
                <td>2.4.1</td>
                <td><a href="http://www.opensource.org/licenses/mit-license.php" target="_blank">MIT License</a></td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>