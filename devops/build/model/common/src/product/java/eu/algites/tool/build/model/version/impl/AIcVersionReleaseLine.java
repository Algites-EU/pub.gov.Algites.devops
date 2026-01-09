package eu.algites.tool.build.model.version.impl;

import eu.algites.tool.build.model.version.intf.AIiVersionReleaseLine;

import java.util.Objects;

public class AIcVersionReleaseLine implements AIiVersionReleaseLine {

	private String laneVersion;

	public AIcVersionReleaseLine() {
	}

	public AIcVersionReleaseLine(String laneVersion) {
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
		if (!(aO instanceof AIcVersionReleaseLine locthat))
			return false;

		return Objects.equals(laneVersion, locthat.laneVersion);
	}

	@Override
	public String toString() {
		return "AIcReleaseLine{" + laneVersion + '}';
	}
}
