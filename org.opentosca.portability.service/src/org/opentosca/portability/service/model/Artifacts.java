package org.opentosca.portability.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * JAXB Class for the results of the ArtifactQuery
 * 
 * @author Marcus Eisele - marcus.eisele@gmail.com
 * 
 */
@XmlRootElement(name = "Artifacts")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Artifacts")
public class Artifacts {
	@XmlTransient
	public static final String NAMESPACE = "http://opentosca.org/planportability/rest";
	
	@XmlElement(name = "deploymentArtifact")
	@XmlElementWrapper(name = "deploymentArtifacts")
	private List<DeploymentArtifact> deploymentArtifact;
	
	@XmlElement(name = "implementationArtifact")
	@XmlElementWrapper(name = "implementationtArtifacts")
	private List<ImplementationArtifact> implementationArtifact;
	
	public List<ImplementationArtifact> getImplementationArtifact() {
		return implementationArtifact;
	}
	
	public void setImplementationArtifact(List<ImplementationArtifact> implementationArtifact) {
		this.implementationArtifact = implementationArtifact;
	}
	
	public List<DeploymentArtifact> getDeploymentArtifact() {
		return deploymentArtifact;
	}
	
	public void setDeploymentArtifact(List<DeploymentArtifact> deploymentArtifact) {
		this.deploymentArtifact = deploymentArtifact;
	}
	
}
