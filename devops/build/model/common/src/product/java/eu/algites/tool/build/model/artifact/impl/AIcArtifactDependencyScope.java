package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.intf.AIiArtifactDependencyScope;
import eu.algites.tool.build.model.artifact.intf.AIiDependencyScopeBehavior;
import eu.algites.tool.build.model.artifact.intf.AInArtifactDependencyScopeLevel;

import java.util.Objects;

public class AIcArtifactDependencyScope implements AIiArtifactDependencyScope {

	private AInArtifactDependencyScopeLevel level;
	private AIiDependencyScopeBehavior behavior;

	public AIcArtifactDependencyScope() {
	}

	public AIcArtifactDependencyScope(AInArtifactDependencyScopeLevel level, AIiDependencyScopeBehavior behavior) {
		this.level = level;
		this.behavior = behavior;
	}

	@Override
	public AInArtifactDependencyScopeLevel getLevel() {
		return level;
	}

	public void setLevel(AInArtifactDependencyScopeLevel level) {
		this.level = level;
	}

	@Override
	public AIiDependencyScopeBehavior getBehavior() {
		return behavior;
	}

	public void setBehavior(AIiDependencyScopeBehavior behavior) {
		this.behavior = behavior;
	}

	@Override
	public int hashCode() {
		int result = Objects.hashCode(level);
		result = 31 * result + Objects.hashCode(behavior);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcArtifactDependencyScope locthat))
			return false;

		return level == locthat.level && Objects.equals(behavior, locthat.behavior);
	}

	@Override
	public String toString() {
		return "AIcArtifactDependencyScope{" +
				"level=" + level +
				", behavior=" + behavior +
				'}';
	}
}
