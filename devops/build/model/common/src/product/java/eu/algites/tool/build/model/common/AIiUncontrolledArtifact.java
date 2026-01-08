package eu.algites.tool.build.model.common;

import static eu.algites.tool.build.model.common.AInArtifactKind.UNCONTROLLED;

/**
 * <p>
 * Title: {@link AIiUncontrolledArtifact}
 * </p>
 * <p>
 * Description: Basic interface for the Algites Unknown Artifacts
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
	 * Gets the kind of the artifact {@link AInArtifactKind#UNCONTROLLED}
	 * @return the kind of the artifact
	 */
	@Override
	default AInArtifactKind getArtifactKind() { return UNCONTROLLED; }

	/**
	 * Gets the version of the artifact
	 * @return the version of the artifact
	 */
	String getArtifactVersion();

}
