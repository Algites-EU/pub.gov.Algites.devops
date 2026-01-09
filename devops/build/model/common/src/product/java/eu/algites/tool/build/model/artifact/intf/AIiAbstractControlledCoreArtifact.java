package eu.algites.tool.build.model.artifact.intf;

/**
 * <p>
 * Title: {@link AIiAbstractControlledCoreArtifact}
 * </p>
 * <p>
 * Description: TODO: Add description
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 09.01.26 10:20
 */
public interface AIiAbstractControlledCoreArtifact
		extends AIiAbstractControlledArtifact, AIiAbstractControlledDirectDependenciesArtifact<AIiAbstractArtifact>,
		AIiAbstractControlledParentContainerArtifact<AIiControlledPolicyArtifact> {

	/**
	 * Returns true if the artifact is a single core artifact having artificial policy parent and contained within the artificial aggregator
	 *
	 * @return true if the artifact is a single core artifact
	 */
	default boolean isSingleModeCoreArtifact() {
		return getParent().isArtificialArtifactForSingleCoreArtifact();
	}

}
