package eu.algites.tool.build.model.impl;

import eu.algites.tool.build.model.common.AIiReleaseLineRevision;

public class AIcReleaseLineRevision implements AIiReleaseLineRevision {

	private int number;

	public AIcReleaseLineRevision() {
	}

	public AIcReleaseLineRevision(int number) {
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
		if (!(aO instanceof AIcReleaseLineRevision locthat))
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
