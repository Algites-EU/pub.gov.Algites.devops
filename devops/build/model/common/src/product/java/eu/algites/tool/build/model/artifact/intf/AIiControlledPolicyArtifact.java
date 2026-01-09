package eu.algites.tool.build.model.artifact.intf;

import static eu.algites.tool.build.model.artifact.intf.AInArtifactKind.POLICY;

/**
 * <p>
 * Title: {@link AIiControlledPolicyArtifact}
 * </p>
 * <p>
 * Description: Basic interface for the Algites Policy Artifacts
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
public interface AIiControlledPolicyArtifact extends AIiAbstractControlledPotentiallyArtificialArtifact,
		AIiAbstractControlledPolicyBackgroundDependenciesManagerArtifact,
		AIiAbstractControlledDirectDependenciesArtifact<AIiAbstractArtifact>,
		AIiAbstractControlledParentContainerArtifact<AIiAbstractArtifact> {

	/**
	 * Gets the Unique Id of the policy in the tree of the inherintance of the policies. The Uid must be usable as the valid XML element (used
	 * in POM-files for the generation of the property element, where the value is given by {@link #getPolicyDefinitionVersion()})
	 *
	 * @return the Unique Id of the policy in the hierarchy of the policy inheritance
	 */
	String getPolicyDefinitionUid();

	/**
	 * Gets the version of the policy
	 *
	 * @return the version of the policy
	 */
	String getPolicyDefinitionVersion();

	/**
	 * Returns if the policy artifact does not exist in the reality like a defined artifact,
	 * but is only the artificial policy artifact constructed only to allow the core artifact
	 * to exist in single artifact mode
	 * @return true if the Artifact is  Artificial artifact and false if not.
	 */
	default boolean isArtificialArtifactForSingleCoreArtifact() {
		;return getArtifactConfigurationFile() == null;
	}

	/**
	 * Gets the kind of the artifact {@link AInArtifactKind#POLICY}
	 *
	 * @return the kind of the artifact
	 */
	@Override
	default AInArtifactKind getArtifactKind() {
		return POLICY;
	}
}
