<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
    <!-- Columnas visibles -->
    <acme:list-column code="sponsor.sponsorship.list.label.ticker" path="ticker" width="20%"/>
    <acme:list-column code="sponsor.sponsorship.list.label.name" path="name" width="40%"/>
    <acme:list-column code="sponsor.sponsorship.list.label.total-money" path="totalMoney.amount" width="20%"/>
    <acme:list-column code="sponsor.sponsorship.list.label.draft-mode" path="draftMode" width="20%"/>

    <!-- Campos ocultos -->
    <acme:list-hidden path="startMoment"/>
    <acme:list-hidden path="endMoment"/>
</acme:list>

<!-- Botón de crear -->
<acme:button code="sponsor.sponsorship.list.button.create" action="/sponsor/sponsorship/create"/>