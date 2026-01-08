package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.common.AIiAbstractArtifact;
import eu.algites.tool.build.model.artifact.common.AIiArtifactDependency;
import eu.algites.tool.build.model.artifact.common.AIiArtifactDependencyScope;
import eu.algites.tool.build.model.artifact.common.AIiArtifactProjection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Dependency that also points to the target artifact.
 *
 * @author linhart1
 */
public class AIcArtifactDependencyLink<A extends AIiAbstractArtifact>
		extends AIcArtifactProjection<A>
		implements AIiArtifactDependency<A> {

	private A linkedArtifact;
	private AIiArtifactDependencyScope dependencyScope;
	private List<AIiArtifactProjection<? extends AIiAbstractArtifact>> dependencyExclusions;

	public AIcArtifactDependencyLink() {
	}

	public AIcArtifactDependencyLink(
			final A linkedArtifact,
			final String outputClassifier,
			final String outputTypeId,
			final AIiArtifactDependencyScope aDependencyScope,
			final List<AIiArtifactProjection<? extends AIiAbstractArtifact>> aDependencyExclusions) {
		super(linkedArtifact, outputClassifier, outputTypeId);
		dependencyScope = aDependencyScope;
		dependencyExclusions = aDependencyExclusions;
	}

	@Override
	public A getLinkedArtifact() {
		return linkedArtifact;
	}

	public void setLinkedArtifact(A aLinkedArtifact) {
		this.linkedArtifact = aLinkedArtifact;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(linkedArtifact);
		result = 31 * result + Objects.hashCode(dependencyScope);
		result = 31 * result + Objects.hashCode(dependencyExclusions);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcArtifactDependencyLink<?> locthat))
			return false;
		if (!super.equals(aO))
			return false;

		return Objects.equals(linkedArtifact, locthat.linkedArtifact) && Objects.equals(
				dependencyScope,
				locthat.dependencyScope) && Objects.equals(dependencyExclusions, locthat.dependencyExclusions);
	}

	@Override
	public String toString() {
		return "AIcArtifactDependencyLink{" + super.toString() + "\n" +
				"linkedArtifact=" + linkedArtifact +
				", dependencyScope=" + dependencyScope +
				", dependencyExclusions=" + dependencyExclusions +
				"} ";
	}

	@Override
	public List<AIiArtifactProjection<? extends AIiAbstractArtifact>> getDependencyExclusions() {
		return dependencyExclusions;
	}

	public void setDependencyExclusions(List<AIiArtifactProjection<? extends AIiAbstractArtifact>> dependencyExclusions) {
		this.dependencyExclusions = dependencyExclusions;
	}

	@Override
	public AIiArtifactDependencyScope getDependencyScope() {
		return dependencyScope;
	}

	public void setDependencyScope(AIiArtifactDependencyScope dependencyScope) {
		this.dependencyScope = dependencyScope;
	}

	public void addDependencyExclusion(AIiArtifactProjection<? extends AIiAbstractArtifact> exclusion) {
		if (dependencyExclusions == null)
			dependencyExclusions = new ArrayList<>();
		dependencyExclusions.add(exclusion);
	}
}
