<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.spokesperson.form.label.full-name" path="identity.fullName" readonly="true"/>
	<acme:form-textbox code="any.spokesperson.form.label.cv" path="cv" readonly="true"/>
	<acme:form-textarea code="any.spokesperson.form.label.achievements" path="achievements" readonly="true"/>
	<acme:form-checkbox code="any.spokesperson.form.label.licensed" path="licensed" readonly="true"/>
</acme:form>