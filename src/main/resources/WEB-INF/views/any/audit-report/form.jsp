<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">

	<acme:form-textbox code="any.audit-report.form.ticker" path="ticker"/>
	<acme:form-textbox code="any.audit-report.form.name" path="name"/>
	<acme:form-textarea code="any.audit-report.form.description" path="description"/>

	<acme:form-moment code="any.audit-report.form.startMoment" path="startMoment"/>
	<acme:form-moment code="any.audit-report.form.endMoment" path="endMoment"/>
	<acme:form-url code="any.audit-report.form.moreInfo" path="moreInfo"/>	

	<!-- Auditor -->
	<acme:form-textbox code="any.audit-report.form.auditorFirm" path="auditorFirm"/>
	<acme:form-textbox code="any.audit-report.form.auditorHighlights" path="auditorHighlights"/>
	<acme:form-checkbox code="any.audit-report.form.auditorSolicitor" path="auditorSolicitor"/>

	<acme:button code="any.audit-report.form.button.sections" action="/any/audit-section/list?auditReportId=${auditReportId}"/>
</acme:form>