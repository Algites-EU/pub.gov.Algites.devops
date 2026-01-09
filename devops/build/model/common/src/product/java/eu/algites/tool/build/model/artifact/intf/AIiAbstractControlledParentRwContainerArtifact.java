package eu.algites.tool.build.model.artifact.intf;

/**
 * <p>
 * Title: {@link AIiAbstractControlledParentRwContainerArtifact}
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
 * @date 09.01.26 10:59
 */
public interface AIiAbstractControlledParentRwContainerArtifact<A extends AIiAbstractArtifact>
		extends AIiAbstractControlledParentContainerArtifact<A> {

	/**
	 * Sets the parent of the artifact
	 * @param aParent the parent of the artifact
	 */
	void setParent(A aParent);
}
