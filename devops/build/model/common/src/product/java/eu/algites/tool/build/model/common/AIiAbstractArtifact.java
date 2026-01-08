package eu.algites.tool.build.model.common;

/**
 * <p>
 * Title: {@link AIiAbstractArtifact}
 * </p>
 * <p>
 * Description: Basic interface for the Algites Artifacts
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
public interface AIiAbstractArtifact {

	/**
	 * Gets the group Id of the artifact
	 * @return the group Id of the artifact
	 */
	String getArtifactGroupId();

	/**
	 * Gets the artifact Id base of the artifact. The Id Base denotes
	 * the Artifact Id and in the case some sub-artifacts
	 * for the given artifact are generated,
	 * to this string can be appended some extra extension, like "-pom"
	 * @return the artifact Id base of the artifact
	 */
	String getArtifactIdBase();

	/**
	 * Gets the kind of the artifact
	 * @return the kind of the artifact.
	 */
	AInArtifactKind getArtifactKind();
}
