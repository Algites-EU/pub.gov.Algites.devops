package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.intf.AIiAbstractArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiAbstractControlledParentRwContainerArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiArtifactDependency;
import eu.algites.tool.build.model.artifact.intf.AIiControlledPolicyArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiAbstractUncontrolledArtifact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AIcControlledPolicyArtifact
		extends AIcAbstractControlledArtifact
		implements
		  AIiControlledPolicyArtifact,
		  AIiAbstractControlledParentRwContainerArtifact<AIiAbstractArtifact> {

	private String policyDefinitionUid;
	private String policyDefinitionVersion;

	private AIiAbstractArtifact parent;

	private List<AIiArtifactDependency<? extends AIiAbstractArtifact>> directDependencies;
	private List<AIiArtifactDependency<? extends AIiAbstractUncontrolledArtifact>> managedPolicyBackgroundDependencies;

	public AIcControlledPolicyArtifact() {
		// keep kind provided by YAML (or caller)
	}

	@Override
	public String getPolicyDefinitionUid() {
		return policyDefinitionUid;
	}

	public void setPolicyDefinitionUid(String policyDefinitionUid) {
		this.policyDefinitionUid = policyDefinitionUid;
	}

	@Override
	public String getPolicyDefinitionVersion() {
		return policyDefinitionVersion;
	}

	public void setPolicyDefinitionVersion(String policyDefinitionVersion) {
		this.policyDefinitionVersion = policyDefinitionVersion;
	}

	@Override
	public AIiAbstractArtifact getParent() {
		return parent;
	}

	public void setParent(AIiAbstractArtifact parent) {
		this.parent = parent;
	}

	@Override
	public List<AIiArtifactDependency<? extends AIiAbstractArtifact>> getDirectDependencies() {
		return directDependencies;
	}

	public void setDirectDependencies(List<AIiArtifactDependency<? extends AIiAbstractArtifact>> directDependencies) {
		this.directDependencies = directDependencies;
	}

	public void addDirectDependency(AIiArtifactDependency<? extends AIiAbstractArtifact> dep) {
		if (directDependencies == null)
			directDependencies = new ArrayList<>();
		directDependencies.add(dep);
	}

	@Override
	public List<AIiArtifactDependency<? extends AIiAbstractUncontrolledArtifact>> getManagedPolicyBackgroundDependencies() {
		return managedPolicyBackgroundDependencies;
	}

	public void setManagedPolicyBackgroundDependencies(
			List<AIiArtifactDependency<? extends AIiAbstractUncontrolledArtifact>> managedPolicyBackgroundDependencies) {
		this.managedPolicyBackgroundDependencies = managedPolicyBackgroundDependencies;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(policyDefinitionUid);
		result = 31 * result + Objects.hashCode(policyDefinitionVersion);
		result = 31 * result + Objects.hashCode(parent);
		result = 31 * result + Objects.hashCode(directDependencies);
		result = 31 * result + Objects.hashCode(managedPolicyBackgroundDependencies);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcControlledPolicyArtifact locthat))
			return false;
		if (!super.equals(aO))
			return false;

		return Objects.equals(policyDefinitionUid, locthat.policyDefinitionUid) && Objects.equals(
				policyDefinitionVersion,
				locthat.policyDefinitionVersion) && Objects.equals(parent, locthat.parent) && Objects.equals(
				directDependencies,
				locthat.directDependencies) && Objects.equals(
				managedPolicyBackgroundDependencies,
				locthat.managedPolicyBackgroundDependencies);
	}

	@Override
	public String toString() {
		return "AIcControlledPolicyArtifact{" + super.toString() + "\n" +
				"policyDefinitionUid='" + policyDefinitionUid + '\'' +
				", policyDefinitionVersion='" + policyDefinitionVersion + '\'' +
				", parent=" + parent +
				", directDependencies=" + directDependencies +
				", managedPolicyBackgroundDependencies=" + managedPolicyBackgroundDependencies +
				"} ";
	}
}
