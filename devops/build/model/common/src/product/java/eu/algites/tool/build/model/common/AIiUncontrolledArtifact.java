package eu.algites.tool.build.model.common;

import static eu.algites.tool.build.model.common.AInArtifactKind.UNCONTROLLED_CORE;

/**
 * <p>
 * Title: {@link AIiUncontrolledArtifact}
 * </p>
 * <p>
 * Description: Basic Marker interface for the Algites Uncontrolled Artifacts
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
public interface AIiUncontrolledArtifact extends AIiAbstractArtifact {

	/**
	 * Gets the version of the artifact
	 * @return the version of the artifact
	 */
	String getArtifactVersion();

}
