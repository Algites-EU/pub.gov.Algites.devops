package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.common.AIiReleaseLine;

import java.util.Objects;

public class AIcReleaseLine implements AIiReleaseLine {

	private String laneVersion;

	public AIcReleaseLine() {
	}

	public AIcReleaseLine(String laneVersion) {
		this.laneVersion = laneVersion;
	}

	@Override
	public String getLaneVersion() {
		return laneVersion;
	}

	public void setLaneVersion(String laneVersion) {
		this.laneVersion = laneVersion;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(laneVersion);
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcReleaseLine locthat))
			return false;

		return Objects.equals(laneVersion, locthat.laneVersion);
	}

	@Override
	public String toString() {
		return "AIcReleaseLine{" + laneVersion + '}';
	}
}
