package org.opentosca.containerapi.resources.csar.servicetemplate.instances.plans;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.namespace.QName;

import org.opentosca.containerapi.resources.utilities.ResourceConstants;
import org.opentosca.containerapi.resources.xlink.Reference;
import org.opentosca.containerapi.resources.xlink.References;
import org.opentosca.containerapi.resources.xlink.XLinkConstants;
import org.opentosca.core.model.csar.id.CSARID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * TODO implement
 * 
 * Copyright 2016 IAAS University of Stuttgart <br>
 * <br>
 * 
 * @author christian.endres@iaas.uni-stuttgart.de
 *
 */
public class PlanInstanceLogs {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(PlanInstanceLogs.class);
	
	private final CSARID csarID;
	private final QName serviceTemplateID;
	private final int serviceTemplateInstanceId;
	private final String correlationID;
	
	private UriInfo uriInfo;
	
	
	public PlanInstanceLogs(CSARID csarID, QName serviceTemplateID, int serviceTemplateInstanceId, String correlationID) {
		this.csarID = csarID;
		this.serviceTemplateID = serviceTemplateID;
		this.serviceTemplateInstanceId = serviceTemplateInstanceId;
		this.correlationID = correlationID;
	}
	
	/**
	 * Produces the xml which lists the CorrelationIDs of the active
	 * PublicPlans.
	 * 
	 * @param uriInfo
	 * @return The response with the legal PublicPlanTypes.
	 */
	@GET
	@Produces(ResourceConstants.LINKED_XML)
	public Response getReferencesXML(@Context UriInfo uriInfo) {
		this.uriInfo = uriInfo;
		return Response.ok(getReferences().getXMLString()).build();
	}
	
	/**
	 * Produces the JSON which lists the links to the History and the active
	 * plans.
	 * 
	 * @param uriInfo
	 * @return The response with the legal PublicPlanTypes.
	 */
	@GET
	@Produces(ResourceConstants.LINKED_JSON)
	public Response getReferencesJSON(@Context UriInfo uriInfo) {
		this.uriInfo = uriInfo;
		return Response.ok(getReferences().getJSONString()).build();
	}
	
	public References getReferences() {
		
		References refs = new References();
		
		// selflink
		refs.getReference().add(new Reference(uriInfo.getAbsolutePath().toString(), XLinkConstants.SIMPLE, XLinkConstants.SELF));
		return refs;
	}
	
}