<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>

	<acme:list-column code="any.sponsorship.list.label.ticker" path="ticker" width="20%"/>

	<acme:list-column code="any.sponsorship.list.label.start-moment" path="startMoment" width="20%"/>

	<acme:list-column code="any.sponsorship.list.label.name" path="name" width="60%"/>

	<acme:list-hidden path="endMoment"/>

	<acme:list-hidden path="monthsActive"/>

	<acme:list-hidden path="totalMoney"/>

	<acme:list-hidden path="sponsor.identity.fullName"/>

</acme:list>