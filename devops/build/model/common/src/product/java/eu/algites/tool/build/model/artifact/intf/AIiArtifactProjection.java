package eu.algites.tool.build.model.artifact.intf;

/**
 * <p>
 * Title: {@link AIiArtifactProjection}
 * </p>
 * <p>
 * Description: Gets the definition of the artifact projection, defining which projection should be used in the used context.
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
public interface AIiArtifactProjection<A extends AIiAbstractArtifact>
		extends AIiArtifactLink<A> {

	/**
	 * Gets the classifier of the output
	 *
	 * @return the classifier of the output. If returns the null value, then this is the default output of the artifact
	 */
	String getOutputClassifier();

	/**
	 * Gets the identification of the type of the output (jar/pom, etc.)
	 *
	 * @return the id of the type of the output.
	 */
	String getOutputTypeId();

}
