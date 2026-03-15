<%--
- form.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.strategy.form.label.ticker" path="ticker" readonly="true"/>
	<acme:form-textbox code="any.strategy.form.label.name" path="name" readonly="true"/>
	<acme:form-textarea code="any.strategy.form.label.description" path="description" readonly="true"/>
	<acme:form-moment code="any.strategy.form.label.start-moment" path="startMoment" readonly="true"/>
	<acme:form-moment code="any.strategy.form.label.end-moment" path="endMoment" readonly="true"/>
	<acme:form-url code="any.strategy.form.label.more-info" path="moreInfo" readonly="true"/>
	<acme:form-double code="any.strategy.form.label.months-active" path="monthsActive" readonly="true"/>
	<acme:form-double code="any.strategy.form.label.expected-percentage" path="expectedPercentage" readonly="true"/>

	<acme:button code="any.strategy.form.button.tactics" action="/any/tactic/list?strategyId=${id}"/>
	<acme:button code="any.strategy.form.button.fundraiser-profile" action="/any/fundraiser/show?id=${fundraiserId}"/>
</acme:form>
