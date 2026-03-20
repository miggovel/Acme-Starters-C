<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>

	<acme:list-column code="auditor.audit-report.form.ticker" path="ticker" width="20%"/>
	<acme:list-column code="auditor.audit-report.form.name" path="name" width="40%"/>
	<acme:list-column code="auditor.audit-report.form.startMoment" path="startMoment" width="20%"/>
	<acme:list-column code="auditor.audit-report.form.endMoment" path="endMoment" width="20%"/>

	<!-- Oculto -->
	<acme:list-hidden path="description"/>

</acme:list>

<jstl:if test="${showCreate}">
	<acme:button code="auditor.audit-report.form.button.create" action="/auditor/audit-report/create"/>
</jstl:if>