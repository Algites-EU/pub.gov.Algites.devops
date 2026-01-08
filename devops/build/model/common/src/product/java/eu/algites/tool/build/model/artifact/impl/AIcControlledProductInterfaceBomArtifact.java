package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.common.AIiArtifactDependency;
import eu.algites.tool.build.model.artifact.common.AIiControlledArtifact;
import eu.algites.tool.build.model.artifact.common.AIiControlledPolicyArtifact;
import eu.algites.tool.build.model.artifact.common.AIiControlledPolicyBackgroundBomArtifact;
import eu.algites.tool.build.model.artifact.common.AIiControlledProductInterfaceBomArtifact;

import java.util.List;
import java.util.Objects;

public class AIcControlledProductInterfaceBomArtifact extends AIcControlledArtifact
		implements AIiControlledProductInterfaceBomArtifact {

	private AIiControlledPolicyArtifact parent;
	private AIiControlledPolicyBackgroundBomArtifact policyBackgroundBom;
	private List<AIiArtifactDependency<? extends AIiControlledArtifact>> managedInterfaceDependencies;

	@Override
	public AIiControlledPolicyArtifact getParent() {
		return parent;
	}

	public void setParent(AIiControlledPolicyArtifact parent) {
		this.parent = parent;
	}

	@Override
	public AIiControlledPolicyBackgroundBomArtifact getPolicyBackgroundBom() {
		return policyBackgroundBom;
	}

	public void setPolicyBackgroundBom(AIiControlledPolicyBackgroundBomArtifact policyBackgroundBom) {
		this.policyBackgroundBom = policyBackgroundBom;
	}

	@Override
	public List<AIiArtifactDependency<? extends AIiControlledArtifact>> getManagedInterfaceDependencies() {
		return managedInterfaceDependencies;
	}

	public void setManagedInterfaceDependencies(List<AIiArtifactDependency<? extends AIiControlledArtifact>> managedInterfaceDependencies) {
		this.managedInterfaceDependencies = managedInterfaceDependencies;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(parent);
		result = 31 * result + Objects.hashCode(policyBackgroundBom);
		result = 31 * result + Objects.hashCode(managedInterfaceDependencies);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcControlledProductInterfaceBomArtifact locthat))
			return false;
		if (!super.equals(aO))
			return false;

		return Objects.equals(parent, locthat.parent) && Objects.equals(policyBackgroundBom, locthat.policyBackgroundBom)
				&& Objects.equals(managedInterfaceDependencies, locthat.managedInterfaceDependencies);
	}

	@Override
	public String toString() {
		return "AIcControlledProductInterfaceBomArtifact{" + super.toString() + "\n" +
				"parent=" + parent +
				", policyBackgroundBom=" + policyBackgroundBom +
				", managedInterfaceDependencies=" + managedInterfaceDependencies +
				"} ";
	}
}
