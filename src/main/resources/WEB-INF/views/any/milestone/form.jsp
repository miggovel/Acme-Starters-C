<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.milestone.form.label.title" path="title" readonly="true"/>
	<acme:form-select code="any.milestone.form.label.kind" path="kind" choices="${kinds}" readonly="true"/>
	<acme:form-double code="any.milestone.form.label.achievements" path="achievements" readonly="true"/>
	<acme:form-textarea code="any.milestone.form.label.effort" path="effort" readonly="true"/>
</acme:form>