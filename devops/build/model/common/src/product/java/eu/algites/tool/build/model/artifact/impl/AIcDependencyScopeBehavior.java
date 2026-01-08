package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.common.AIiDependencyScopeBehavior;

public class AIcDependencyScopeBehavior implements AIiDependencyScopeBehavior {

	private boolean locked;
	private boolean transitive;

	public AIcDependencyScopeBehavior() {
	}

	public AIcDependencyScopeBehavior(boolean locked, boolean transitive) {
		this.locked = locked;
		this.transitive = transitive;
	}

	@Override
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public boolean isTransitive() {
		return transitive;
	}

	public void setTransitive(boolean transitive) {
		this.transitive = transitive;
	}

	@Override
	public int hashCode() {
		int result = Boolean.hashCode(locked);
		result = 31 * result + Boolean.hashCode(transitive);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcDependencyScopeBehavior locthat))
			return false;

		return locked == locthat.locked && transitive == locthat.transitive;
	}

	@Override
	public String toString() {
		return "AIcDependencyScopeBehavior{" +
				"locked=" + locked +
				", transitive=" + transitive +
				'}';
	}
}
