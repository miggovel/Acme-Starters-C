<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.campaign.list.label.ticker" path="ticker" width="20%"/>
	<acme:list-column code="any.campaign.list.label.start-moment" path="startMoment" width="20%"/>
	<acme:list-column code="any.campaign.list.label.name" path="name" width="60%"/>
	<acme:list-hidden path="endMoment"/>
	<acme:list-hidden path="effort"/>
	<acme:list-hidden path="spokesperson.identity.fullName"/>
</acme:list>