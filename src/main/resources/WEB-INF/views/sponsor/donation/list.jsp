<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
    <!-- Columnas visibles -->
    <acme:list-column code="sponsor.donation.list.label.name" path="name" width="40%"/>
    <acme:list-column code="sponsor.donation.list.label.kind" path="kind" width="30%"/>
    <acme:list-column code="sponsor.donation.list.label.money" path="money.amount" width="30%"/>

    <!-- Campos ocultos -->
    <acme:list-hidden path="notes"/>
</acme:list>

<jstl:if test="${showCreate}">
    <acme:button code="sponsor.donation.list.button.create" action="/sponsor/donation/create?sponsorshipId=${sponsorshipId}"/>
</jstl:if>