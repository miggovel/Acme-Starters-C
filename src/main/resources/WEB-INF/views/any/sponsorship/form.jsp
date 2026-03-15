<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:form-textbox code="any.sponsorship.form.label.ticker" path="ticker" readonly="true"/>

	<acme:form-textbox code="any.sponsorship.form.label.name" path="name" readonly="true"/>

	<acme:form-textarea code="any.sponsorship.form.label.description" path="description" readonly="true"/>

	<acme:form-moment code="any.sponsorship.form.label.start-moment" path="startMoment" readonly="true"/>

	<acme:form-moment code="any.sponsorship.form.label.end-moment" path="endMoment" readonly="true"/>

	<acme:form-url code="any.sponsorship.form.label.more-info" path="moreInfo" readonly="true"/>

	<acme:form-double code="any.sponsorship.form.label.months-active" path="monthsActive" readonly="true"/>

	<acme:form-money code="any.sponsorship.form.label.total-money" path="totalMoney" readonly="true"/>

	<acme:form-checkbox code="any.sponsorship.form.label.draft-mode" path="draftMode" readonly="true"/>

	<acme:button code="any.sponsorship.form.button.sponsor-profile" action="/any/sponsor/show?id=${sponsorId}"/>

</acme:form>