<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.invention.list.label.ticker" path="ticker" width="15%"/>
	<acme:list-column code="any.invention.list.label.name" path="name" width="25%"/>
	<acme:list-column code="any.invention.list.label.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="any.invention.list.label.endMoment" path="endMoment" width="15%"/>
	<acme:list-column code="any.invention.list.label.cost" path="cost" width="10%"/>
</acme:list>
