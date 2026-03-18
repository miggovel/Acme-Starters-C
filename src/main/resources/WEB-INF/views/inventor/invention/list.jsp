<%--  <%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="inventor.invention.list.label.ticker" path="ticker" width="15%" />
	<acme:list-column code="inventor.invention.list.label.name" path="name" width="25%" />
	<acme:list-column code="inventor.invention.list.label.startMoment" path="startMoment" width="15%" />
	<acme:list-column code="inventor.invention.list.label.endMoment" path="endMoment" width="15%" />
	<acme:list-column code="inventor.invention.list.label.cost" path="cost" width="10%" />
</acme:list>

<acme:button code="inventor.invention.list.button.create" action="/inventor/invention/create" />

--%>

<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="inventor.invention.list.label.ticker" path="ticker" width="15%"/>
	<acme:list-column code="inventor.invention.list.label.name" path="name" width="25%"/>
	<acme:list-column code="inventor.invention.list.label.draftMode" path="draftMode" width="10%"/>
	<acme:list-column code="inventor.invention.list.label.startMoment" path="startMoment" width="20%"/>
	<%-- Propiedad derivada expuesta de forma segura --%>
	<acme:list-column code="inventor.invention.list.label.cost" path="cost" width="10%"/>
</acme:list>

<acme:button code="inventor.invention.list.button.create" action="/inventor/invention/create"/>

