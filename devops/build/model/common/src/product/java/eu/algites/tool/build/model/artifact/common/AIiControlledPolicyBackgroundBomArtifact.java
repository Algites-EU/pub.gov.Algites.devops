package eu.algites.tool.build.model.artifact.common;

import static eu.algites.tool.build.model.artifact.common.AInArtifactKind.POLICY_BACKGROUND_BOM;

import java.util.List;

/**
 * <p>
 * Title: {@link AIiControlledPolicyBackgroundBomArtifact}
 * </p>
 * <p>
 * Description: Basic interface for the Algites PB-BOM Artifacts
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
public interface AIiControlledPolicyBackgroundBomArtifact extends
		AIiControlledArtifact, AIiPolicyBackgroundDependenciesManager, AIiParentContainer<AIiControlledPolicyArtifact> {

	@Override
	@SuppressWarnings("unchecked")
	default List<AIiArtifactDependency<? extends AIiAbstractArtifact>> getManagedDependencies() {
		return (List)getManagedPolicyBackgroundDependencies();
	}

	/**
	 * Gets the kind of the artifact {@link AInArtifactKind#POLICY_BACKGROUND_BOM}
	 *
	 * @return the kind of the artifact
	 */
	@Override
	default AInArtifactKind getArtifactKind() {
		return POLICY_BACKGROUND_BOM;
	}
}
