package eu.algites.tool.build.model.version.impl;

import eu.algites.tool.build.model.version.intf.AIiVersionReleaseLineRevision;

public class AIcVersionReleaseLineRevision implements AIiVersionReleaseLineRevision {

	private int number;

	public AIcVersionReleaseLineRevision() {
	}

	public AIcVersionReleaseLineRevision(int number) {
		this.number = number;
	}

	@Override
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public int hashCode() {
		return number;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcVersionReleaseLineRevision locthat))
			return false;

		return number == locthat.number;
	}

	@Override
	public String toString() {
		return "AIcReleaseLineRevision{" +
				"number=" + number +
				'}';
	}
}
