package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.intf.AIiAbstractArtifact;
import eu.algites.tool.build.model.artifact.intf.AInArtifactKind;

import java.util.Objects;

/**
 * Simple mutable implementation of {@link AIiAbstractArtifact}.
 * <p>
 * Note: This is primarily intended as a backing class for YAML/JSON loading. Keep it boring and predictable (POJO with getters/setters).
 * </p>
 *
 * @author linhart1
 */
public abstract class AIcAbstractArtifact implements AIiAbstractArtifact {

	private String artifactGroupId;
	private String artifactIdBase;
	private AInArtifactKind artifactKind;

	public AIcAbstractArtifact() {
	}

	public AIcAbstractArtifact(String aArtifactGroupId, String aArtifactIdBase, AInArtifactKind aArtifactKind) {
		this.artifactGroupId = aArtifactGroupId;
		this.artifactIdBase = aArtifactIdBase;
		this.artifactKind = aArtifactKind;
	}

	@Override
	public String getArtifactGroupId() {
		return artifactGroupId;
	}

	@Override
	public String getArtifactIdBase() {
		return artifactIdBase;
	}

	public void setArtifactIdBase(String artifactIdBase) {
		this.artifactIdBase = artifactIdBase;
	}

	@Override
	public AInArtifactKind getArtifactKind() {
		return artifactKind;
	}

	public void setArtifactKind(AInArtifactKind artifactKind) {
		this.artifactKind = artifactKind;
	}

	public void setArtifactGroupId(String groupId) {
		this.artifactGroupId = groupId;
	}

	@Override
	public int hashCode() {
		int result = Objects.hashCode(artifactGroupId);
		result = 31 * result + Objects.hashCode(artifactIdBase);
		result = 31 * result + Objects.hashCode(artifactKind);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcAbstractArtifact locthat))
			return false;

		return Objects.equals(artifactGroupId, locthat.artifactGroupId) && Objects.equals(
				artifactIdBase,
				locthat.artifactIdBase) && artifactKind == locthat.artifactKind;
	}

	@Override
	public String toString() {
		return "AAbstractArtifactImpl{" +
				"artifactGroupId='" + artifactGroupId + '\'' +
				", artifactIdBase='" + artifactIdBase + '\'' +
				", artifactKind=" + artifactKind +
				'}';
	}
}
