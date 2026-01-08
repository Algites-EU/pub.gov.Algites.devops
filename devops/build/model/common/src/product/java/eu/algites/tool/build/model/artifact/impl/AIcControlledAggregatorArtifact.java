package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.common.AIiControlledAggregatorArtifact;
import eu.algites.tool.build.model.artifact.common.AIiControlledArtifact;

import java.util.List;
import java.util.Objects;

public class AIcControlledAggregatorArtifact extends AIcControlledArtifact implements AIiControlledAggregatorArtifact {

	private List<AIiControlledArtifact> containedArtifacts;

	@Override
	public List<AIiControlledArtifact> getContainedArtifacts() {
		return containedArtifacts;
	}

	public void setContainedArtifacts(List<AIiControlledArtifact> containedArtifacts) {
		this.containedArtifacts = containedArtifacts;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(containedArtifacts);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcControlledAggregatorArtifact locthat))
			return false;
		if (!super.equals(aO))
			return false;

		return Objects.equals(containedArtifacts, locthat.containedArtifacts);
	}

	public String toString() {
		return "AIcControlledAggregatorArtifact{" + super.toString() + "\n" +
				"containedArtifacts=" + containedArtifacts +
				"} ";
	}
}
