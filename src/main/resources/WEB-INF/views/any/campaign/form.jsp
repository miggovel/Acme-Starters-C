<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.campaign.form.label.ticker" path="ticker" readonly="true"/>
	<acme:form-textbox code="any.campaign.form.label.name" path="name" readonly="true"/>
	<acme:form-textarea code="any.campaign.form.label.description" path="description" readonly="true"/>
	<acme:form-moment code="any.campaign.form.label.start-moment" path="startMoment" readonly="true"/>
	<acme:form-moment code="any.campaign.form.label.end-moment" path="endMoment" readonly="true"/>
	<acme:form-url code="any.campaign.form.label.more-info" path="moreInfo" readonly="true"/>
	<acme:form-double code="any.campaign.form.label.months-active" path="monthsActive" readonly="true"/>
	<acme:form-double code="any.campaign.form.label.effort" path="effort" readonly="true"/>

	<acme:button code="any.campaign.form.button.milestones" action="/any/milestone/list?campaignId=${id}"/>
	<acme:button code="any.campaign.form.button.spokesperson-profile" action="/any/spokesperson/show?id=${spokespersonId}"/>
</acme:form>