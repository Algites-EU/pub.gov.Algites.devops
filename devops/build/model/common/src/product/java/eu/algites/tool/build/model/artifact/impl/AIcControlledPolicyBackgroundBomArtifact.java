package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.intf.AIiAbstractControlledParentRwContainerArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiArtifactDependency;
import eu.algites.tool.build.model.artifact.intf.AIiControlledPolicyArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiControlledPolicyBackgroundBomArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiAbstractUncontrolledArtifact;

import java.util.List;
import java.util.Objects;

public class AIcControlledPolicyBackgroundBomArtifact
		extends AIcAbstractControlledArtifact
		implements
		  AIiControlledPolicyBackgroundBomArtifact,
		  AIiAbstractControlledParentRwContainerArtifact<AIiControlledPolicyArtifact> {

	private AIiControlledPolicyArtifact parent;
	private List<AIiArtifactDependency<? extends AIiAbstractUncontrolledArtifact>> managedPolicyBackgroundDependencies;

	@Override
	public AIiControlledPolicyArtifact getParent() {
		return parent;
	}

	public void setParent(AIiControlledPolicyArtifact aArtifact) {
		this.parent = aArtifact;
	}

	@Override
	public List<AIiArtifactDependency<? extends AIiAbstractUncontrolledArtifact>> getManagedPolicyBackgroundDependencies() {
		return managedPolicyBackgroundDependencies;
	}

	public void setManagedPolicyBackgroundDependencies(
			List<AIiArtifactDependency<? extends AIiAbstractUncontrolledArtifact>> aManagedPolicyBackgroundDependencies) {
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
