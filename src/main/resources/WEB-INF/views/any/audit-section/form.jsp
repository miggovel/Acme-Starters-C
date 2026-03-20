<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">

	<acme:form-textbox  code="any.audit-section.form.name"  path="name"/>
	<acme:form-textarea code="any.audit-section.form.notes" path="notes"/>
	<acme:form-integer  code="any.audit-section.form.hours" path="hours"/>
	<acme:form-select   code="any.audit-section.form.kind"  path="kind" choices="${kinds}"/>

</acme:form>																					