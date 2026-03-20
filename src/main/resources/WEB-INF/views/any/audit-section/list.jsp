<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>

	<acme:list-column code="any.audit-section.form.name" path="name" width="40%"/>
	<acme:list-column code="any.audit-section.form.kind" path="kind" width="30%"/>
	<acme:list-column code="any.audit-section.form.hours" path="hours" width="30%"/>

</acme:list>