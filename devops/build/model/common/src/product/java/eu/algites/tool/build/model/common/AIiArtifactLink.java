package eu.algites.tool.build.model.common;

/**
 * <p>
 * Title: {@link AIiArtifactLink}
 * </p>
 * <p>
 * Description: Gets the abstraction of the link of the artifact
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 07.01.26 15:53
 */
public interface AIiArtifactLink<A extends AIiAbstractArtifact> {

	/**
	 * Gets the linked artifact
	 * @return the linked artifact
	 */
	A getLinkedArtifact();

}
