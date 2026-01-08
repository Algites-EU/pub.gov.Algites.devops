package eu.algites.tool.build.model.impl;

import eu.algites.tool.build.model.common.AIiAbstractArtifact;
import eu.algites.tool.build.model.common.AIiArtifactDependency;
import eu.algites.tool.build.model.common.AIiControlledPolicyArtifact;
import eu.algites.tool.build.model.common.AIiControlledPolicyBackgroundBomArtifact;

import java.util.List;
import java.util.Objects;

public class AIcControlledPolicyBackgroundBomArtifact extends AIcControlledArtifact
		implements AIiControlledPolicyBackgroundBomArtifact {

	private AIiControlledPolicyArtifact parent;
	private List<AIiArtifactDependency<? extends AIiAbstractArtifact>> managedPolicyBackgroundDependencies;

	@Override
	public AIiControlledPolicyArtifact getParent() {
		return parent;
	}

	public void setParent(AIiControlledPolicyArtifact aArtifact) {
		this.parent = aArtifact;
	}

	@Override
	public List<AIiArtifactDependency<? extends AIiAbstractArtifact>> getManagedPolicyBackgroundDependencies() {
		return managedPolicyBackgroundDependencies;
	}

	public void setManagedPolicyBackgroundDependencies(
			List<AIiArtifactDependency<? extends AIiAbstractArtifact>> aManagedPolicyBackgroundDependencies) {
		this.managedPolicyBackgroundDependencies = aManagedPolicyBackgroundDependencies;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(parent);
		result = 31 * result + Objects.hashCode(managedPolicyBackgroundDependencies);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcControlledPolicyBackgroundBomArtifact locthat))
			return false;
		if (!super.equals(aO))
			return false;

		return Objects.equals(parent, locthat.parent) && Objects.equals(
				managedPolicyBackgroundDependencies,
				locthat.managedPolicyBackgroundDependencies);
	}

	public String toString() {
		return "AIcControlledPolicyBackgroundBomArtifact{" + super.toString() + "\n" +
				"parent=" + parent +
				", managedPolicyBackgroundDependencies=" + managedPolicyBackgroundDependencies +
				"} ";
	}
}
