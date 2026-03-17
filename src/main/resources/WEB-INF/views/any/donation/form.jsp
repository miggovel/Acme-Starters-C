<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:form-textbox code="any.donation.form.label.name" path="name" readonly="true"/>

	<acme:form-select code="any.donation.form.label.kind" path="kind" choices="${kinds}" readonly="true"/>

	<acme:form-money code="any.donation.form.label.money" path="money" readonly="true"/>

	<acme:form-textarea code="any.donation.form.label.notes" path="notes" readonly="true"/>

</acme:form>