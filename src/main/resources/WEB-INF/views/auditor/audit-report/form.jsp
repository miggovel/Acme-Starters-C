<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<!-- Campos de AuditReport -->
	<acme:form-textbox code="auditor.audit-report.form.ticker" path="ticker"/>
	<acme:form-textbox code="auditor.audit-report.form.name" path="name"/>
	<acme:form-textarea code="auditor.audit-report.form.description" path="description"/>

	<acme:form-moment code="auditor.audit-report.form.startMoment" path="startMoment"/>
	<acme:form-moment code="auditor.audit-report.form.endMoment" path="endMoment"/>
	<acme:form-url code="auditor.audit-report.form.moreInfo" path="moreInfo"/>
	

	<acme:form-checkbox code="auditor.audit-report.form.published" path="published"/>

	<!-- Botones -->
	<jstl:choose>

		<jstl:when test="${_command == 'show' && published == true}">
        	<acme:button code="auditor.audit-report.form.button.sections" action="/auditor/audit-section/list?auditReportId=${id}"/>
    	</jstl:when>

    	<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && published == false}">
        	<acme:button code="auditor.audit-report.form.button.sections" action="/auditor/audit-section/list?auditReportId=${id}"/>
        	<acme:submit code="auditor.audit-report.form.button.update" action="/auditor/audit-report/update"/>
        	<acme:submit code="auditor.audit-report.form.button.delete" action="/auditor/audit-report/delete"/>
        	<acme:submit code="auditor.audit-report.form.button.publish" action="/auditor/audit-report/publish"/>
    	</jstl:when>

    	<jstl:when test="${_command == 'create'}">
	        <acme:submit code="auditor.audit-report.form.button.create" action="/auditor/audit-report/create"/>
    	</jstl:when>

	</jstl:choose>

</acme:form>