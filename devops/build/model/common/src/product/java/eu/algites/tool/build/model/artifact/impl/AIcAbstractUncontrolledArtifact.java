package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.intf.AIiAbstractUncontrolledArtifact;

import java.util.Objects;

public abstract class AIcAbstractUncontrolledArtifact extends AIcAbstractArtifact implements AIiAbstractUncontrolledArtifact {

	private String artifactVersion;

	public AIcAbstractUncontrolledArtifact() {
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
		if (!(aO instanceof AIcAbstractUncontrolledArtifact locthat))
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
