package eu.algites.tool.build.model.common;

import static eu.algites.tool.build.model.common.AInArtifactKind.PRODUCT_CORE;

/**
 * <p>
 * Title: {@link AIiControlledProductCoreArtifact}
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
public interface AIiControlledProductCoreArtifact extends AIiControlledArtifact,
		AIiDirectDependencies<AIiAbstractArtifact>, AIiParentContainer<AIiControlledPolicyArtifact> {

	/**
	 * Gets the kind of the artifact {@link AInArtifactKind#PRODUCT_CORE}
	 *
	 * @return the kind of the artifact
	 */
	@Override
	default AInArtifactKind getArtifactKind() {
		return PRODUCT_CORE;
	}
}
