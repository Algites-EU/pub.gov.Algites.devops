package eu.algites.tool.build.model.common;

import static eu.algites.tool.build.model.common.AInArtifactKind.UNCONTROLLED_BOM;

/**
 * <p>
 * Title: {@link AIiUncontrolledBomArtifact}
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
public interface AIiUncontrolledBomArtifact extends AIiUncontrolledArtifact {

	/**
	 * Gets the kind of the artifact {@link AInArtifactKind#UNCONTROLLED_BOM}
	 *
	 * @return the kind of the artifact
	 */
	@Override
	default AInArtifactKind getArtifactKind() {
		return UNCONTROLLED_BOM;
	}

}
