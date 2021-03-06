package org.opentosca.planbuilder.bpel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opentosca.planbuilder.model.tosca.AbstractDeploymentArtifact;
import org.opentosca.planbuilder.model.tosca.AbstractImplementationArtifact;
import org.opentosca.planbuilder.model.tosca.AbstractNodeTemplate;
import org.opentosca.planbuilder.model.tosca.AbstractOperation;
import org.opentosca.planbuilder.model.tosca.AbstractParameter;
import org.opentosca.planbuilder.model.tosca.AbstractRelationshipTemplate;
import org.opentosca.planbuilder.plugins.IPlanBuilderPrePhaseDAPlugin;
import org.opentosca.planbuilder.plugins.IPlanBuilderPrePhaseIAPlugin;
import org.opentosca.planbuilder.plugins.IPlanBuilderProvPhaseOperationPlugin;
import org.opentosca.planbuilder.plugins.IPlanBuilderProvPhaseParamOperationPlugin;
import org.opentosca.planbuilder.plugins.context.TemplatePlanContext;
import org.opentosca.planbuilder.plugins.context.TemplatePlanContext.Variable;

/**
 * <p>
 * This Class is a wrapper class for the other wrapper classes
 * (IACandidateWrapper,DACandidateWrapper,..). The class also represents if
 * there are complete provisioning possible with the available template
 * implementations.
 * </p>
 * Copyright 2013 IAAS University of Stuttgart <br>
 * <br>
 * 
 * @author Kalman Kepes - kepeskn@studi.informatik.uni-stuttgart.de
 * 
 */
public class OperationChain {
	
	// this chain either holds a NodeTemplate or RelationshipTemplate
	AbstractNodeTemplate nodeTemplate;
	AbstractRelationshipTemplate relationshipTemplate;
	
	// lists for all other wrapper classes
	List<DANodeTypeImplCandidate> daCandidates = new ArrayList<DANodeTypeImplCandidate>();
	List<IANodeTypeImplCandidate> iaCandidates = new ArrayList<IANodeTypeImplCandidate>();
	List<OperationNodeTypeImplCandidate> provCandidates = new ArrayList<OperationNodeTypeImplCandidate>();
	
	
	/**
	 * <p>
	 * Constructor for a NodeTemplate
	 * </p>
	 * 
	 * @param nodeTemplate a NodeTemplate which the ProvisioningChain should
	 *            belong
	 */
	OperationChain(AbstractNodeTemplate nodeTemplate) {
		this.nodeTemplate = nodeTemplate;
	}
	
	/**
	 * <p>
	 * Constructor for a RelationshipTemplate
	 * </p>
	 * 
	 * @param relationshipTemplate a RelationshipTemplate which the
	 *            ProvisioningChain should belong
	 */
	OperationChain(AbstractRelationshipTemplate relationshipTemplate) {
		this.relationshipTemplate = relationshipTemplate;
	}
	
	/**
	 * <p>
	 * Executes the first found IACandidate to provision IA's with the
	 * appropiate plugins set in the candidate
	 * </p>
	 * 
	 * @param context a TemplatePlanContext which is initialized for either
	 *            a NodeTemplate or RelationshipTemplate this
	 *            ProvisioningChain belongs to
	 * @return returns false only when execution of a plugin inside the
	 *         IACandidate failed, else true. There may be no IACandidate
	 *         available, because there is no need for IA's to provision. In
	 *         this case true is also returned.
	 */
	public boolean executeIAProvisioning(TemplatePlanContext context) {
		boolean check = true;
		if (!this.iaCandidates.isEmpty()) {
			IANodeTypeImplCandidate iaCandidate = this.iaCandidates.get(0);
			for (int index = 0; index < iaCandidate.ias.size(); index++) {
				AbstractImplementationArtifact ia = iaCandidate.ias.get(index);
				AbstractNodeTemplate infraNode = iaCandidate.infraNodes.get(index);
				IPlanBuilderPrePhaseIAPlugin plugin = iaCandidate.plugins.get(index);
				check &= plugin.handle(context, ia, infraNode);
			}
		}
		return check;
	}
	
	/**
	 * <p>
	 * Executes the first found DACandidate to provision DA's with the
	 * appropiate plugins set in the candidate
	 * </p>
	 * 
	 * @param context a TemplatePlanContext which is initialized for either
	 *            a NodeTemplate or RelationshipTemplate this
	 *            ProvisioningChain belongs to
	 * @return returns false only when execution of a plugin inside the
	 *         DACandidate failed, else true. There may be no IACandidate
	 *         available, because there is no need for DA's to provision. In
	 *         this case true is also returned.
	 */
	public boolean executeDAProvisioning(TemplatePlanContext context) {
		boolean check = true;
		if (!this.daCandidates.isEmpty()) {
			DANodeTypeImplCandidate daCandidate = this.daCandidates.get(0);
			for (int index = 0; index < daCandidate.das.size(); index++) {
				AbstractDeploymentArtifact da = daCandidate.das.get(index);
				AbstractNodeTemplate infraNode = daCandidate.infraNodes.get(index);
				IPlanBuilderPrePhaseDAPlugin plugin = daCandidate.plugins.get(index);
				check &= plugin.handle(context, da, infraNode);
			}
		}
		return check;
	}
	
	/**
	 * <p>
	 * Executes the first found ProvisioningCandidate to execute
	 * provisioning operations with the appropiate plugins set in the
	 * candidate
	 * </p>
	 * 
	 * <p>
	 * <b>Info:</b> A ProvisioningCandidate may not have an appropiate order
	 * of operations set
	 * </p>
	 * 
	 * @param context a TemplatePlanContext which is initialized for either
	 *            a NodeTemplate or RelationshipTemplate this
	 *            ProvisioningChain belongs to
	 * @return returns false only when execution of a plugin inside the
	 *         ProvisioningCandidate failed, else true. There may be no
	 *         ProvisioningCandidate available, because there is no need for
	 *         operation to call. In this case true is also returned.
	 */
	public boolean executeOperationProvisioning(TemplatePlanContext context) {
		boolean check = true;
		if (!this.provCandidates.isEmpty()) {
			OperationNodeTypeImplCandidate provCandidate = this.provCandidates.get(0);
			for (int index = 0; index < provCandidate.ops.size(); index++) {
				AbstractOperation op = provCandidate.ops.get(index);
				AbstractImplementationArtifact ia = provCandidate.ias.get(index);
				IPlanBuilderProvPhaseOperationPlugin plugin = provCandidate.plugins.get(index);
				check &= plugin.handle(context, op, ia);
			}
		}
		return check;
	}
	
	/**
	 * <p>
	 * Executes the first found ProvisioningCandidate to execute
	 * provisioning operations with the appropiate plugins set in the
	 * candidate. The order of calling each operation provisioning is
	 * represented in the given list of strings
	 * </p>
	 * 
	 * @param context a TemplatePlanContext which is initialized for either
	 *            a NodeTemplate or RelationshipTemplate this
	 *            ProvisioningChain belongs to
	 * @param operationNames a List of String denoting an order of
	 *            operations (name attribute)
	 * @return returns false only when execution of a plugin inside the
	 *         ProvisioningCandidate failed, else true. There may be no
	 *         ProvisioningCandidate available, because there is no need for
	 *         operation to call. In this case true is also returned.
	 */
	public boolean executeOperationProvisioning(TemplatePlanContext context, List<String> operationNames) {
		boolean check = true;
		if (!this.provCandidates.isEmpty()) {
			OperationNodeTypeImplCandidate provCandidate = this.provCandidates.get(0);
			Map<String, Integer> order = new HashMap<String, Integer>();
			// check for index of prov candidates
			for (String opName : operationNames) {
				for (Integer index = 0; index < provCandidate.ops.size(); index++) {
					AbstractOperation op = provCandidate.ops.get(index);
					if (op instanceof InterfaceDummy) {
						if (((InterfaceDummy) op).getOperationNames().contains(opName)) {
							order.put(opName, index);
						}
					} else {
						if (opName.equals(op.getName())) {
							order.put(opName, index);
						}
					}
				}
			}
			
			for (String opName : operationNames) {
				Integer index = order.get(opName);
				if (index == null) {
					continue;
				}
				AbstractOperation op = provCandidate.ops.get(index);
				
				if (op instanceof InterfaceDummy) {
					op = ((InterfaceDummy) op).getOperation(opName);
				}
				
				if (!operationNames.contains(op.getName())) {
					// if the operation isn't mentioned in operationName
					// list, don't execute the operation
					continue;
				}
				AbstractImplementationArtifact ia = provCandidate.ias.get(index);
				IPlanBuilderProvPhaseOperationPlugin plugin = provCandidate.plugins.get(index);
				check &= plugin.handle(context, op, ia);
			}
		}
		return check;
	}
	
	public List<AbstractDeploymentArtifact> getDAsOfCandidate(int candidateIndex) {
		return this.daCandidates.get(candidateIndex).das;
	}
	
	public boolean executeOperationProvisioning(TemplatePlanContext context, List<String> operationNames, Map<AbstractParameter, Variable> param2propertyMapping) {
		int checkCount = 0;
		if (!this.provCandidates.isEmpty()) {
			OperationNodeTypeImplCandidate provCandidate = this.provCandidates.get(0);
			Map<String, Integer> order = new HashMap<String, Integer>();
			// check for index of prov candidates
			for (String opName : operationNames) {
				for (Integer index = 0; index < provCandidate.ops.size(); index++) {
					AbstractOperation op = provCandidate.ops.get(index);
					if (opName.equals(op.getName())) {
						order.put(opName, index);
					}
				}
			}
			
			for (String opName : operationNames) {
				Integer index = order.get(opName);
				if (index == null) {
					continue;
				}
				AbstractOperation op = provCandidate.ops.get(index);
				if (!operationNames.contains(op.getName())) {
					// if the operation isn't mentioned in operationName
					// list, don't execute the operation
					continue;
				}
				AbstractImplementationArtifact ia = provCandidate.ias.get(index);
				IPlanBuilderProvPhaseOperationPlugin plugin = provCandidate.plugins.get(index);
				
				if (plugin instanceof IPlanBuilderProvPhaseParamOperationPlugin) {
					IPlanBuilderProvPhaseParamOperationPlugin paramPlugin = (IPlanBuilderProvPhaseParamOperationPlugin) plugin;
					if (paramPlugin.handle(context, op, ia, param2propertyMapping)) {
						checkCount++;
					}
				}
				
			}
		}
		return checkCount == operationNames.size();
		
	}
}