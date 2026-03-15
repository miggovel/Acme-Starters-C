<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <!-- Campos de Donation -->
    <acme:form-textbox code="sponsor.donation.form.label.name" path="name"/>
    <acme:form-select code="sponsor.donation.form.label.kind" path="kind" choices="${kinds}"/>
    <acme:form-money code="sponsor.donation.form.label.money" path="money" placeholder="sponsor.donation.form.placeholder.money"/>
    <acme:form-textarea code="sponsor.donation.form.label.notes" path="notes"/>

    <!-- Botones según comando y draftMode -->
    <jstl:choose>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
            <acme:submit code="sponsor.donation.form.button.update" action="/sponsor/donation/update"/>
            <acme:submit code="sponsor.donation.form.button.delete" action="/sponsor/donation/delete"/>
        </jstl:when>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="sponsor.donation.form.button.create" action="/sponsor/donation/create?sponsorshipId=${sponsorshipId}"/>
        </jstl:when>
    </jstl:choose>
</acme:form>