package eu.algites.tool.build.model.version.impl;

import eu.algites.tool.build.model.version.intf.AIiVersionReleaseLine;
import eu.algites.tool.build.model.version.intf.AIiVersionReleaseLineRevision;
import eu.algites.tool.build.model.version.intf.AIiVersionContext;
import eu.algites.tool.build.model.version.intf.AIiVersionQualifier;

import java.util.Objects;

public class AIcVersionContext implements AIiVersionContext {

	private AIiVersionReleaseLine releaseLine;
	private AIiVersionReleaseLineRevision revision;
	private AIiVersionQualifier qualifier;

	public AIcVersionContext() {
	}

	public AIcVersionContext(AIiVersionReleaseLine releaseLine, AIiVersionReleaseLineRevision revision, AIiVersionQualifier qualifier) {
		this.releaseLine = releaseLine;
		this.revision = revision;
		this.qualifier = qualifier;
	}

	@Override
	public AIiVersionReleaseLine getReleaseLine() {
		return releaseLine;
	}

	public void setReleaseLine(AIiVersionReleaseLine releaseLine) {
		this.releaseLine = releaseLine;
	}

	@Override
	public AIiVersionReleaseLineRevision getRevision() {
		return revision;
	}

	public void setRevision(AIiVersionReleaseLineRevision revision) {
		this.revision = revision;
	}

	@Override
	public AIiVersionQualifier getQualifier() {
		return qualifier;
	}

	public void setQualifier(AIiVersionQualifier qualifier) {
		this.qualifier = qualifier;
	}

	@Override
	public int hashCode() {
		int result = Objects.hashCode(releaseLine);
		result = 31 * result + Objects.hashCode(revision);
		result = 31 * result + Objects.hashCode(qualifier);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcVersionContext locthat))
			return false;

		return Objects.equals(releaseLine, locthat.releaseLine) && Objects.equals(revision, locthat.revision)
				&& Objects.equals(qualifier, locthat.qualifier);
	}

	@Override
	public String toString() {
		return "AIcVersionContext{" +
				"releaseLine=" + releaseLine +
				", revision=" + revision +
				", qualifier=" + qualifier +
				'}';
	}
}
