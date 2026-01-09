package eu.algites.tool.build.model.artifact.intf;

/**
 * <p>
 * Title: {@link AIiAbstractControlledPotentiallyArtificialArtifact}
 * </p>
 * <p>
 * Description: Basic interface for the Algites Potentially artificial Artifacts
 * The Artificial artifact is not the one existing in the reality, but created only to allow
 * the core artifact to exist in single artifact mode
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 07.01.26 14:07
 */
public interface AIiAbstractControlledPotentiallyArtificialArtifact extends AIiAbstractControlledArtifact {

	/**
	 * Returns if the policy artifact does not exist in the reality like a defined artifact,
	 * but is only the artificial policy artifact constructed only to allow the core artifact
	 * to exist in single artifact mode
	 * @return true if the Artifact is an artificial artifact and false if not.
	 */
	default boolean isArtificialArtifactForSingleCoreArtifact() {
		;return getArtifactConfigurationFile() == null;
	}

}
