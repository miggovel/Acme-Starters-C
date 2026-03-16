<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="spokesperson.campaign.list.label.ticker" path="ticker" width="20%"/>
	<acme:list-column code="spokesperson.campaign.list.label.name" path="name" width="40%"/>
	<acme:list-column code="spokesperson.campaign.list.label.get-effort" path="getEffort" width="20%"/>
	<acme:list-column code="spokesperson.campaign.list.label.draft-mode" path="draftMode" width="20%"/>
	<acme:list-hidden path="startMoment"/>
	<acme:list-hidden path="endMoment"/>
</acme:list>

<acme:button code="spokesperson.campaign.list.button.create" action="/spokesperson/campaign/create"/>