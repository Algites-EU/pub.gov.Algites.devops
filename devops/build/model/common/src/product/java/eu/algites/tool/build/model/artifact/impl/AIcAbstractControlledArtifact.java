package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.intf.AIiAbstractControlledArtifact;
import eu.algites.tool.build.model.version.intf.AIiVersionContext;

import java.nio.file.Path;
import java.util.Objects;

public abstract class AIcAbstractControlledArtifact extends AIcAbstractArtifact implements AIiAbstractControlledArtifact {

	private AIiVersionContext versionContext;
	private Path artifactConfigurationFile;

	public AIcAbstractControlledArtifact() {
	}

	@Override
	public AIiVersionContext getVersionContext() {
		return versionContext;
	}

	public void setVersionContext(AIiVersionContext versionContext) {
		this.versionContext = versionContext;
	}

	@Override
	public Path getArtifactConfigurationFile() {
		return artifactConfigurationFile;
	}

	/**
	 * @param aArtifactConfigurationFile the artifactConfigurationFile
	 */
	public void setArtifactConfigurationFile(final Path aArtifactConfigurationFile) {
		artifactConfigurationFile = aArtifactConfigurationFile;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcAbstractControlledArtifact locthat))
			return false;
		if (!super.equals(aO))
			return false;

		return Objects.equals(versionContext, locthat.versionContext) && Objects.equals(
				artifactConfigurationFile,
				locthat.artifactConfigurationFile);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(versionContext);
		result = 31 * result + Objects.hashCode(artifactConfigurationFile);
		return result;
	}

	@Override
	public String toString() {
		return "AIcAbstractControlledArtifact{" + super.toString() + "\n" +
				"versionContext=" + versionContext +
				", artifactConfigurationFile=" + artifactConfigurationFile +
				"} ";
	}
}
