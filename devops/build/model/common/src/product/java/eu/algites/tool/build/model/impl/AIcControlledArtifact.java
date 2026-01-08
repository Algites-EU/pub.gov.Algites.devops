package eu.algites.tool.build.model.impl;

import eu.algites.tool.build.model.common.AIiControlledArtifact;
import eu.algites.tool.build.model.common.AIiVersionContext;
import eu.algites.tool.build.model.common.AInArtifactKind;

import java.util.Objects;

public class AIcControlledArtifact extends AIcAbstractArtifact implements AIiControlledArtifact {

	private AIiVersionContext versionContext;

	public AIcControlledArtifact() {
	}

	public AIcControlledArtifact(String groupId, String artifactIdBase, AInArtifactKind kind, AIiVersionContext versionContext) {
		super(groupId, artifactIdBase, kind);
		this.versionContext = versionContext;
	}

	@Override
	public AIiVersionContext getVersionContext() {
		return versionContext;
	}

	public void setVersionContext(AIiVersionContext versionContext) {
		this.versionContext = versionContext;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(versionContext);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcControlledArtifact locthat))
			return false;
		if (!super.equals(aO))
			return false;

		return Objects.equals(versionContext, locthat.versionContext);
	}

	@Override
	public String toString() {
		return "AIcControlledArtifact{" + super.toString() + "\n" +
				"versionContext=" + versionContext +
				"} ";
	}
}
