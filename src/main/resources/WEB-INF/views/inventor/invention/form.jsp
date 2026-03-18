<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<jstl:choose>
		<jstl:when test="${!draftMode && _command != 'create'}">
			<acme:form-textbox code="inventor.invention.form.label.ticker" path="ticker" readonly="true"/>
			<acme:form-textbox code="inventor.invention.form.label.name" path="name" readonly="true"/>
			<acme:form-moment code="inventor.invention.form.label.startMoment" path="startMoment" readonly="true"/>
			<acme:form-moment code="inventor.invention.form.label.endMoment" path="endMoment" readonly="true"/>
			<acme:form-url code="inventor.invention.form.label.moreInfo" path="moreInfo" readonly="true"/>
			<acme:form-textarea code="inventor.invention.form.label.description" path="description" readonly="true"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:form-textbox code="inventor.invention.form.label.ticker" path="ticker"/>
			<acme:form-textbox code="inventor.invention.form.label.name" path="name"/>
			<acme:form-moment code="inventor.invention.form.label.startMoment" path="startMoment"/>
			<acme:form-moment code="inventor.invention.form.label.endMoment" path="endMoment"/>
			<acme:form-url code="inventor.invention.form.label.moreInfo" path="moreInfo"/>
			<acme:form-textarea code="inventor.invention.form.label.description" path="description"/>
		</jstl:otherwise>
	</jstl:choose>

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="inventor.invention.form.button.create" action="create"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'show' || _command == 'update' || _command == 'publish'}">
			
			<jstl:if test="${draftMode}">
				<acme:submit code="inventor.invention.form.button.update" action="update"/>
				<acme:submit code="inventor.invention.form.button.delete" action="delete"/>
				<acme:submit code="inventor.invention.form.button.publish" action="publish"/>
			</jstl:if>
			
			<acme:button code="inventor.invention.form.button.part" action="/inventor/part/list?inventionId=${id}"/>			
		</jstl:when>
	</jstl:choose>
</acme:form>