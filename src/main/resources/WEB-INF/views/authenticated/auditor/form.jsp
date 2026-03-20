<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:form-textbox code="authenticated.auditor.form.firm" path="firm"/>
	<acme:form-textarea code="authenticated.auditor.form.highlights" path="highlights"/>
	<acme:form-checkbox code="authenticated.auditor.form.solicitor" path="solicitor"/>

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.auditor.form.button.create" action="/authenticated/auditor/create"/>
		</jstl:when>
		<jstl:when test="${_command == 'show' || _command == 'update'}">
			<acme:submit code="authenticated.auditor.form.button.update" action="/authenticated/auditor/update"/>
		</jstl:when>
	</jstl:choose>

</acme:form>