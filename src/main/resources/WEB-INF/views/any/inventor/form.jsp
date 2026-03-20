<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="any.inventor.form.label.full-name" path="identity.fullName" readonly="true"/>
	<acme:form-textbox code="any.inventor.form.label.email" path="identity.email" readonly="true"/>
	<acme:form-textarea code="any.inventor.form.label.bio" path="bio" readonly="true"/>
	<acme:form-textbox code="any.inventor.form.label.key-words" path="keyWords" readonly="true"/>
	<acme:form-checkbox code="any.inventor.form.label.licensed" path="licensed" readonly="true"/>
</acme:form>
