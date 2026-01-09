package eu.algites.tool.build.model.artifact.intf;

import java.util.List;

/**
 * <p>
 * Title: {@link AIiAbstractControlledDirectDependenciesArtifact}
 * </p>
 * <p>
 * Description: Basic interface for the Algites direct dependencies container
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
public interface AIiAbstractControlledDirectDependenciesArtifact<T extends AIiAbstractArtifact> extends AIiAbstractControlledArtifact {

	/**
	 * gets the dependencies of the artifact. Dependencies mean the dírectly transitive dependencies applied to the artifact or its children.
	 *
	 * @return the dependencies of the artifact
	 */
	List<AIiArtifactDependency<? extends T>> getDirectDependencies();

}
