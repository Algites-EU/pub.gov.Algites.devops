package eu.algites.tool.build.model.impl;

import eu.algites.tool.build.model.common.AIiUncontrolledArtifact;
import eu.algites.tool.build.model.common.AInArtifactKind;

import java.util.Objects;

public abstract class AIcUncontrolledArtifact extends AIcAbstractArtifact implements AIiUncontrolledArtifact {

	private String artifactVersion;

	public AIcUncontrolledArtifact() {
	}

	public AIcUncontrolledArtifact(String groupId, String artifactIdBase, AInArtifactKind kind, String artifactVersion) {
		super(groupId, artifactIdBase, kind);
		this.artifactVersion = artifactVersion;
	}

	@Override
	public String getArtifactVersion() {
		return artifactVersion;
	}

	public void setArtifactVersion(String artifactVersion) {
		this.artifactVersion = artifactVersion;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(artifactVersion);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcUncontrolledArtifact locthat))
			return false;
		if (!super.equals(aO))
			return false;

		return Objects.equals(artifactVersion, locthat.artifactVersion);
	}

	@Override
	public String toString() {
		return "AIcUncontrolledArtifact{" + super.toString() + "\n" +
				"artifactVersion=" + artifactVersion +
				"} ";
	}
}
