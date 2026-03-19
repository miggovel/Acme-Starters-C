<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="inventor.part.form.label.name" path="name"/>
	<acme:form-select code="inventor.part.form.label.kind" path="kind" choices="${kinds}"/>
	<acme:form-money code="inventor.part.form.label.cost" path="cost"/>
	<acme:form-textarea code="inventor.part.form.label.description" path="description"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update') && draftMode == true}">
			<acme:submit code="inventor.part.form.button.update" action="update"/>
			<acme:submit code="inventor.part.form.button.delete" action="delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="inventor.part.form.button.create" action="create?inventionId=${inventionId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>