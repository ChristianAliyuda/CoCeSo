<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%--
/**
 * CoCeSo
 * Client HTML dashboard log table
 * Copyright (c) WRK\Coceso-Team
 *
 * Licensed under the GNU General Public License, version 3 (GPL-3.0)
 * Redistributions of files must retain the above copyright notice.
 *
 * @copyright Copyright (c) 2014 WRK\Coceso-Team
 * @link https://sourceforge.net/projects/coceso/
 * @license GPL-3.0 ( http://opensource.org/licenses/GPL-3.0 )
 */
--%>

<c:url var="get_inc" value="?iid="/>
<c:url var="get_unit" value="?uid="/>

<table class="table table-striped table-hover">
  <thead>
    <tr>
      <th><spring:message code="label.log.timestamp"/></th>
      <th><spring:message code="label.operator"/></th>
      <th><spring:message code="label.log.text"/></th>
      <th><spring:message code="label.incident"/></th>
      <th><spring:message code="label.unit"/></th>
      <th><spring:message code="label.task.state"/></th>
      <th><spring:message code="label.log.changes"/></th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${logs}" var="log">
      <tr>
        <td><fmt:formatDate type="both" dateStyle="short" timeStyle="medium" value="${log.timestamp}"/></td>
        <td><c:out value="${log.user.username}"/></td>
        <td class="log_text"><t:logtext log="${log}"/></td>
        <td>
          <c:if test="${not empty log.incident}">
            <a href="${get_inc}${log.incident.id}"><t:inctitle incident="${log.incident}"/></a>
          </c:if>
        </td>
        <td>
          <c:if test="${not empty log.unit}">
            <a href="${get_unit}${log.unit.id}"><c:out value="${log.unit.call}"/></a>
          </c:if>
        </td>
        <td>
          <c:if test="${not empty log.state}">
            <spring:message code="label.task.state.${fn:toLowerCase(log.state)}" text="${log.state}"/>
          </c:if>
        </td>
        <td><t:changes container="${log.changes}"/></td>
      </tr>
    </c:forEach>
  </tbody>
</table>