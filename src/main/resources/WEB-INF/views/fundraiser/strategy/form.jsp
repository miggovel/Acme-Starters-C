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

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="fundraiser.strategy.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="fundraiser.strategy.form.label.name" path="name"/>
	<acme:form-textarea code="fundraiser.strategy.form.label.description" path="description"/>
	<acme:form-moment code="fundraiser.strategy.form.label.start-moment" path="startMoment"/>
	<acme:form-moment code="fundraiser.strategy.form.label.end-moment" path="endMoment"/>
	<acme:form-url code="fundraiser.strategy.form.label.more-info" path="moreInfo"/>
	<acme:form-double code="fundraiser.strategy.form.label.months-active" path="monthsActive" readonly="true"/>
	<acme:form-double code="fundraiser.strategy.form.label.expected-percentage" path="expectedPercentage" readonly="true"/>

	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="fundraiser.strategy.form.button.tactics" action="/fundraiser/tactic/list?strategyId=${id}"/>
			<acme:button code="fundraiser.strategy.form.button.fundraiser-profile" action="/any/fundraiser/show?id=${fundraiserId}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="fundraiser.strategy.form.button.tactics" action="/fundraiser/tactic/list?strategyId=${id}"/>
			<acme:submit code="fundraiser.strategy.form.button.update" action="/fundraiser/strategy/update"/>
			<acme:submit code="fundraiser.strategy.form.button.delete" action="/fundraiser/strategy/delete"/>
			<acme:submit code="fundraiser.strategy.form.button.publish" action="/fundraiser/strategy/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="fundraiser.strategy.form.button.create" action="/fundraiser/strategy/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
