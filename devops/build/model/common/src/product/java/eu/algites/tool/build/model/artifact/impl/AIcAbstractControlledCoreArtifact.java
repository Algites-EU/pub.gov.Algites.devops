package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.intf.AIiAbstractArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiAbstractControlledParentRwContainerArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiArtifactDependency;
import eu.algites.tool.build.model.artifact.intf.AIiControlledPolicyArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiControlledProductCoreArtifact;

import java.util.List;
import java.util.Objects;

public class AIcAbstractControlledCoreArtifact
		extends AIcAbstractControlledArtifact
		implements AIiControlledProductCoreArtifact,
		AIiAbstractControlledParentRwContainerArtifact<AIiControlledPolicyArtifact> {

	private AIiControlledPolicyArtifact parent;
	private List<AIiArtifactDependency<? extends AIiAbstractArtifact>> directDependencies;

	@Override
	public AIiControlledPolicyArtifact getParent() {
		return parent;
	}

	public void setParent(AIiControlledPolicyArtifact parent) {
		this.parent = parent;
	}

	@Override
	public List<AIiArtifactDependency<? extends AIiAbstractArtifact>> getDirectDependencies() {
		return directDependencies;
	}

	public void setDirectDependencies(List<AIiArtifactDependency<? extends AIiAbstractArtifact>> directDependencies) {
		this.directDependencies = directDependencies;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(parent);
		result = 31 * result + Objects.hashCode(directDependencies);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcAbstractControlledCoreArtifact locthat))
			return false;
		if (!super.equals(aO))
			return false;

		return Objects.equals(parent, locthat.parent) && Objects.equals(directDependencies, locthat.directDependencies);
	}

	@Override
	public String toString() {
		return "AIcAbstractControlledCoreArtifact{" + super.toString() + "\n" +
				"parent=" + parent +
				", directDependencies=" + directDependencies +
				"} ";
	}
}
